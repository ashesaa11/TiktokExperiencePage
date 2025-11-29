package com.example.tiktok.utils

import android.content.Context
import android.util.Log
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.tiktok.data.model.ExperienceItem
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlin.system.measureTimeMillis

/**
 * 预加载方法
 */
fun preDownloadImage(
    items: List<ExperienceItem>,
    context: Context,
    coroutineScope: CoroutineScope,
) {
    val semaphore = Semaphore(12) // 限制并发
    val imageLoader = imageLoaderProvider(context ,coroutineScope ) // 使用统一 ImageLoader

    //启动一个IO线程协程
    coroutineScope.launch(Dispatchers.IO) {
        //map ：为每个 item 创建一个协程，用于读取图片。
        //返回一个 Deferred 对象的集合。
        items.map { item ->
            async {
                //使用.withPermit限制并发数
                semaphore.withPermit {
                    try {
                        // 主图预加载
                        //timeMain用于计算读取时间，用于调试
                        val timeMain = measureTimeMillis {
                            //构建图片读取请求
                            val request = ImageRequest.Builder(context)
                                .data(item.imageUrl)
                                //启用内存 硬盘存储
                                .memoryCachePolicy(CachePolicy.ENABLED)
                                .diskCachePolicy(CachePolicy.ENABLED)
                                .crossfade(true)
                                //创建监听，用于调试输出读取状况
                                .listener(
                                    onSuccess = { _, _ ->
                                        Log.d("PreloadImage", "主图缓存命中: ${item.imageUrl}")
                                    },
                                    onError = { _, result ->
                                        Log.e(
                                            "PreloadImage",
                                            "主图加载失败: ${item.imageUrl} -> ${result.throwable}"
                                        )
                                    }
                                )
                                .build()
                            //将该请求加入imageLoader队列
                            imageLoader.enqueue(request)
                        }
                        Log.d("PreloadImage", "主图预加载耗时: ${timeMain}ms | URL: ${item.imageUrl}")

                        // 头像预加载
                        val avatarRequest = ImageRequest.Builder(context)
                            .data(item.userAvatar)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .crossfade(true)
                            .build()
                        imageLoader.enqueue(avatarRequest)

                    } catch (e: Exception) {
                        Log.e(
                            "PreloadImage",
                            "预加载异常: ${item.imageUrl} 或 ${item.userAvatar}",
                            e
                        )
                    }
                }
            }
        }.awaitAll()//等待所有协程完成任务
        Log.d("PreloadImage", "所有图片预加载任务完成")
    }
}
