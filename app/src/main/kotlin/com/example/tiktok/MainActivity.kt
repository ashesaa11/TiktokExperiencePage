package com.example.tiktok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tiktok.ui.screens.ExperienceScreen
import com.example.tiktok.ui.screens.SplashScreen
import com.example.tiktok.ui.theme.TiktokTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TiktokTheme {
                //创建导航控制Controller
                //使用rememberNavController（）保证数据不会丢失
                val navController = rememberNavController()

                //设置导航图
                //先打开splash,启动页面
                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ) {
                    // 启动页
                    composable("splash") {
                        SplashScreen(
                            //结束时回调至experience
                            onTimeout = {
                                navController.navigate("experience") {
                                    //销毁这个界面，防止回到启动页
                                    popUpTo("splash") { inclusive = true }
                                }
                            }
                        )
                    }

                    // 主页面
                    composable("experience") {
                        ExperienceScreen()
                    }
                }
            }
        }
    }
}
