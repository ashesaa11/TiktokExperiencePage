# tiktokExperienceApp

> 一个基于 Jetpack Compose 的 TikTok 风格卡片浏览应用（无视频，仅卡片浏览）。

---

## 项目简介

`tiktokExperienceApp` 是一个使用 Kotlin 和 Jetpack Compose 构建的 Android 应用，主要功能是：

- 卡片式内容展示（仿 TikTok 风格）
- 图片异步加载与缓存（使用 Coil）
- 下拉刷新（SwipeRefresh）
- 无限滚动加载下一页
- 支持切换列数展示
- 启动页（Splash Screen）并在启动时预加载内容

> 注意：此版本仅展示卡片数据，不包含视频播放功能。

---

## 功能说明

1. **卡片列表展示**  
   使用 `LazyVerticalStaggeredGrid` 渲染卡片，支持多列布局，可切换列数。  
   
2. **图片预加载与缓存**  
   使用 Coil 对卡片主图和头像进行预加载，保证滚动时加载流畅。

3. **下拉刷新**  
   下拉刷新触发 `ViewModel` 的 `refresh()` 方法，刷新内容列表。

4. **半屏触发加载下一页**  
   当滚动到当前列表的半屏位置时，会自动调用 `loadNextPage()` 预加载下一页数据。

5. **启动页 Splash Screen**  
   - 展示本地背景图片
   - 持续 2-3 秒
   - 在启动页显示时提前调用主界面加载逻辑，实现内容预加载

---
## 项目结构
tiktokExperienceApp/
├─ app/src/main/java/com/example/tiktok
│ ├─ MainActivity.kt # 入口Activity，设置NavHost
│ ├─ ui/screens/ # 页面Screen
│ │ ├─ ExperienceScreen.kt
│ │ └─ SplashScreen.kt
│ ├─ ui/components/ # UI组件（卡片、顶部/底部栏等）
│ ├─ ui/theme/ # 主题相关文件
│ └─ viewmodel/ # ViewModel管理数据
├─ build.gradle.kts # Gradle构建脚本
└─ lib.version.toml # 版本和依赖管理


---

## 技术栈

- **Kotlin**：主要开发语言
- **Jetpack Compose**：现代 UI 构建
- **Coil**：图片加载和缓存
- **Accompanist SwipeRefresh**：下拉刷新功能
- **Navigation Compose**：页面导航
- **Material3**：UI 组件和主题
- **Coroutine + Flow**：异步加载和状态管理

---
-克隆仓库：
-使用方法
git clone https://github.com/yourusername/tiktokExperienceApp.git

作者

Chen ashesaa11

Github: https://github.com/ashesaa11


## 依赖说明

```kotlin
// ===================== Jetpack Compose =====================
implementation("androidx.compose.foundation:foundation:1.5.0") // LazyVerticalGrid
implementation("androidx.compose.material3:material3:1.4.0-alpha05") // UI组件
implementation("androidx.compose.material3:material3-window-size-class:1.2.0-alpha05") // Material3窗口大小支持
implementation("androidx.navigation:navigation-compose:2.7.3") // 页面导航
implementation("com.google.accompanist:accompanist-swiperefresh:0.30.1") // 下拉刷新

// ===================== 图片加载 =====================
implementation("io.coil-kt:coil:2.7.0") // 核心库
implementation("io.coil-kt:coil-compose:2.7.0") // Compose 图片加载

// ===================== JSON解析（可选） =====================
implementation("com.google.code.gson:gson:2.10.1") // 数据解析
---





## 项目结构

