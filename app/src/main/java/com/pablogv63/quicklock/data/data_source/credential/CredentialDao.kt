package com.pablogv63.quicklock.data.data_source.credential

import androidx.room.*
import com.pablogv63.quicklock.domain.model.CredentialWithCategoryList
import com.pablogv63.quicklock.domain.model.Credential
import kotlinx.coroutines.flow.Flow

@Dao
interface CredentialDao {

    @Query("Select * From credential")
    fun getAll(): Flow<List<Credential>>

    @Query("Select * From credential Where credentialId = :id")
    fun findById(id: Int): Flow<Credential>

    // With categories
    @Transaction
    @Query("Select * From credential")
    fun getAllWithCategories(): Flow<List<CredentialWithCategoryList>>

    @Transaction
    @Query("Select * From credential Where credentialId = :id")
    fun findByIdWithCategories(id: Int): Flow<CredentialWithCategoryList>

    // If exists, behaves the same as update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(credential: Credential): Long

    @Delete
    fun delete(credential: Credential): Int

    @Query("Delete From credential Where credentialId=:credentialId")
    fun delete(credentialId: Int): Int

    @Update
    fun update(credential: Credential): Int
}