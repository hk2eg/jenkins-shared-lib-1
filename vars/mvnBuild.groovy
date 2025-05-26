def call(String skipTest = 'true') {
    def mvnHome = tool name: 'maven-3.8', type: 'hudson.tasks.Maven$MavenInstallation'
    withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {
        dir('java-jenkins-sample-project') {  // adjust to your actual folder name
            sh "find . -name pom.xml"
            sh "mvn clean package install -Dmaven.test.skip=${skipTest}"
        }
    }
}