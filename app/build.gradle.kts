println("========================================================build.gradle.kts starting========================================================")

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 32
    buildToolsVersion = "33.0.0"

    defaultConfig {
        applicationId = "com.oldsboy.basekotlinproject"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }

//    配置签名
    signingConfigs{
        val keystoreFilePath = "keystore.jks"
        val pwd = "123456"
        val myKeyAlias = "key"
        register("release") {
            keyAlias = myKeyAlias
            keyPassword = pwd
            storeFile = file(keystoreFilePath)
            storePassword = pwd
        }
    }

//    修改打包名称,例:all_kotlin_test_output_apk_1.01_release.apk
    android.applicationVariants.all{
        val buildType = this.buildType.name
        outputs.all {
            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                this.outputFileName = "all_kotlin_test_output_apk_${defaultConfig.versionName}_$buildType.apk"
            }
        }
    }

    buildTypes {
        getByName("release") {
//            signingConfig = signingConfigs.getByName("release")

            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),"proguard-rules.pro")

            println("执行buildTypes")
        }
//        getByName("debug"){
//            signingConfig = signingConfigs.getByName("release")
//        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
    }
}

//Kotlin 源代码可以与 Java 源代码放在相同文件夹或者不同文件夹中。默认约定是使用不同的文件夹：
//如果不使用默认约定，那么应该更新相应的 sourceSets 属性：
//sourceSets.main {
//    java.srcDirs("src/main/java", "src/main/kotlin", "src/main/newMain")
//}
//println(sourceSets)
//println(sourceSets.asMap)
//println(sourceSets.size)
//println(sourceSets.names)
//println(sourceSets.rules)
//println(rootProject.rootDir.path)

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
}

println("========================================================build.gradle.kts end========================================================")