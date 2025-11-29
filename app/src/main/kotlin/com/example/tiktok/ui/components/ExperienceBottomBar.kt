package com.example.tiktok.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.example.tiktok.R

/**经验面 底部菜单栏
 */
@Composable
fun ExperienceBottomBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(vertical = 36.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        //只含有文字，不具有实际跳转效果
        Text(
            text = "首页",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { }
        )
        Text(
            text = "朋友",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = "Add",
            modifier = Modifier
                .size(24.dp)
                .clickable { }
        )

        Text(
            text = "消息",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { }
        )

        Text(
            text = "我",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { }
        )
    }
}
