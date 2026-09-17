plugins {
    id("java-library")
    id("com.gradleup.shadow") version "9.6.1"
    id("xyz.jpenilla.run-paper") version "3.1.0"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.3.build.+")
    compileOnly(files("libs/nms-26.3.jar"))
    compileOnly("com.mojang:authlib:10.0.77")
    compileOnly("io.netty:netty-all:4.2.16.Final")

    compileOnly("org.jetbrains:annotations:26.1.0")
    compileOnly("org.projectlombok:lombok:1.18.46")
    annotationProcessor("org.projectlombok:lombok:1.18.46")

    implementation("org.spongepowered:configurate-yaml:4.2.0")
    implementation("org.spongepowered:configurate-core:4.2.0")
    implementation("org.yaml:snakeyaml:2.6")
    implementation("com.squareup.okhttp3:okhttp:5.4.0")
    implementation("com.squareup.okio:okio-jvm:3.18.1")
    implementation("org.ow2.asm:asm:9.10.1")
    implementation("org.ow2.asm:asm-commons:9.10.1")
    implementation("com.google.code.gson:gson:2.14.0")
    implementation("com.mojang:brigadier:1.3.11")
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
            "org.objectweb.asm" to "com.rserene.chosen.server.libs.asm",
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
