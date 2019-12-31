# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile



# 指定代码的压缩级别 0 - 7(指定代码进行迭代优化的次数，在Android里面默认是5，这条指令也只有在可以优化时起作用。)
-optimizationpasses 5
# 混淆时不会产生形形色色的类名(混淆时不使用大小写混合类名)
-dontusemixedcaseclassnames
# 指定不去忽略非公共的库类(不跳过library中的非public的类)
-dontskipnonpubliclibraryclasses
# 指定不去忽略包可见的库类的成员
-dontskipnonpubliclibraryclassmembers
#不进行优化，建议使用此选项，
-dontoptimize
 # 不进行预校验,Android不需要,可加快混淆速度。
-dontpreverify
# 屏蔽警告
-ignorewarnings
# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
# 保护代码中的Annotation不被混淆
-keepattributes *Annotation*
# 避免混淆泛型, 这在JSON实体映射时非常重要
-keepattributes Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
 #优化时允许访问并修改有修饰符的类和类的成员，这可以提高优化步骤的结果。
# 比如，当内联一个公共的getter方法时，这也可能需要外地公共访问。
# 虽然java二进制规范不需要这个，要不然有的虚拟机处理这些代码会有问题。当有优化和使用-repackageclasses时才适用。
#指示语：不能用这个指令处理库中的代码，因为有的类和类成员没有设计成public ,而在api中可能变成public
-allowaccessmodification
#当有优化和使用-repackageclasses时才适用。
-repackageclasses ''
 # 混淆时记录日志(打印混淆的详细信息)
 # 这句话能够使我们的项目混淆后产生映射文件
 # 包含有类名->混淆后类名的映射关系
-verbose


# 广告SDK 文件混淆
-keepattributes *Annotation*
-keep public class com.xnad.sdk.ad.entity.**{*;}
#监听混淆
-keep public interface com.xnad.sdk.ad.outlistener.**{*;}
-keep public interface com.xnad.sdk.ad.listener.**{*;}
-keep public interface com.xnad.sdk.ad.admanager.AdManager{*;}
-keep class com.xnad.sdk.MidasAdSdk{ *;}
-keep class com.xnad.sdk.ad.entity.AdInfo{ *;}
-keep class com.xnad.sdk.http.model.BaseResponse{ *;}
-keep class com.xnad.sdk.config.AdConfig{ *;}
-keepattributes Exceptions,InnerClasses
-keep class com.xnad.sdk.config.AdConfig$*{
<fields>;
<methods>;
}
#加载广告可选参数混淆
-keep class com.xnad.sdk.config.AdParameter{ *;}
-keepattributes Exceptions,InnerClasses
-keep class com.xnad.sdk.config.AdParameter$*{
<fields>;
<methods>;
}
#常量混淆
-keep class com.xnad.sdk.config.Constants{ *;}
-keepattributes Exceptions,InnerClasses
-keep class com.xnad.sdk.config.Constants$*{
<fields>;
<methods>;
}



#====gson====
-keep class sun.misc.Unsafe{*;}
-dontwarn com.google.gson.**
-keep class com.google.gson.**{*;}
-keep class com.google.gson.stream.**{*;}
-keep class com.google.gson.examples.android.model.**{*;}



#穿山甲广告SDK混淆
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep class com.bytedance.** { *; }
-keep class com.ss.android.** { *; }
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
-keep class com.pgl.sys.ces.* {*;}
#牛数埋点SDK混淆
-dontwarn com.xiaoniu.statistic.**
-keep class com.xiaoniu.statistic.**{*;}

-keep class com.bun.miitmdid.** {*;}
-keep class com.bun.miitmdid.core.** {*;}