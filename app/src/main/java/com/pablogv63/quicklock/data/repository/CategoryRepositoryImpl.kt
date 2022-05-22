package com.pablogv63.quicklock.data.repository

import com.pablogv63.quicklock.data.data_source.category.CategoryDao
import com.pablogv63.quicklock.domain.model.Category
import com.pablogv63.quicklock.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CategoryRepositoryImpl(
    private val dao: CategoryDao
): CategoryRepository {
    override fun getCategories(): Flow<List<Category>> {
        return dao.getCategories()
    }

    override suspend fun getCategoryById(id: Int): Flow<Category> {
        return dao.getCategoryById(id)
    }

    override suspend fun insertAll(categoryList: List<Category>): List<Int> {
        val idList = mutableListOf<Int>()
        withContext(Dispatchers.IO) {
            categoryList.map {
                idList.add(dao.insertCategory(category = it).toInt())
            }
        }
        return idList
    }

    override suspend fun insertCategory(category: Category) {
        return withContext(Dispatchers.IO) { dao.insertCategory(category = category) }
    }

    override suspend fun deleteCategory(category: Category) {
        return withContext(Dispatchers.IO) { dao.deleteCategory(category = category) }
    }
}