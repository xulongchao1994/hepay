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
#指定代码的压缩级别
-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames

#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
#优化 不优化输入的类文件
-dontoptimize
#预校验
-dontpreverify
 #混淆时是否记录日志
-verbose
 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保护注解
-keepattributes *Annotation*
#忽略警告
-ignorewarning
#保留crash日志的行号
-keepattributes SourceFile,LineNumberTable

#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
 native <methods>;
}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
 public <init>(android.content.Context, android.util.AttributeSet);
}

#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
 public void *(android.view.View);
}

-keep public class * extends android.view.View {
 public <init>(android.content.Context);
 public <init>(android.content.Context, android.util.AttributeSet);
 public <init>(android.content.Context, android.util.AttributeSet, int);
 public void set*(...);
}

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
 public static final android.os.Parcelable$Creator *;
}

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
 static final long serialVersionUID;
 private static final java.io.ObjectStreamField[] serialPersistentFields;
 !static !transient <fields>;
 !private <fields>;
 !private <methods>;
 private void writeObject(java.io.ObjectOutputStream);
 private void readObject(java.io.ObjectInputStream);
 java.lang.Object writeReplace();
 java.lang.Object readResolve();
}

#保持枚举 enum 类不被混淆
-keepclassmembers enum * {
 public static **[] values();
 public static ** valueOf(java.lang.String);
}

-keepclassmembers class * {
 public void *ButtonClicked(android.view.View);
}

#不混淆资源类
-keepclassmembers class **.R$* {
 public static <fields>;
}
#避免混淆泛型 如果混淆报错建议关掉
#-keepattributes Signature

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService

# 保留support下的所有类及其内部类
-keep class android.support.** {*;}

# 保留继承的
-dontwarn android.support.**
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**
# 保留R下面的资源
-keep class **.R$* {*;}

# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# webView处理，项目中没有使用到webView忽略即可
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}

-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}
-keep class com.feidu.hechaung_img.view.**{*;}
#防止inline
-dontoptimize
#Butter Knife
-dontwarn butterknife.**
-keep class butterknife.**{*;}
#RoundProgressBar
-dontwarn com.octopus.round_progressbar.**
-keep class com.octopus.round_progressbar.**{*;}
#LRecyclerview
-dontwarn com.github.jdsjlzx.**
-keep class com.github.jdsjlzx.progressindicator.indicators.** { *; }
#AVLoadingIndicatorView
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }
#okhttp-3.4.0
-keep class okhttp3.**{*;}
#okio-1.9.0
-keep class okio.**{*;}
#kotlin-stdlib-1.1.2-4
#kotlin-stdlib-jrb7-1.1.2-4
-keep class kotlin.**{*;}
#glide-3.7.0
-keep class com.bumptech.glide.**{*;}
#glide-transformations-2.0.2
-keep class jp.wasabeef.glide.transformations.**{*;}
# tbs_sdk
-dontwarn com.tencent.**
-keep class com.tencent.**{*;}
#baidulbs-android
-dontwarn com.baidu.**
-keep class com.baidu.**{*;}
#retrofit2
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
##京东开普勒
#-keep class com.kepler.**{*;}
#-dontwarn com.kepler.**
#-keep class com.jingdong.jdma.**{*;}
# -dontwarn com.jingdong.jdma.**
#-keep class com.jingdong.crash.**{*;}
#-dontwarn com.jingdong.crash.**
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }

#AutoVerticalTextview
-keep class com.paradoxie.autoscrolltextview.**{*;}

#极光推送
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

#百度语音识别
-keep class com.baidu.tts.**{*;}
-keep class com.baidu.speechsynthesizer.**{*;}

#微信sdk
-keep class com.tencent.mm.opensdk.** {
*;
}

-keep class com.tencent.wxop.** {
*;
}

-keep class com.tencent.mm.sdk.** {
*;
}
#阿里云
#基线包使用，生成mapping.txt
-printmapping mapping.txt
#生成的mapping.txt在app/build/outputs/mapping/release路径下，移动到/app路径下
#修复后的项目使用，保证混淆结果一致
#-applymapping mapping.txt
#hotfix
-keep class com.taobao.sophix.**{*;}
-keep class com.ta.utdid2.device.**{*;}
#防止inline
-dontoptimize

-keepclassmembers class com.hechuang.hepay.base.HePayApplication {
    public <init>();
}
# 如果不使用android.support.annotation.Keep则需加上此行
# -keep class com.my.pkg.SophixStubApplication$RealApplicationStub



#sharesdk 分享
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-keep class m.framework.**{*;}
-dontwarn cn.sharesdk.**
-dontwarn com.sina.**
-dontwarn com.mob.**
-dontwarn **.R$*