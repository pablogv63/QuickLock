package com.pablogv63.quicklock.domain.repository

import com.pablogv63.quicklock.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategories(): Flow<List<Category>>
    suspend fun getCategoryById(id: Int): Flow<Category>
    suspend fun insertAll(categoryList: List<Category>): List<Int>
    suspend fun insertCategory(category: Category)
    suspend fun deleteCategory(category: Category)
}