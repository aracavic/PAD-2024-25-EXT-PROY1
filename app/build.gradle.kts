plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.proyectoextraordinario"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.proyectoextraordinario"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    implementation("androidx.room:room-runtime:2.5.2")
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.1.0")
    implementation(libs.room.common.jvm)
    annotationProcessor("androidx.room:room-compiler:2.5.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.work:work-runtime:2.8.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}