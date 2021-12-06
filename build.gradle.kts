import org.jetbrains.kotlin.ir.backend.js.compile

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String by project
val kmongo_version: String by project
val jbcrypt_version:String by project


plugins {
    application
    kotlin("jvm") version "1.5.31"

}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

}

tasks.create("stage") {
    dependsOn("installDist")
}


repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-websockets:$ktor_version")
    implementation("io.ktor:ktor-server-sessions:$ktor_version")
    implementation("io.ktor:ktor-gson:$ktor_version")
    implementation("io.ktor:ktor-server-host-common:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-auth-jwt:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")

    //KMongo
    implementation("org.litote.kmongo:kmongo:$kmongo_version")
    implementation("org.litote.kmongo:kmongo-coroutine:$kmongo_version")

    //Koin Core features
    implementation ("io.insert-koin:koin-core:$koin_version")
    implementation ("io.insert-koin:koin-ktor:$koin_version")
    implementation ("io.insert-koin:koin-logger-slf4j:$koin_version")

    //Bcrypt
    //implementation("org.mindrot.jbcrypt.BCrypt:$jbcrypt_version")

    //gson
    testImplementation("com.google.code.gson:gson:2.8.9")
    //Koin
    testImplementation ("io.insert-koin:koin-test:$koin_version")
    //Ktor test
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    //Kotlin test
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
    //Truth
    testImplementation( "com.google.truth:truth:1.1.3")

}