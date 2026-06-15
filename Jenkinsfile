/* Requires the Docker Pipeline plugin */
pipeline {
    agent any

    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
                sh mvn install -DskipTests
            }
        }
        stage('test') {
            steps {
                sh 'mvn clean test -pl api-tests'
                sh 'mvn clean test -pl ui-tests'
                sh 'mvn gatling:test -pl performance-tests'
            }
        }
    }
}