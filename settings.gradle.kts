pluginManagement {
	repositories {
		maven("https://maven.fabricmc.net") {
            name = "FabricMC"
        }
		gradlePluginPortal()
	}
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "Cortex"
