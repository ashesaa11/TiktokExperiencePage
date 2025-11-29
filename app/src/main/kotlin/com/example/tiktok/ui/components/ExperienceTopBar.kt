package com.example.tiktok.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tiktok.R

/**经验面 顶部菜单栏设计
 *
 */
@Composable
fun ExperienceTopBar(visible: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(top = 24.dp, bottom = 8.dp)
    ) {
        //设计为可左右滑动
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            //将每个按钮设计为统一样式
            listOf("经验", "热点", "直播", "精选", "团购", "同城", "关注", "商城", "推荐")
                .forEach { text ->
                    Text(
                        text = text,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable { }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
        }

        Spacer(modifier = Modifier.height(8.dp))

        //设计搜索栏，可见性由visible决定，visible由同级包effect下SearchBarVisibility文件决定
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            var searchText by remember { mutableStateOf("") }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(Color.DarkGray, RoundedCornerShape(20.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    //搜索图标由本地给出
                    Image(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "搜索图标",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    //搜索输入栏
                    BasicTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        textStyle = TextStyle(color = Color.White, fontSize = 14.sp),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )

                    //搜索提示词
                    if (searchText.isEmpty()) {
                        Text(
                            text = "搜索",
                            color = Color.LightGray,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
