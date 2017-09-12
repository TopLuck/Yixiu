# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\adt-bundle-windows-x86_64-20140702\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#极光推送
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

#==================gson && protobuf==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}
#======= 百度地图=========
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**
# Retrofit2
	-dontwarn retrofit2.**
	-keep class retrofit2.** { *; }
	-keep class retrofit2.**
	-keep class com.base.frame.net.retrofit.converter.gson.**{ *; }
	-keep class com.base.frame.net.retrofit.converter.gson.**
	-keepattributes Signature
	-keepattributes Exceptions
# OkHttp
	-dontwarn okhttp3.**
	-keep class okhttp3.** { *; }

	-dontwarn com.squareup.okhttp.**
	-keep class com.squareup.okhttp.** { *; }
	-dontwarn okio.**
# UMeng
	-keepclassmembers class * {
       public <init> (org.json.JSONObject);
    }
    -keep public class com.yixiu.carrepair.R$*{
    public static final int *;
    }
    -keepclassmembers enum * {
        public static **[] values();
        public static ** valueOf(java.lang.String);
    }
    #WeiXinPay
    -keep class com.tencent.mm.opensdk.** {
       *;
    }
    -keep class com.tencent.wxop.** {
       *;
    }

    -keep class com.tencent.mm.sdk.** {
       *;
    }