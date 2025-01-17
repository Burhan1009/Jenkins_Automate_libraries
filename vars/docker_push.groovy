def call(String project, String imageTag, String dockerHubUser) {
    try {
        // Logging the start of the process
        echo "🚀 Starting Docker push process for ${project}:${imageTag} to Docker Hub user: ${dockerHubUser}"
        
        // Using credentials for secure login
        withCredentials([usernamePassword(credentialsId: 'docker', passwordVariable: 'dockerHubPass', usernameVariable: 'dockerHubUser')]) {
            // Secure Docker login
            echo "🔑 Logging in to Docker Hub..."
            sh "echo ${dockerHubPass} | docker login -u ${dockerHubUser} --password-stdin"
        }

        // Push the Docker image
        def fullImageName = "${dockerHubUser}/${project}:${imageTag}"
        echo "📤 Pushing Docker image: ${fullImageName}..."
        sh "docker push ${fullImageName}"

        // Logging success
        echo "✅ Successfully pushed Docker image: ${fullImageName}"

    } catch (Exception e) {
        // Error handling for debugging
        echo "❌ Error during Docker push: ${e.getMessage()}"
        error "Failed to push Docker image ${project}:${imageTag}"
    } finally {
        // Logout from Docker Hub to secure credentials
        echo "🔒 Logging out from Docker Hub..."
        sh "docker logout"
    }
}
