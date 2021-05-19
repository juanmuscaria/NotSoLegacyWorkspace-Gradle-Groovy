import net.minecraftforge.gradle.user.UserExtension

buildscript {
    repositories {
        mavenCentral()
        maven {
            name = "forge"
            url = java.net.URI("https://maven.minecraftforge.net/")
        }
        maven {
            name = "sonatype"
            url = java.net.URI("https://oss.sonatype.org/content/repositories/snapshots/")
        }
        maven {
            name = "github"
            url = java.net.URI("https://github.com/juanmuscaria/maven/raw/master")
        }
    }
    dependencies {
        classpath("net.minecraftforge.gradle:ForgeGradle:1.2-1.3.0-SNAPSHOT")
    }
}

apply(plugin = "forge")

version = "1.0"
group= "com.yourname.modid" // http://maven.apache.org/guides/mini/guide-naming-conventions.html
this.setProperty("archivesBaseName", "modidArchieve")
val minecraft = extensions.findByType<UserExtension>()
val sourceSets = extensions.findByType<SourceSetContainer>()

//Equivalent of minecraft {}
configure<UserExtension> {
    version = "1.7.10-10.13.4.1614-1.7.10"
    runDir = "eclipse"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // you may put jars on which you depend on in ./libs
    // or you may define them like so..
    //compile "some.group:artifact:version:classifier"
    //compile "some.group:artifact:version"
      
    // real examples
    //compile 'com.mod-buildcraft:buildcraft:6.0.8:dev'  // adds buildcraft to the dev env
    //compile 'com.googlecode.efficient-java-matrix-library:ejml:0.24' // adds ejml to the dev env

    // for more info...
    // http://www.gradle.org/docs/current/userguide/artifact_dependencies_tutorial.html
    // http://www.gradle.org/docs/current/userguide/dependency_management.html

}

tasks.named<ProcessResources>("processResources") {
    // this will ensure that this task is redone when the versions change.
    inputs.property("version", project.version)
    inputs.property("mcversion", minecraft?.version)

    // replace stuff in mcmod.info, nothing else
    from(sourceSets!!["main"].resources.srcDirs) {
        include("mcmod.info")

        // replace version and mcversion
        //expand ("version":project.version, "mcversion":minecraft?.version)
        expand(mapOf<String, Any>("version" to project.version, "mcversion" to (minecraft?.version) as Any))
    }

    // copy everything else, thats not the mcmod.info
    from(sourceSets["main"].resources.srcDirs) {
        exclude ("mcmod.info")
    }
}

