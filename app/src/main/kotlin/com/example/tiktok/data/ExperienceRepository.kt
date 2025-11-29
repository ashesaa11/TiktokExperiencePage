package com.example.tiktok.data

import com.example.tiktok.data.model.ExperienceItem

/**
 * 数据仓库：只负责提供数据，不做 UI 相关操作
 */
object ExperienceRepository {

    suspend fun loadExperiencePage(page: Int): List<ExperienceItem> {
        return MockData.getExperienceList(page)
    }
}
