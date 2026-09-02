pipeline {
    agent any

    options {
        timestamps()
    }

    triggers {
        cron('16 11 * * *')
        githubPush()
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

        stage("Static Code Analysis") {
            steps {
                bat "mvn checkstyle:checkstyle"

                publishHTML(target: [
                    reportDir: 'target/site',
                    reportFiles: 'checkstyle.html',
                    reportName: "Checkstyle Report"
                ])
            }
        }

        stage("Build Docker Image") {
            steps {
                bat "docker build -t elladev20/calculator-app:v1 ."
            }
        }

        stage("Push Docker Image") {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {
                    bat '''
                        docker login -u %DOCKER_USER% -p %DOCKER_PASS%
                        docker push elladev20/calculator-app:v1
                    '''
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                bat 'kubectl apply -f k8s/'
            }
        }

        stage('Verify Deployment') {
            steps {
                bat 'kubectl rollout status deployment/calculator-app'
            }
        }

        stage('Show Application URL') {
            steps {
                bat '''
                    echo ================================
                    echo Kubernetes Deployment:
                    echo ================================
                    kubectl get deployment calculator-app

                    echo.
                    echo ================================
                    echo Pods:
                    echo ================================
                    kubectl get pods

                    echo.
                    echo ================================
                    echo Service:
                    echo ================================
                    kubectl get service calculator-service

                    echo.
                    echo ================================
                    echo Application URL:
                    echo ================================
                    minikube service calculator-service --url
                '''
            }
        }
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
}