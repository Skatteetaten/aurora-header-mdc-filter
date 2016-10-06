node {
   stage 'Checkout'
   checkout scm

   stage 'Build and Upload to Nexus'
   sh "./mvnw clean deploy"
}