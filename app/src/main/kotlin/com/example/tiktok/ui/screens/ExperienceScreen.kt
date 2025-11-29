package com.example.tiktok.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tiktok.ui.components.ColumnToggleButton
import com.example.tiktok.ui.components.ExperienceCard
import com.example.tiktok.ui.components.ExperienceTopBar
import com.example.tiktok.ui.components.ExperienceBottomBar
import com.example.tiktok.utils.imageLoaderProvider
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.example.tiktok.ui.effect.searchBarVisibility
import com.example.tiktok.viewmodel.ExperienceViewModel

@Composable
fun ExperienceScreen() {
    //创建viewModel用于负责读取数据
    val vm: ExperienceViewModel = viewModel()
    val context = LocalContext.current//当地运行内容
    val scope = rememberCoroutineScope()//协程作用域

    val items by vm.items.collectAsState()//ExperienceItems数据List
    val isRefreshing by vm.isRefreshing.collectAsState()//用来记录是否刷新的bool

    var columnCount by remember { mutableStateOf(2) }//用来改变当前为单列还是双列状态

    //gridState用于检测目前的屏幕状态，用于设置搜索栏的可见性
    //searchBarVisible作为参数，交由searchBarVisibility（）来调整判断
    val gridState = rememberLazyStaggeredGridState()
    val searchBarVisible = searchBarVisibility(gridState)

    //提供全局的imageloader，调用imageProvider()
    val imageLoader = imageLoaderProvider(
        coroutineScope = scope,
        context = context
    )

    // 首次加载
    LaunchedEffect(Unit) {
        if (items.isEmpty()) {
            //调用的vm的数据首次读取函数
            vm.loadFirstPage(context)
        }
    }

    // 半屏触发加载下一页
    LaunchedEffect(items.size, gridState) {
        //将gridState转换为Flow
        snapshotFlow {
            // 当前滚动位置 + 偏移
            //firstVisibleItemIndex：当前屏幕最顶部可见的网格项索引。
            //firstVisibleItemScrollOffset：这个项滚动的偏移量。
            //把索引 + 偏移量计算成 visibleIndex，表示当前屏幕大概显示到第几个 item
            val firstIndex = gridState.firstVisibleItemIndex
            val firstOffset = gridState.firstVisibleItemScrollOffset
            firstIndex + if (firstOffset > 0) 1 else 0
        }
            //Flow开始接收数据
            .collect { visibleIndex ->
                //滑到半屏时开始加载
                if (items.isNotEmpty() && visibleIndex >= items.size / 3) {
                    vm.loadNextPage(context)
                }
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Black,
            //顶部菜单
            topBar = { ExperienceTopBar(visible = searchBarVisible) },
            //底部菜单，调整上移
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    ExperienceBottomBar()
                }
            }
        ) { padding ->
            //使用SwipeRefresh实现下拉刷新
            //onRefresh状态交由vm来确定
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = { vm.refresh(context) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                //实现瀑布流滑动
                LazyVerticalStaggeredGrid(
                    //列数由悬浮按钮决定
                    columns = StaggeredGridCells.Fixed(columnCount),
                    //记录状态用于搜索栏可见性和预加载策略
                    state = gridState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    //每列间距
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    //渲染Card，负责将每个获取的ExperienceItem数据渲染成ExperienceCard样式
                    items(items, key = { it.id }) { item ->
                        Column {
                            ExperienceCard(
                                item = item,
                                imageLoader = imageLoader
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }
            }
        }

        //悬浮按钮，用于切换单列还是双列
        ColumnToggleButton(
            columnCount = columnCount,
            onToggle = { newCount -> columnCount = newCount },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp, bottom = 100.dp)
        )
    }
}
