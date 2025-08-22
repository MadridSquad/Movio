# --- General Android & Kotlin Rules ---
-dontwarn kotlin.**
-keep class kotlin.Metadata { *; }
-keep class kotlin.jvm.internal.** { *; }
-keepclassmembers class **$WhenMappings { *; }

# --- Compose ---
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# --- Hilt / Dagger ---
-keep class dagger.** { *; }
-dontwarn dagger.**
-keep class javax.inject.** { *; }
-dontwarn javax.inject.**
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory { *; }
-keep class * extends androidx.lifecycle.ViewModel

# Hilt Generated Components
-keep class * extends dagger.hilt.internal.GeneratedComponent { *; }
-keep class * implements dagger.hilt.internal.GeneratedComponent { *; }
-keep class * extends androidx.lifecycle.ViewModel

# --- Firebase / Google Services ---
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

# --- Retrofit & Gson ---
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# Retrofit model classes (add your package if needed)
-keepclassmembers class * {
    @retrofit2.http.* <methods>;
}
-keepattributes Signature,RuntimeVisibleAnnotations,RuntimeInvisibleAnnotations

# --- OkHttp ---
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }

# --- Room ---
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**
-keepclassmembers class * {
    @androidx.room.* <methods>;
}

# --- SLF4J ---
-dontwarn org.slf4j.**
-keep class org.slf4j.** { *; }

# --- Serialization ---
-keep class kotlinx.serialization.** { *; }
-dontwarn kotlinx.serialization.**

# --- Misc: AndroidX, SplashScreen, WorkManager, etc. ---
-keep class androidx.** { *; }
-dontwarn androidx.**

-keep class com.madrid.movio.** { *; }

# --- Keep MainActivity & Entry Points ---
-keep class * extends android.app.Activity
-keep class * extends android.app.Application

# --- Prevent removal of test code in debug builds (optional, for debug only) ---
#-keep class *Test* { *; }

# --- Jacoco (for code coverage, if used in release builds) ---
-dontwarn org.jacoco.**
-keep class org.jacoco.** { *; }

# --- Exclude generated classes and resources ---
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

-keep enum com.madrid.presentation.viewModel.seeAll.movies.factory.SeeAllMoviesType { *; }
-keep enum com.madrid.presentation.viewModel.seeAll.tvShows.factory.SeeAllTvShowType { *; }
-keep enum com.madrid.presentation.viewModel.libraryViewModel.viewAll.factory.ViewAllType { *; }


# --- General Android Proguard optimizations ---
# (The default file proguard-android-optimize.txt already includes most optimizations, this is only to extend)