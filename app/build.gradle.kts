import java.util.regex.Pattern.compile

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.arturomarmolejo.nycschoolscompose"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.arturomarmolejo.nycschoolscompose"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


}

dependencies {


    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    /**
     * GSON - A Java library that can be used to convert Java Objects into their JSON representation and vice versa.
     * Docs: https://www.javadoc.io/doc/com.google.code.gson/gson/latest/index.html
     **/
    implementation("com.google.code.gson:gson:2.10.1")

    /*
     * Retrofit - A type-safe HTTP client for Android and Java.
     * Docs: https://square.github.io/retrofit/2.x/retrofit/
     */
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    /**
     * Okhttp - A modern HTTP client for Android and Java.
     * Docs: https://square.github.io/okhttp/4.x/okhttp/
     */
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.6")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.6")

    /**
     * HILT - A dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.
     * Docs: https://dagger.dev/hilt/
     */
    implementation("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-android-compiler:2.45")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    /**
     * Coroutines - A library for writing asynchronous, non-blocking code in a more concise and structured way.
     * Docs: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/
     */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    /**
     * SlidingPaneLayout - A layout that allows a user to slide a pane out from the side of the screen to reveal content.
     * Docs: https://developer.android.com/reference/androidx/slidingpanelayout/widget/SlidingPaneLayout
     */
    implementation("androidx.slidingpanelayout:slidingpanelayout:1.2.0")

    /**
     * Mockk - A library for performing unit tests that allows the mocking of custom objects
     */
    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("androidx.arch.core:core-testing:2.1.0")

}



