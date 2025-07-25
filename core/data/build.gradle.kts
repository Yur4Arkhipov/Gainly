plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.jacqulin.gainly.core.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(project(":core:domain"))
    implementation(project(":core:util"))

    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.gson)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    //Datastore
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.jwtdecode) // for jwt token decode

    testImplementation(libs.junit4)
    testImplementation(libs.mockito.core) // for mock usecases
    testImplementation(libs.kotlinx.coroutines.test) // for runTest()

    androidTestImplementation(libs.androidx.test.rules)  // this two for instrumented test
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.core)   // for provide app context
    androidTestImplementation(libs.kotlinx.coroutines.test) // for runTest()
    androidTestImplementation(libs.androidx.test.ext.junit) // for assertEquals()
}