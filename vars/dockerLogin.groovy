def call(docker_user, docker_pass) {
    sh "docker login -u ${docker_user} -p ${docker_pass}"
}