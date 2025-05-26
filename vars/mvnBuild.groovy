def call(String skipTest = 'true') {
    def mvnHome = tool name: 'maven-3.8'
    withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {
        script {
            def pomPath = sh(script: "find . -name pom.xml | head -n1", returnStdout: true).trim()
            def pomDir = pomPath.replaceAll('/pom.xml$', '')
            echo "Using pom at: ${pomPath}"

            dir(pomDir) {
                sh "mvn clean package install -Dmaven.test.skip=${skipTest}"
            }
        }
    }
}
