// vars/pypipeline.groovy
def call(Map config = [:]) {
  // allow override of node label, defaults, etc.
  String nodeLabel   = config.nodeLabel   ?: 'agent-1'
  String image       = config.image       ?: 'hk2802/flask-sample'
  String credentials = config.credentials ?: 'docker-hub-login'

  node(nodeLabel) {
    stage('Checkout App') {
      checkout scm
    }

    withCredentials([usernamePassword(
        credentialsId: credentials,
        usernameVariable: 'DOCKER_USR',
        passwordVariable: 'DOCKER_PSW'
    )]) {
      // Build
      stage('Build Docker Image') {
        dockerLogin(env.DOCKER_USR, env.DOCKER_PSW)
        dockerBuild(image, "v${env.BUILD_NUMBER}")
      }

      // Push
      stage('Push Docker Image') {
        dockerLogin(env.DOCKER_USR, env.DOCKER_PSW)
        dockerPush(image, "v${env.BUILD_NUMBER}")
      }
    }

    stage('Cleanup') {
      cleanWs()
    }
  }
}
