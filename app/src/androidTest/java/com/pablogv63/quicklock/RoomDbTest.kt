package com.pablogv63.quicklock

import android.content.Context
import android.graphics.Color
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pablogv63.quicklock.data.data_source.AppDatabase
import com.pablogv63.quicklock.data.data_source.category.CategoryDao
import com.pablogv63.quicklock.data.data_source.credential.CredentialDao
import com.pablogv63.quicklock.domain.model.CredentialWithCategoryList
import com.pablogv63.quicklock.data.data_source.credential_category_pair.CredentialCategoryPairDao
import com.pablogv63.quicklock.domain.model.CredentialCategoryPair
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomDbTest {
    private lateinit var credentialDao: CredentialDao
    private lateinit var categoryDao: CategoryDao
    private lateinit var credentialCategoryPairDao: CredentialCategoryPairDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        credentialDao = db.credentialDao()
        categoryDao = db.categoryDao()
        credentialCategoryPairDao = db.credentialWithCategoryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeCredentialWithoutCategoryAndReadInList(){
        // Insert credential
        val credential = TestUtil.createCredential(1, "Cred1", "cred@mail.com", "****")
        credentialDao.insert(credential)
        // Retrieve example person
        runBlocking {
            val byName = credentialDao.findById(credential.credentialId).first()
            MatcherAssert.assertThat(byName, Matchers.equalTo(credential))
        }
    }

    @Test
    @Throws(Exception::class)
    fun writeCredentialWithCategoryAndReadInList(){
        // Insert credential and category
        val credential = TestUtil.createCredential(1, "Cred1", "cred@mail.com", "****")
        credentialDao.insert(credential)
        val category = TestUtil.createCategory(1,"Studies", Color.GREEN)
        categoryDao.insertCategory(category)
        val credentialCategoryPair = CredentialCategoryPair(credential.credentialId, category.categoryId)
        credentialCategoryPairDao.insert(credentialCategoryPair)
        // Retrieve and compare
        runBlocking {
            val retrieved = credentialCategoryPairDao.getCredentialsWithCategories().first().first()
            MatcherAssert.assertThat(retrieved, Matchers.equalTo(credentialCategoryPair))
        }
    }

    @Test
    @Throws(Exception::class)
    fun writeCredentialWithCategoryAndReadPair(){
        // Insert credential and category
        val credential = TestUtil.createCredential(1, "Cred1", "cred@mail.com", "****")
        credentialDao.insert(credential)
        val category = TestUtil.createCategory(1,"Studies", Color.GREEN)
        categoryDao.insertCategory(category)
        val credentialCategoryPair = CredentialCategoryPair(credential.credentialId, category.categoryId)
        credentialCategoryPairDao.insert(credentialCategoryPair)
        // Create pair
        val pair = CredentialWithCategoryList(credential, listOf(category))
        // Retrieve and compare
        runBlocking {
            val retrievedPair = credentialCategoryPairDao.getCredentialCategoryPairs().first().first()
            MatcherAssert.assertThat(retrievedPair, Matchers.equalTo(pair))
        }
    }
}