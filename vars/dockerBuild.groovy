def call(image, tag) {
      dir("${env.WORKSPACE}") {
        sh "docker build -t ${image}:${tag} ."
      }
}
