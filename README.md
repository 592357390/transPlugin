## 使用方式
### 1、在项目级别的build.gralde中

```java
buildscript {

    repositories {
        google()
        jcenter()

        maven {
            url uri("http://nexus.tttinner.com:8888/nexus/content/repositories/releases/")
            credentials {
                username = "..."
                password = "..."
            }
        }
    }
    dependencies {

        classpath 'com.language.trans:plugin:1.0.5'

    }
}
```

### 2、在module的build.gradle中
```java

apply plugin: 'com.trans.plugin'



trans {
    //(配置目标语言,可配置多个目标语言)
    targetLanguage = ['en', 'jp'] 
}

```

当前可支持语言包括(
AUTO, // 自动检测语种
ZH, // 中文
EN, // 英语
JP, // 日语
FRA, // 法语
KOR, // 韩语
DE, // 德语
RU, // 俄语
)

### 开始翻译 
1、在gradle中找到当前module任务，在other中，找到trans任务，点击执行
2、在命令行执行 gradle trans


