def call(test = 'true') {
    sh "mvn clean package install -Dmaven.test.skip=${test}"
}