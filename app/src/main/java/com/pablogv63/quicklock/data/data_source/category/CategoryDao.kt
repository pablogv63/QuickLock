package com.pablogv63.quicklock.data.data_source.category

import androidx.room.*
import com.pablogv63.quicklock.domain.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("Select * From Category")
    fun getCategories(): Flow<List<Category>>

    @Query("Select * From Category Where categoryId=:id")
    fun getCategoryById(id: Int): Flow<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: Category)

    @Delete
    fun deleteCategory(category: Category)

    @Update
    fun updateCategory(category: Category)
}