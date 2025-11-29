package com.example.tiktok.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * 悬浮按钮：切换列数
 * @param columnCount 当前列数
 * @param onToggle 切换列数的回调
 */
@Composable
fun ColumnToggleButton(
    columnCount: Int,
    onToggle: (Int) -> Unit,
    modifier: Modifier = Modifier   // 默认参数
) {
    FloatingActionButton(
        onClick = { onToggle(if (columnCount == 2) 1 else 2) },
        containerColor = Color.DarkGray,
        contentColor = Color.White,
        modifier = modifier.size(48.dp)
    ) {
        Text(text = if (columnCount == 2) "双列" else "单列")
    }
}

