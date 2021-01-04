pipeline {
    agent any
    
    tools {
        maven 'Default_Maven'
    }
    stages {
        stage("Compile Stage") {
            steps {
                //withMaven(maven: 'Default_Maven') {
                    sh 'mvn clean compile'
                //}
            }
        }

        stage("test") {
            steps {
               // withMaven(maven: 'Default_Maven') {
                    sh 'mvn test'
              //  }
            }
        }

        stage("deploy") {
            steps {
             //   withMaven(maven: 'Default_Maven') {
                     sh 'mvn deploy'
             //   }
            }
        }
    }
}
