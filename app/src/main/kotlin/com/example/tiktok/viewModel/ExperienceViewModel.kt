package com.example.tiktok.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiktok.data.ExperienceRepository
import com.example.tiktok.data.model.ExperienceItem
import com.example.tiktok.utils.preDownloadImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Experience Screen 的 ViewModel
 * 功能：
 * 1. 分页加载 ExperienceItem
 * 2. 下拉刷新
 * 3. 预下载图片（使用 preDownloadImage）
 * 4. 暴露状态供 UI 层订阅
 */

class ExperienceViewModel : ViewModel() {

    private val repository = ExperienceRepository

    // 当前所有的 ExperienceItem 列表
    private val _items = MutableStateFlow<List<ExperienceItem>>(emptyList())
    val items: StateFlow<List<ExperienceItem>> = _items

    // 下拉刷新状态
    //内部使用
    private val _isRefreshing = MutableStateFlow(false)
    //可暴露给外部的参数
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    // 当前页码
    private var currentPage = 0

    /**
     * 加载第一页
     */

    fun loadFirstPage(context: Context) {
        viewModelScope.launch {
            currentPage = 0
            val list = repository.loadExperiencePage(currentPage)
            _items.value = list

            // 预下载图片
            preDownloadImage(list, context, viewModelScope)
        }
    }

    /**
     * 下拉刷新
     */
    fun refresh(context: Context) {
        viewModelScope.launch {
            _isRefreshing.value = true

            currentPage = 0
            val list = repository.loadExperiencePage(currentPage)
            _items.value = list

            // 预下载图片
            preDownloadImage(list, context, viewModelScope)

            _isRefreshing.value = false
        }
    }

    /**
     * 加载下一页
     */
    fun loadNextPage(context: Context) {
        viewModelScope.launch {
            currentPage++
            val newList = repository.loadExperiencePage(currentPage)

            // 更新状态
            _items.value = _items.value + newList

            // 预下载新页图片
            preDownloadImage(newList, context, viewModelScope)
        }
    }
}
