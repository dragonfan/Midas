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
# 广告SDK 文件混淆
-keepattributes *Annotation*
-keep public class com.xnad.sdk.ad.entity.**{*;}
-keep public interface com.xnad.sdk.ad.outlistener.**{*;}
-keep public interface com.xnad.sdk.ad.listener.**{*;}
-keep public interface com.xnad.sdk.ad.admanager.AdManager{*;}
-keep class com.xnad.sdk.MidasAdSdk{ *;}
-keep class com.xnad.sdk.ad.entity.AdInfo{ *;}
-keep class com.xnad.sdk.http.model.BaseResponse{ *;}
-keep class com.xnad.sdk.config.AdParameter{ *;}
-keep class com.xnad.sdk.config.ADConfigBuild{ *;}

-keepattributes Exceptions,InnerClasses
-keep class com.xnad.sdk.config.AdParameter$*{
<fields>;
<methods>;
}


#====okhttp====
-libraryjars libs/okhttp-2.7.0.jar
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-keep interface okhttp3.**{*;}

-keepattributes Signature
-keepattributes *Annotation*
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.**{*;}
-keep interface com.squareup.okhttp.**{*;}

#====okio====
-libraryjars libs/okio-1.6.0.jar
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**
-keep class okio.**{*;}
-keep interface okio.**{*;}

#====gson====
-libraryjars libs/gson-2.2.1.jar
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