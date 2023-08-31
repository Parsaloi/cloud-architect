// Using Kotlin DSL
plugins {
	id("java")
}

group = "aws.test"
version = "1.0-SNAPSHOT"

sourceCompatibility JavaVersion.VERSION_11
targetCompatibility JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation(platform("software.amazon.awssdk:bom:2.20.56"))
	implementation("software.amazon,awssdk:s3")
	implementation("software.amazon.awssdk:sso")
	implementation("software.amazon.awssdk:ssoidc")
	implementation("org.slf4j-simple:2.0.5")
	testImplementation(platform("org.junit:junit-bom:5.9.1"))
	testImplemntation("org.junit.jupiter:junit-jupiter")
}

test {
	useJUnitPlatform()
}
