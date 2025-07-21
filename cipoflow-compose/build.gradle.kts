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
        commonMain.dependencies {
                //put your multiplatform dependencies here
                implementation(project(":cipoflow-core"))

                // Compose dependencies (managed by the compose plugin)
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                api(compose.ui)
                api(compose.materialIconsExtended)

                // Voyager navigation library dependencies
                api(libs.voyager.navigator)
                api(libs.voyager.screenmodel)
                api(libs.voyager.transitions)

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
//
//signing {
//    val signingKeyId = project.findProperty("signing.keyId")?.toString()
//
//    // Apenas tenta assinar se o ID da chave for encontrado
//    if (signingKeyId != null) {
//        useGpgCmd() // Usa o GPG externo
//        sign(publishing.publications)
//    }
//}

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