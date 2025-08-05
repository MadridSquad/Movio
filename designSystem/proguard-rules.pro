# --- General Kotlin rules ---
-dontwarn kotlin.**
-keep class kotlin.Metadata { *; }
-keep class kotlin.jvm.internal.** { *; }
-keepclassmembers class **$WhenMappings { *; }

# --- Jetpack Compose ---
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# --- AndroidX (UI, Material, Foundation, Activity, etc) ---
-keep class androidx.** { *; }
-dontwarn androidx.**

# --- Material Components (Material & Material3) ---
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**

# --- Coil (Image loading) ---
-keep class coil.** { *; }
-dontwarn coil.**

# --- Project-specific (optional, adjust as needed) ---
-keep class com.madrid.designSystem.** { *; }

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