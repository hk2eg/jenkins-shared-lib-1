package org.iti
class Docker implements Serializable{
    String docker_user
    String docker_pass
    String image
    String tag   = 'latest'

    def steps

    void login() {
        steps.sh "docker login -u ${docker_user} -p ${docker_pass} "
    }

    void build() {
        steps.sh "docker build -t ${image}:${tag} ."
    }

    void push() {
        steps.sh "docker push ${image}:${tag}"
    }
}