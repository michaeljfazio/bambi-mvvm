pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'man compile'
      }
    }
    stage('Test') {
      steps {
        sh 'mvn test'
      }
    }
    stage('Package') {
      steps {
        sh 'man package'
      }
    }
  }
}