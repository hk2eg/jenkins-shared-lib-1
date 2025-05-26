def call(String skipTest) {
    def mvnHome = tool name: 'maven-3.8', type: 'hudson.tasks.Maven$MavenInstallation'
    withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {
        sh "mvn clean package install -Dmaven.test.skip=${skipTest}"
    }
}