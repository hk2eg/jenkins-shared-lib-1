package org.iti

def run_pipeline(Map args = [:]) {
    def script = args.script
    def docker_user = args.docker_user
    def docker_pass = args.docker_pass
    def image = args.default_image
    def version = args.version
    def skipTest = args.skipTest

    script.node('agent-1') {
        script.tool name: "java-8", type: 'hudson.model.JDK'
        script.tool name: "maven-3.8", type: 'hudson.tasks.Maven$MavenInstallation'

        script.withCredentials([
            script.usernamePassword(
                credentialsId: 'docker-hub-login',
                usernameVariable: 'DOCKER_USR',
                passwordVariable: 'DOCKER_PSW'
            )
        ]) {
            try {
                script.stage("VM info") {
                    script.echo "Agent IP: ${script.vmIp()}"
                }

                script.stage("Build java app") {
                    script.sayHello('ITI')
                    script.mvnBuild(skipTest)
                }

                script.stage("build docker image") {
                    script.dockerLogin(docker_user, docker_pass)
                    script.dockerBuild(image, version)
                }

                script.stage("push docker image") {
                    script.dockerLogin(docker_user, docker_pass)
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
