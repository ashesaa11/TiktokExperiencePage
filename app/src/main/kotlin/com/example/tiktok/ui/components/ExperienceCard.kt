package com.example.tiktok.ui.components

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.tiktok.R
import com.example.tiktok.data.model.ExperienceItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext

/**经验面 卡片的样式设计
 */
@Composable
fun ExperienceCard(
    item: ExperienceItem,
    imageLoader: ImageLoader
) {
    //借用Compsable函数的特性，将Like(点赞数)借用mutableStateOf()创建一个状态，
    // 然后借用remember（类似静态变量）避免被重复刷新
    //by使用委托机制方便管理
    var liked by remember { mutableStateOf(item.liked) }
    var likeCount by remember { mutableStateOf(item.likes) }

    var pressed by remember { mutableStateOf(false) }
    //制作点击缩放动画
    val cardScale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = spring()
    )
    //制作点赞效果
    val likeScale by animateFloatAsState(
        targetValue = if (liked) 1.25f else 1f,
        animationSpec = spring()
    )
    //确定协程作用域，方便管理生命周期
    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clickable {
                pressed = true
                scope.launch {
                    delay(120)
                    pressed = false
                }
            },
        //形状，颜色
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.15f)
        ),
        //阴影高度
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            // 进行主图加载，采用异步读取方式（Coil方法）
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    //用于调试，读取时是否是从哪里读取
                    //1 缓存 2 硬盘 3 网络
                    .listener(
                        onStart = {
                            Log.d("CoilDebug", "开始加载：${item.imageUrl}")
                        },
                        onSuccess = { _, result ->
                            Log.d("CoilDebug", "加载成功：${item.imageUrl} 来源=${result.dataSource}")
                        },
                        onError = { _, error ->
                            Log.e("CoilDebug", "加载失败：${item.imageUrl} -> ${error.throwable}")
                        }
                    )
                    .build(),
                contentDescription = "image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(item.height.dp)
                    .clip(RoundedCornerShape(topStart = 1.dp, topEnd = 1.dp)),
                contentScale = ContentScale.Crop
            )


            Spacer(modifier = Modifier.height(8.dp))

            //文章的标题内容
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 2,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            //新的一列，用于排布用户信息
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // 加载用户图像
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.userAvatar)
                            .crossfade(true)
                            .scale(Scale.FILL)
                            .build(),
                        imageLoader = imageLoader,
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = item.username,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                // 点赞按钮
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        //调用本地的点赞图片
                        painter = androidx.compose.ui.res.painterResource(
                            id = if (liked) R.drawable.ic_like_filled else R.drawable.ic_like_outline
                        ),
                        contentDescription = "Like",
                        modifier = Modifier
                            .size(22.dp)
                            .scale(likeScale)
                            //如没点赞点击则点击数+1，反之-1
                            .clickable {
                                liked = !liked
                                likeCount = if (liked) likeCount + 1 else likeCount - 1
                            }
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    //点击数
                    Text(
                        text = likeCount.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
