package com.example.tiktok.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import com.example.tiktok.R
import com.example.tiktok.viewmodel.ExperienceViewModel

/**
 * 启动页，在启动页时预先调用ViewModel,
 * 进行预加载，提升用户体验
 */
@Composable
fun SplashScreen(onTimeout: () -> Unit) {

    val vm: ExperienceViewModel = viewModel()
    val context = LocalContext.current

    // 启动开始加载第一页数据
    LaunchedEffect(Unit) {
        if (vm.items.value.isEmpty()) {
            vm.loadFirstPage(context)  //触发vm的加载
        }
        //启动页停留2.5s
        delay(2500)
        onTimeout()
    }

    // 显示启动背景图
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.splash_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}
