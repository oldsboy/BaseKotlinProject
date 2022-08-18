println("========================================================project.build.gradle.kts starting========================================================")

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        //最新阿里镜像/替代jcenter()
        maven("https://maven.aliyun.com/repository/public")
//        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        //最新阿里镜像/替代jcenter()
        maven("https://maven.aliyun.com/repository/public")
//        maven("https://jitpack.io")
    }
}

tasks {
    //    create的task会直接执行,而registering的task得手动触发
    val clean by registering(Delete::class) {
        delete(buildDir)
    }

    //  剪切文件
    fun fileClipTo(origin_file: File, target_file: File) {
        if (target_file.exists()) {
            var maxVersion = 0;
            for (listFile in file(target_file.parentFile).listFiles()) {    //  找到最大的一个同版本命名数
                if (listFile.nameWithoutExtension.contains(target_file.nameWithoutExtension)) {
                    val start = listFile.nameWithoutExtension.indexOf("(")+1
                    val end = listFile.nameWithoutExtension.indexOf(")")
                    if (start != -1 && end != -1) {
                        val version = listFile.nameWithoutExtension.substring(start, end)
                        maxVersion = maxOf(maxVersion, version.toInt())
                    }
                }
            }

            maxVersion++
            val newPath = "${target_file.parentFile.absolutePath}/${target_file.nameWithoutExtension}($maxVersion).apk"
            origin_file.copyTo(file(newPath))
        }else{
            origin_file.copyTo(target_file)
        }

        origin_file.delete()
    }

    create("move_apk") {
        println("move_apk starting")
        file("apk/").mkdirs();

        file("app/build/outputs/apk/release/").walk()
            .maxDepth(1)
            .filter { it.isFile }
            .filter { it.extension == "apk" }
            .forEach {
                fileClipTo(it, file("apk/${it.name}"))
            }
        println("move_apk end")
    }

}

println("========================================================project.build.gradle.kts end========================================================")