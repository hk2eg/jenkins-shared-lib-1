def call(script) {
    def image    = "hk2802/java-mvn-sample"
    def version  = script.params.VERSION ?: script.env.BUILD_NUMBER
    def skipTest = script.params.TEST ?: 'true'

    script.node('agent-1') {
        script.tool name: "java-8"
        script.tool name: "maven-3.8"

        script.withCredentials([script.usernamePassword(
            credentialsId: 'docker-hub-login',
            usernameVariable: 'DOCKER_USR',
            passwordVariable: 'DOCKER_PSW'
        )]) {
            try {
                script.stage('Checkout App Repo') {
                    script.checkout script.scm
                }

                script.stage("VM info") {
                    script.echo "Agent IP: ${script.vmIp()}"
                }

                script.stage("Build java app") {
                    script.sayHello('ITI')
                    script.mvnBuild(skipTest)
                }

                script.stage("Build Docker Image") {
                    script.dockerLogin(script.env.DOCKER_USR, script.env.DOCKER_PSW)
                    script.dockerBuild(image, version)
                }

                script.stage("Push Docker Image") {
                    script.dockerLogin(script.env.DOCKER_USR, script.env.DOCKER_PSW)
                    script.dockerPush(image, version)
                }

            } catch (err) {
                script.echo "Error: ${err.getMessage()}"
                script.currentBuild.result = 'FAILURE'
                throw err
            } finally {
                script.echo "Clean the Workspace"
                script.cleanWs()
            }
        }
    }
}
