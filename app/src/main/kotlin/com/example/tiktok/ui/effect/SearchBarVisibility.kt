package com.example.tiktok.ui.effect

import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.runtime.LaunchedEffect


/**
 * 搜索栏可见状态控制：
 * - 下滑立刻隐藏
 * - 上滑累计超过阈值才显示
 */
@Composable
fun searchBarVisibility(
    //传入LazyVerticalStaggeredGrid，
    // 使用其中的delta全局滑动量用来控制搜索栏可见性
    gridState: LazyStaggeredGridState,
    showThreshold: Int = 20 // 上滑显示阈值
): Boolean {//传回Visble可见性
    //上一次滑动位置和最后的滑动位置
    var lastIndex by remember { mutableStateOf(0) }
    var lastOffset by remember { mutableStateOf(0) }
    var upwardScrollAccumulator by remember { mutableStateOf(0) }
    var visible by remember { mutableStateOf(true) } // 初始可见

    //启动新协程，用于监听屏幕滚动状态
    LaunchedEffect(gridState) {
        snapshotFlow { gridState.firstVisibleItemIndex to gridState.firstVisibleItemScrollOffset }
            .collect { (index, offset) ->
                val delta = (index - lastIndex) * 1000 + (offset - lastOffset)

                if (delta > 0) {
                    // 下滑 -> 立即隐藏，并重置上滑累计
                    if (visible) visible = false
                    upwardScrollAccumulator = 0
                } else if (delta < 0) {
                    // 上滑 -> 累积
                    upwardScrollAccumulator += -delta
                    if (!visible && upwardScrollAccumulator > showThreshold) {
                        visible = true
                        upwardScrollAccumulator = 0
                    }
                }

                lastIndex = index
                lastOffset = offset
            }
    }

    return visible
}

