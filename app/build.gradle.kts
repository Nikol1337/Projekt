plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.projekt"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.projekt"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

    }
}

dependencies {


    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("com.squareup.picasso:picasso:2.1.1")
    implementation ("com.squareup.retrofit2:retrofit:2.6.2")
    //implementation ("com.android.support:recyclerview-v7:29.0.0")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.6.2")
    implementation ("com.squareup.okhttp3:logging-interceptor:3.10.0")
    implementation ("androidx.room:room-runtime:2.6.1")
    annotationProcessor ("androidx.room:room-compiler:2.6.1")


    implementation ("androidx.camera:camera-core:1.2.2")
    implementation ("androidx.camera:camera-camera2:1.2.2")
    implementation ("androidx.camera:camera-lifecycle:1.2.2")
    implementation ("androidx.camera:camera-video:1.2.2")
    implementation ("com.squareup.picasso:picasso:2.71828")

    implementation ("androidx.camera:camera-view:1.2.2")
    implementation ("androidx.camera:camera-extensions:1.2.2")
    implementation("com.google.code.findbugs:jsr305:2.0.2")

}