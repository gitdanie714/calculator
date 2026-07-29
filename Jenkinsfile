pipeline {
    agent any

     triggers {
            cron('16 11 * * *')
            githubPush()
        }
     post {
         success {
             mail to: 'sheisgraced40@gmail.com',
             subject: "SUCCESS: ${currentBuild.fullDisplayName}",
             body: "Build succeeded ✅\n${env.BUILD_URL}"
         }
         failure {
             mail to: 'sheisgraced40@gmail.com',
             subject: "FAILED: ${currentBuild.fullDisplayName}",
             body: "Build failed ❌\nCheck logs: ${env.BUILD_URL}"
         }
     }

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

        stage("Static code analysis") {
            steps {
                bat "mvn checkstyle:checkstyle"

                publishHTML(target: [
                    reportDir: 'target/site',
                    reportFiles: 'checkstyle.html',
                    reportName: "Checkstyle Report"
                ])
            }
        }
    }
}