plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    //新增
    id("com.google.gms.google-services")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.example.tiktok"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.tiktok"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation.layout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

// ===================== Google Firebase 依赖 =====================
    implementation(platform("com.google.firebase:firebase-bom:33.5.1")) // Firebase 版本管理

// ===================== Jetpack Compose 依赖 =====================
    implementation("androidx.compose.foundation:foundation:1.5.0")      // 基础组件
    implementation("androidx.compose.material3:material3:1.4.0-alpha05") // Material3 UI 样式，
    implementation("androidx.compose.material3:material3-window-size-class:1.2.0-alpha05") // 响应式窗口布局
    implementation("androidx.navigation:navigation-compose:2.7.3")      // Compose 页面导航
    implementation("com.google.accompanist:accompanist-swiperefresh:0.30.1") // 下拉刷新

// ===================== Coil 图片加载库 =====================
    implementation("io.coil-kt:coil:2.7.0")             // 核心库
    implementation("io.coil-kt:coil-compose:2.7.0")     // Compose 图片加载

}