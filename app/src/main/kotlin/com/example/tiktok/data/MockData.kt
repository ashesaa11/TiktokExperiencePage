package com.example.tiktok.data

import com.example.tiktok.data.model.ExperienceItem

/**
 * 用于Mock模拟数据
 */
object MockData {

    private val usernames = listOf(
        "55303", "Dyy", "yq信仰者"
    )

    private val titles = listOf(
        "早上好", "今天吃油条", "明天没早扒", "我是猪妞谢谢家人们", "再让我听到新三国的台词我就扎龙自己的耳朵"
    )

    //随机生成头像
    private fun randomAvatar(id: Int): String =
        "https://picsum.photos/seed/avatar$id/150/150"

    /**
     * 生成 ExperienceItem 列表
     * 不做网络请求，只生成本地数据和 URL
     */
    fun getExperienceList(page: Int = 0, pageSize: Int = 12): List<ExperienceItem> {
        //返回新的已经Mock好的ExperienceItem
        return (1..pageSize).map { index ->
            val globalIndex = page * pageSize + index
            val username = usernames.random()
            val title = titles.random()
            ExperienceItem(
                id = globalIndex,
                imageUrl = "https://picsum.photos/400/${180 + (0..80).random()}?random=$globalIndex",
                title = title,
                username = username,
                userAvatar = randomAvatar(globalIndex % 70 + 1),
                likes = (0..500).random(),
                liked = listOf(true, false).random(),
                height = listOf(180, 200, 220, 250, 280).random()
            )
        }
    }
}
