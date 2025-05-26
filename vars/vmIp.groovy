def call() {
    return sh(script: "ip a | grep 192.168", returnStdout: true).trim()
}