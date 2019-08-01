package com.app.repository

import com.app.api.Api
import com.app.api.model.CategoryModel
import com.app.api.model.TimelineItemModel

/** Main interface to work with timeline list */
interface TimelineRepository {

    /**
     * Provides timeline items from some source
     *
     * @param category parent category of items
     * @return list of [TimelineItemModel]
     */
    suspend fun getItems(category: CategoryModel): List<TimelineItemModel>
}

/** Actual implementation of [TimelineRepository] interface */
class TimelineRepositoryImpl : TimelineRepository {

    override suspend fun getItems(category: CategoryModel): List<TimelineItemModel> =
        Api.getList(category.data)
}