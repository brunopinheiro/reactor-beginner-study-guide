plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.21")
    id("io.spring.dependency-management").version("1.0.6.RELEASE")
    application
}

repositories {
    jcenter()
}

dependencyManagement {
    imports {
        mavenBom("io.projectreactor:reactor-bom:Bismuth-RELEASE")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.projectreactor:reactor-core")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    mainClassName = "reactive.AppKt"
}
