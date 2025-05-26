def call(String skipTest = 'true') {
    def mvnHome = tool name: 'maven-3.8'
    withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {
        // Show workspace contents
        sh 'echo "Workspace contents:" && ls -la && find . -name pom.xml'

        // You must manually dir() into the actual folder containing pom.xml
        // Example: if the pom.xml is at ./src/myapp/pom.xml
        // Replace 'src/myapp' with the correct path
        dir('.') {
            sh "mvn clean package install -Dmaven.test.skip=${skipTest}"
        }
    }
}
