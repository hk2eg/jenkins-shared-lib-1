import org.iti.Docker

def call(Map m = [:]) {
    def docker = new Docker(
        docker_user: m.docker_user,
        docker_pass: m.docker_pass,
        image: m.image,
        tag: m.tag ?: 'latest'
    )
    docker.steps = this
    docker.login()
}