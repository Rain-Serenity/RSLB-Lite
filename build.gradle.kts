plugins {
    id("java-library")
    id("com.gradleup.shadow") version "9.6.1"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.23"
    id("xyz.jpenilla.run-paper") version "3.1.0"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    paperweight.paperDevBundle("26.3.build.+")

    compileOnly("org.jetbrains:annotations:26.1.0")
    compileOnly("org.projectlombok:lombok:1.18.48")
    annotationProcessor("org.projectlombok:lombok:1.18.48")

    implementation("org.spongepowered:configurate-yaml:4.2.0")
    implementation("org.spongepowered:configurate-core:4.2.0")
    implementation("com.squareup.okhttp3:okhttp:5.5.0")
    implementation("com.squareup.okio:okio-jvm:3.18.2")
    implementation("com.google.code.gson:gson:2.14.0")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

tasks {
    shadowJar {
        archiveBaseName = "RSLB-Lite"

        mergeServiceFiles()

        val relocate = listOf(
            "org.spongepowered.configurate" to "com.rserene.chosen.server.libs.configurate",
            "okhttp3" to "com.rserene.chosen.server.libs.okhttp",
            "okio" to "com.rserene.chosen.server.libs.okio",
            "com.google.gson" to "com.rserene.chosen.server.libs.gson",
            "kotlin" to "com.rserene.chosen.server.libs.kotlin"
        )
        relocate.forEach { (from, to) -> relocate(from, to) }

        exclude("META-INF/maven/**")
        exclude("META-INF/versions/**")
        exclude("module-info.class")
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/kotlin-stdlib.kotlin_module")
        exclude("META-INF/okhttp3.kotlin_module")
    }

    build {
        dependsOn(shadowJar)
    }

    runServer {
        minecraftVersion("26.3")
        jvmArgs("-Xms2G", "-Xmx2G")
    }

    processResources {
        val props = mapOf("version" to version)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}
