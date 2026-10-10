import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.plugin.compose)
}

group = "dev.atlassoftware.libs"
version = "1.0.0"

kotlin {
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.coil.network.okhttp)
        }
        commonMain.dependencies {
                implementation(project(":cipoflow-core"))

                // Compose dependencies
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                api(compose.ui)
                api(compose.materialIconsExtended)

                // Navigation Compose
                api(libs.navigation.compose)

                // Coil
                api(libs.coil.compose)
        }
        commonTest.dependencies {
                implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "dev.atlassoftware.cipoflow.compose"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates(group.toString(), "cipoflow-compose", version.toString())
    pom {
        name = "Cipo Flow compose KMM library"
        description = "A Framework to low-code building apps, with compose support"
        inceptionYear = "2025"
        url = "https://github.com/atlas-software-dev/cipo-flow-kmm"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "http://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "albertklik"
                name = "Paulo Figueiro"
                url = "albertklik.github.io"
            }
        }
        scm {
            url = "https://github.com/atlas-software-dev/cipo-flow-kmm"
            connection = "scm:git:git://github.com:atlas-software-dev/cipo-flow-kmm.git"
            developerConnection = "scm:git:ssh://git@github.com/atlas-software-dev/cipo-flow-kmm.git"
        }
    }
}
