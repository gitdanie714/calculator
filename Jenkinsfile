pipeline {
    agent any

    stages {
        stage("Checkout") {
            steps {
                git branch: 'master',
                    url: 'https://github.com/gitdanie714/calculator.git'
            }
        }

        stage("Compile") {
            steps {
                bat 'mvn compile'
            }
        }
      stage("Unit test") {
            steps {
                 bat "mvn test"
         }
}
       stage("Code coverage") {
         steps {
           bat "mvn clean verify"

           publishHTML(target: [
             reportDir: 'target/site/jacoco',
             reportFiles: 'index.html',
             reportName: "JaCoCo Report"
           ])
         }
       }
    }
}