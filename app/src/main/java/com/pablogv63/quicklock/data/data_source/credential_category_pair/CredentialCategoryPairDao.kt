package com.pablogv63.quicklock.data.data_source.credential_category_pair

import androidx.room.*
import com.pablogv63.quicklock.domain.model.CredentialCategoryPair
import com.pablogv63.quicklock.domain.model.CredentialWithCategoryList
import kotlinx.coroutines.flow.Flow

@Dao
interface CredentialCategoryPairDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(credentialCategoryPair: CredentialCategoryPair)

    @Delete
    fun delete(credentialCategoryPair: CredentialCategoryPair)

    @Transaction
    @Query("Select * From credentialcategorypair")
    fun getCredentialCategoryPairs(): Flow<List<CredentialCategoryPair>>

    @Query("Select * From credentialcategorypair where credentialId=:credentialId")
    fun getCredentialCategoryPairsFromId(credentialId: Int): Flow<List<CredentialCategoryPair>>

    @Query("Delete From credentialcategorypair where credentialId=:credentialId")
    fun deleteCredentialCategoryPairsFromId(credentialId: Int)

    @Transaction
    @Query("Select * From credential")
    fun getCredentialsWithCategories(): Flow<List<CredentialWithCategoryList>>

    @Transaction
    @Query("Select * From credential where credentialId=:credentialId")
    fun getCredentialWithCategoriesFromId(credentialId: Int): Flow<CredentialWithCategoryList?>
}