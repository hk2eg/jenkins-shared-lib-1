def call(image, tag='latest') {
    sh "docker push ${image}:${tag}"
}