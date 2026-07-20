pipeline {
    agent any

    stages {
        stage("Checkout") {
            steps {
                git branch: 'master',
                    url: 'https://github.com/gitdanie714/calculator.git'
            }
        }

        stage("Build, Test & Coverage") {
            steps {
                bat "mvn clean verify"
            }
        }

        stage("Publish Coverage") {
            steps {
                jacoco(
                    execPattern: '**/target/jacoco.exec',
                    classPattern: '**/target/classes',
                    sourcePattern: '**/src/main/java'
                )
            }
        }

        stage("Publish HTML Report") {
            steps {
                publishHTML(target: [
                    reportDir: 'target/site/jacoco',
                    reportFiles: 'index.html',
                    reportName: "JaCoCo Report"
                ])
            }
        }
    }
}