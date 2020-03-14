plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(29)
    buildToolsVersion = "29.0.3"
    defaultConfig {
        applicationId = "gal.mpena.hermes.oembed"
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")
    sourceSets["test"].java.srcDir("src/test/kotlin")
    sourceSets["androidTest"].java.srcDir("src/androidTest/kotlin")
}

dependencies {

    implementation(kotlin("stdlib-jdk8"))
    
    //Android X libraries
    implementation ("androidx.appcompat:appcompat:1.1.0")
    implementation ("androidx.core:core-ktx:1.2.0")
    implementation ("androidx.constraintlayout:constraintlayout:1.1.3")
    
    //Material Design libraries
    implementation ("com.google.android.material:material:1.1.0-rc02")

    //Testing libraries
    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.2.0")
}
