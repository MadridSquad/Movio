# --- General Kotlin rules ---
-dontwarn kotlin.**
-keep class kotlin.Metadata { *; }
-keep class kotlin.jvm.internal.** { *; }
-keepclassmembers class **$WhenMappings { *; }

# --- Jetpack Compose ---
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# --- AndroidX Core, AppCompat, WorkManager, Navigation, Paging, Lifecycle ---
-keep class androidx.** { *; }
-dontwarn androidx.**

# --- Material Components ---
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**

# --- Coil (Image loading) ---
-keep class coil.** { *; }
-dontwarn coil.**

# --- Kotlin Serialization (kotlinx) ---
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

# --- WebKit (if using reflection or JS interface) ---
-keep class android.webkit.** { *; }
-dontwarn android.webkit.**

# --- Project-specific (optional, adjust as needed) ---
-keep class com.madrid.presentation.** { *; }

# --- Prevent stripping consumer proguard rules (for library consumers) ---
-keepnames class * {
    @androidx.annotation.Keep *;
}

# --- Logging (optional but safe) ---
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# --- General optimization (most included in default file) ---
# (No additional rules needed unless you encounter issues)