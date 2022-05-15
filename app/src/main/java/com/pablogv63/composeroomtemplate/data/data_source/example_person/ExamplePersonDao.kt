package com.pablogv63.composeroomtemplate.data.data_source.example_person

import androidx.room.*
import com.pablogv63.composeroomtemplate.domain.model.ExamplePerson
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamplePersonDao {

    @Query("Select * From examplePerson")
    fun getAll(): Flow<List<ExamplePerson>>

    @Query("Select * From examplePerson Where id = :id")
    fun findById(id: Int): Flow<ExamplePerson>

    // If exists, behaves the same as update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(examplePerson: ExamplePerson)

    @Delete
    fun delete(examplePerson: ExamplePerson)

    @Update
    fun update(examplePerson: ExamplePerson)
}