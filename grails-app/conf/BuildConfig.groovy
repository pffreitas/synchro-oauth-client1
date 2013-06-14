grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6

grails.project.dependency.resolution = {
    inherits("global") {
    }
    log "error" 
    checksums true 
    legacyResolve true 

    repositories {
        inherits true 

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        mavenRepo "http://snapshots.repository.codehaus.org"
        mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        mavenRepo "http://repository.jboss.com/maven2/"
    }
	
    dependencies {
		runtime 'com.google.oauth-client:google-oauth-client:1.15.0-rc'
		runtime 'com.google.http-client:google-http-client-jackson2:1.15.0-rc'
    }

    plugins {
		
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.8.0"
        runtime ":resources:1.1.6"

        build ":tomcat:$grailsVersion"

        runtime ":database-migration:1.2.1"

        compile ':cache:1.0.1'
    }
}
