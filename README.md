# tiktokExperienceApp

> 一个基于 Jetpack Compose 的 高防抖音“经验”频道应用（单/双列瀑布流卡片列表）。

---

## 项目简介

`tiktokExperienceApp` 是一个使用 Kotlin 和 Jetpack Compose 构建的 Android 应用，主要功能是：

- 单/双列瀑布流卡片，（仿 TikTok经验面 风格）
- 图片异步加载与缓存（使用 Coil）
- 下拉刷新（SwipeRefresh）
- 支持无限滚动加载下一页
- 支持切换列数展示



---

## 功能说明

1. **单/双列瀑布流卡片列表**  
   使用 `LazyVerticalStaggeredGrid` 渲染卡片，支持多列布局，可切换列数。  
   
2. **图片预加载与缓存**  
   使用 Coil 对卡片主图和头像进行预加载，保证滚动时加载流畅。

3. **下拉刷新**  
   下拉刷新触发 `ViewModel` 的 `refresh()` 方法，刷新内容列表。

4. **半屏触发加载下一页**  
   当滚动到当前列表的半屏位置时，会自动调用 `loadNextPage()` 预加载下一页数据。


---
## 项目结构
- tiktokExperienceApp/
- ├─ app/src/main/java/com/example/tiktok
- │ ├─ MainActivity.kt # 入口Activity
- │ ├─ ui/screens/ # 页面Screen
- │ │ ├─ ExperienceScreen.kt #经验主页面
- │ │ └─ SplashScreen.kt #启动页
- │ ├─ ui/components/ # UI组件（卡片、顶部/底部栏、悬浮按钮）
- │ ├─ ui/theme/ # 主题
- │ └─ viewmodel/ # ViewModel管理数据
- ├─ build.gradle.kts # Gradle构建脚本
- └─ lib.version.toml # 版本和依赖管理


---

## 技术栈

- **Kotlin**：开发语言
- **Jetpack Compose**：UI 构建
- **Coil**：图片加载和缓存
- **Accompanist SwipeRefresh**：下拉刷新功能
- **Navigation Compose**：页面导航
- **Material3**：UI 组件和主题
- **Coroutine + Flow**：异步加载和状态管理

---
-克隆仓库：
-使用方法
git clone https://github.com/ashesaa11/tiktokExperienceApp.git

作者

ashesaa11

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

```







