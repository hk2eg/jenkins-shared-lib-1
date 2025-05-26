package org.iti;

def login(docker_user, docker_pass) {
    sh "docker login -u ${docker_user} -p ${docker_pass}"
}

def build(image, tag) {
    sh "docker build -t ${image}:${tag} ."
}

def push(image, tag='latest') {
    sh "docker push ${image}:${tag}"
}