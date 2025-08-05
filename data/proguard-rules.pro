# --- General Kotlin rules ---
-dontwarn kotlin.**
-keep class kotlin.Metadata { *; }
-keep class kotlin.jvm.internal.** { *; }
-keepclassmembers class **$WhenMappings { *; }

# --- Room Database ---
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**
-keepclassmembers class * {
    @androidx.room.* <methods>;
}

# --- Retrofit & Gson ---
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**
-keepclassmembers class * {
    @retrofit2.http.* <methods>;
}
-keepattributes Signature,RuntimeVisibleAnnotations,RuntimeInvisibleAnnotations

# --- OkHttp ---
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**

# --- Serialization (Kotlinx) ---
-keep class kotlinx.serialization.** { *; }
-dontwarn kotlinx.serialization.**

# --- Hilt / Dagger ---
-keep class dagger.** { *; }
-dontwarn dagger.**
-keep class javax.inject.** { *; }
-dontwarn javax.inject.**
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory { *; }
-keep class * extends androidx.lifecycle.ViewModel
-keep class * implements dagger.hilt.internal.GeneratedComponent { *; }

# --- AndroidX / AppCompat / Preferences ---
-keep class androidx.** { *; }
-dontwarn androidx.**

# --- Project-specific (adjust if needed) ---
-keep class com.madrid.data.** { *; }

# --- Prevent stripping consumer proguard rules (for library consumers) ---
-keepnames class * {
    @androidx.annotation.Keep *;
}

# --- Logging (optional) ---
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# --- General optimization (if needed, most included in default file) ---
# (No additional rules needed unless you encounter issues)