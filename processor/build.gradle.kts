plugins {
  kotlin("jvm")
  id("com.google.devtools.ksp")
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("com.google.auto.service:auto-service-annotations:1.0.1")
  implementation("com.google.devtools.ksp:symbol-processing-api:1.7.10-1.0.6")
  ksp("dev.zacsweers.autoservice:auto-service-ksp:1.0.0")
}