import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application

    kotlin("jvm") version "1.4.31"

    //id("com.github.jakemarsden.git-hooks") version "0.0.1"
    //id("com.github.johnrengelman.shadow") version "5.2.0"
    //id("io.gitlab.arturbosch.detekt") version "1.15.0"
}

group = "KordDnD"
version = "1.0"

repositories {
    maven {
        name = "Kotlin Discord"
        url = uri("https://maven.kotlindiscord.com/repository/maven-public/")
    }
}

dependencies {
    implementation("com.kotlindiscord.kord.extensions:kord-extensions:1.4.0-RC7")

    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.github.microutils:kotlin-logging:2.0.3")
    implementation("org.codehaus.groovy:groovy:3.0.4")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation("io.ktor:ktor-client-gson:1.4.1")

}

application {
    // This is deprecated, but the Shadow plugin requires it
    mainClassName = "AppKt"
}

// If you don't want the import, remove it and use org.jetbrains.kotlin.gradle.tasks.KotlinCompile
tasks.withType<KotlinCompile> {
    // Current LTS version of Java
    kotlinOptions.jvmTarget = "11"

    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    kotlinOptions.useIR = true
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "AppKt"
        )
    }
}

// Add task to gradle that builds a fatJar (a jar file that also contains all its dependencies)
task<Jar>("fatJar") {
    group = "application"
    archiveFileName.set("${project.name}.jar")
    manifest.attributes(
        "Main-Class" to application.mainClass.get()
    )
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

java {
    // Current LTS version of Java
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

