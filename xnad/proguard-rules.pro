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
-keep public class com.xnad.sdk.entity.**{*;}
-keep public com.xnad.sdk.ad.outlistener.**{*;}
-keep class com.xnad.sdk.ad.MidasAdSdk{ *;}
-keep class com.xnad.sdk.http.model.BaseResponse{ *;}
-keep class com.xnad.sdk.config.AdParameter{ *;}
-keep class com.xnad.sdk.config.ADConfigBuild{ *;}

-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
-keep class com.pgl.sys.ces.* {*;}
#牛数埋点SDK混淆
-dontwarn com.xiaoniu.statistic.**
-keep class com.xiaoniu.statistic.**{*;}