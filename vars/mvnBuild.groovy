def call(String skipTest = 'true') {
    def mvnHome = tool name: 'maven-3.8'
    withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {
        // Ensure you're in the directory that contains pom.xml
        sh 'find . -name pom.xml'  // Optional: debug output
        dir('java-jenkins-sample-project') {
            sh "mvn clean package install -Dmaven.test.skip=${skipTest}"
        }
    }
}
