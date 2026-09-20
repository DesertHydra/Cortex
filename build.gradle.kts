plugins {
	`maven-publish`
	alias(libs.plugins.fabric.loom)
}

version = "${project.version}+${libs.versions.minecraft.get()}"

val modID = providers.gradleProperty("mod_id")

repositories {
	exclusiveContent {
		forRepository { maven("https://maven.blamejared.com") }
		filter { includeGroup("mezz.jei") }
	}
}

// All the dependencies are declared at gradle/libs.version.toml and referenced with "libs.<id>"
// See https://docs.gradle.org/current/userguide/platforms.html for information on how version catalogs work.
dependencies {
	minecraft(libs.minecraft)
	implementation(libs.fabric.loader)

	implementation(libs.fabric.api)

	localRuntime(libs.jei.fabric)
}

fabricApi {
	configureDataGeneration {
		client = true
		modId = modID
		strictValidation = true
	}
}

loom {
	mods {
		create(modID.get()) {
			sourceSet("main")
		}
	}

	accessWidenerPath = modID.map { file("src/main/resources/${it}.classtweaker") }
}

tasks.named<ProcessResources>("processResources") {
	val expandProps = mapOf<String, Any>(
	    "version" to version
    )

	inputs.properties(expandProps)

	filesMatching("fabric.mod.json") {
		expand(expandProps)
	}

	// It would be super confusing if the lang/README was on the JAR file
	exclude("assets/${modID.get()}/lang/README.md")
}

tasks.withType<JavaCompile>().configureEach {
	options.encoding = "UTF-8"
	options.release = libs.versions.java.map { it.toInt() }
}

java {
	withSourcesJar()
    toolchain {
        languageVersion = libs.versions.java.map(JavaLanguageVersion::of)
        vendor = JvmVendorSpec.MICROSOFT
    }
}

// If you plan to use a different file for the license, don't forget to change the file name here!
tasks.named<Jar>("jar") {
	from("LICENSE") {
		rename("LICENSE", "LICENSE_${modID.get()}")
	}
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
		}
	}

	// See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
	repositories {
		// Add repositories to publish to here.
		// Notice: This block does NOT have the same function as the block in the top level.
		// The repositories here will be used for publishing your artifact, not for
		// retrieving dependencies.
	}
}
