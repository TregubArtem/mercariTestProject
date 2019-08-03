package com.app.repository

import com.app.api.Api
import com.app.api.model.CategoryModel

/** Main interface to work with categories */
interface CategoriesRepository {

    /**
     * Provides categories from some source
     *
     * @return list of [CategoryModel]
     */
    suspend fun getCategories(): List<CategoryModel>
}

/** Actual implementation of [CategoriesRepository] interface */
class CategoriesRepositoryImpl : CategoriesRepository {

    override suspend fun getCategories(): List<CategoryModel> =
        Api.getTabs()
}