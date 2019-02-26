node {

        withMaven(maven:'maven') {


    try{
        stage('Remove') {
            sh "docker rm -vf account"
            sh "docker rm -vf player"
            sh "docker rm -vf mongoclient"
            sh "docker rm -vf consumer"
        }
    } catch(e) {
        build_ok = false
        echo e.toString()
    }
 
        stage('Build') {
            sh 'mvn package spring-boot:repackage'
        }
 
        stage('Image') {
            docker.build "account"
        }
 
        stage ('Run') {
            docker.image("account").run('-p 8081:8081 -h account --network football --name account')
        }
 
        stage ('Final') {
            build job: 'player-service-pipeline', wait: false
        }
    }
 
}
