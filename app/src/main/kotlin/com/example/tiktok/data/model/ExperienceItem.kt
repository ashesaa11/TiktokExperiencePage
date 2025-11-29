package com.example.tiktok.data.model

/**
 * 数据模型
 */
data class ExperienceItem(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val username: String,
    val userAvatar: String,
    val likes: Int,
    val liked: Boolean,
    val height: Int
)