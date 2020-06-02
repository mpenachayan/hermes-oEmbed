plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion('android-R')
    buildToolsVersion = "30.0.0 rc4"
    defaultConfig {
        applicationId = "gal.mpena.hermes.oembed"
        minSdkVersion(21)
        targetSdkVersion('R')
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
    implementation ("androidx.appcompat:appcompat:1.2.0-rc01")
    implementation ("androidx.core:core-ktx:1.3.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.0.0-beta6")
    
    //Material Design libraries
    implementation ("com.google.android.material:material:1.2.0-beta01")

    //Testing libraries
    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.2-rc01")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.3.0-rc01")
}
