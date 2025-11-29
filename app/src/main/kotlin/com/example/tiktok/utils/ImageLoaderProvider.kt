package com.example.tiktok.utils

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CoroutineScope

/**
 * 提供全局统一 ImageLoader
 */
fun imageLoaderProvider(
    context: Context,
    coroutineScope: CoroutineScope
): ImageLoader {


    // 创建 OkHttpClient，设置超时和日志
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()

    //返回创建的ImageLoader
    return ImageLoader.Builder(context)
        //采用内存缓存
        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25) // 占用最大内存 25%
                .build()
        }
        //采用磁盘储存
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.1) // 占用磁盘缓存 10%
                .build()
        }
        // 使用 OkHttpClient进行访问
        .okHttpClient(okHttpClient)
        //创建渐隐出现效果
        .crossfade(true)
        .build()//创建
}
