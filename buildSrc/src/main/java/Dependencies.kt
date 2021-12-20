object Sdk {
    const val MIN_SDK_VERSION = 26
    const val TARGET_SDK_VERSION = 31
    const val COMPILE_SDK_VERSION = 31
}

object Versions {
    const val ANDROIDX_TEST_EXT = "1.1.1"
    const val ANDROIDX_TEST = "1.2.0"
    const val APPCOMPAT = "1.2.0"
    const val CONSTRAINT_LAYOUT = "2.0.2"
    const val CORE_KTX = "1.2.0"
    const val ESPRESSO_CORE = "3.2.0"
    const val JUNIT = "4.13"
    const val KTLINT = "0.37.2"
    const val NAVIGATION = "2.3.5"
    const val COMPOSE = "1.0.5"
    const val KOTLIN_COROUTINES = "1.5.2"
    const val KOTLIN = "1.5.31"
}

object BuildPluginsVersion {
    const val AGP = "7.0.0"
    const val DETEKT = "1.10.0"
    const val KOTLIN = "1.5.31"
    const val KTLINT = "9.2.1"
    const val VERSIONS_PLUGIN = "0.28.0"
}

object SupportLibs {
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
    const val ANDROIDX_CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val ANDROIDX_CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val NAVIGATION_FRAGMENT_KTX =
        "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"
    const val LIFECYCLE_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:2.2.0"
    const val DATASTORE = "androidx.datastore:datastore-preferences:1.0.0"
    const val LOCATION_LOCUS = "com.github.BirjuVachhani:locus-android:3.0.1"
    const val GOOGLE_LOCATION = "com.google.android.gms:play-services-location:19.0.0"
}

object TestingLib {
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
    const val STRIKT = "io.strikt:strikt-core:0.32.0"
    const val MOCKK = "io.mockk:mockk:1.12.0"
}

object AndroidTestingLib {
    const val ANDROIDX_TEST_RULES = "androidx.test:rules:${Versions.ANDROIDX_TEST}"
    const val ANDROIDX_TEST_RUNNER = "androidx.test:runner:${Versions.ANDROIDX_TEST}"
    const val ANDROIDX_TEST_EXT_JUNIT = "androidx.test.ext:junit:${Versions.ANDROIDX_TEST_EXT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
}

object NetworkingLib {
    const val RETROFIT = "com.squareup.retrofit2:retrofit:2.9.0"
    const val KTOR_CLIENT = "io.ktor:ktor-client-android:1.6.5"
    const val KTOR_CLIENT_CIO = "io.ktor:ktor-client-cio:1.6.5"
    const val KTOR_SERIALIZATION_JVM = "io.ktor:ktor-client-serialization-jvm:1.6.5"
    const val KTOR_SERIALIZATION = "io.ktor:ktor-client-serialization:1.6.5"
    const val KTOR_LOGGING = "io.ktor:ktor-client-logging-jvm:1.6.5"
}

object KotlinLibs {
    const val COROUTINES =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLIN_COROUTINES}"
    const val COROUTINES_ANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLIN_COROUTINES}"
    const val COROUTINES_TEST =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.KOTLIN_COROUTINES}"
}

object DaggerLibs {
    const val MAIN = "com.google.dagger:dagger:2.40.5"
    const val COMPILER = "com.google.dagger:dagger-compiler:2.40.5"
}

object ComposeLibs {
    const val ACTIVITY = "androidx.activity:activity-compose:1.4.0"
    const val MATERIAL = "androidx.compose.material:material:1.0.5"
    const val ANIMATION = "androidx.compose.animation:animation:1.0.5"
    const val UI_TOOLING = "androidx.compose.ui:ui-tooling:1.0.5"
    const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"
    const val UI_TEST = "androidx.compose.ui:ui-test-junit4:1.0.1"
    const val MATERIAL_THEME_ADAPTER = "com.google.android.material:compose-theme-adapter:1.0.5"
    const val APPCOMPAT_THEME_ADAPTER = "com.google.accompanist:accompanist-appcompat-theme:0.16.0"
    const val COIL = "io.coil-kt:coil-compose:1.4.0"
    const val ACCOMPANIST_PERMISSIONS = "com.google.accompanist:accompanist-permissions:0.21.5-rc"
}