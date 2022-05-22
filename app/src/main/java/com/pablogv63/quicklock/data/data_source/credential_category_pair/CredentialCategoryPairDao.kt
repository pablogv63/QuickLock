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

    @Transaction
    @Query("Select * From credential")
    fun getCredentialsWithCategories(): Flow<List<CredentialWithCategoryList>>
}