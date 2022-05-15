package com.pablogv63.quicklock.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pablogv63.quicklock.data.data_source.category.CategoryDao
import com.pablogv63.quicklock.data.data_source.credential.CredentialConverters
import com.pablogv63.quicklock.data.data_source.credential.CredentialDao
import com.pablogv63.quicklock.domain.model.CredentialCategoryPair
import com.pablogv63.quicklock.data.data_source.credential_category_pair.CredentialCategoryPairDao
import com.pablogv63.quicklock.domain.model.Category
import com.pablogv63.quicklock.domain.model.Credential

@Database(
    entities = [Credential::class, Category::class, CredentialCategoryPair::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CredentialConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun credentialDao(): CredentialDao
    abstract fun categoryDao(): CategoryDao
    abstract fun credentialWithCategoryDao(): CredentialCategoryPairDao

    companion object {
        const val DATABASE_NAME = "quicklock_db"
    }
}