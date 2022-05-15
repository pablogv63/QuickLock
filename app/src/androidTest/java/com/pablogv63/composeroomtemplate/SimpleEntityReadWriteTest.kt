package com.pablogv63.composeroomtemplate

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pablogv63.composeroomtemplate.data.data_source.AppDatabase
import com.pablogv63.composeroomtemplate.data.data_source.example_person.ExamplePersonDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var examplePersonDao: ExamplePersonDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        examplePersonDao = db.examplePersonDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeExamplePersonAndReadInList(){
        // Insert example person
        val examplePerson = TestUtil.createExamplePerson("Example", LocalDate.now())
        examplePersonDao.insert(examplePerson)
        // Retrieve example person
        runBlocking {
            val byName = examplePersonDao.findById(examplePerson.id).first()
            MatcherAssert.assertThat(byName, Matchers.equalTo(examplePerson))
        }
    }
}