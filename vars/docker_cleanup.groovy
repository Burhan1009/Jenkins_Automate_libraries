def call(String project, String imageTag, String dockerHubUser) {
    try {
        // Log the start of the process
        echo "🗑️ Starting removal of Docker image Ye Babu Rao ka style hai  : ${dockerHubUser}/${project}:${imageTag}"

        // Remove the Docker image
        sh """
            docker rmi ${dockerHubUser}/${project}:${imageTag} || echo "⚠️ Image not found or already removed"
        """

        // Log success
        echo "✅ Successfully removed Docker image: ${dockerHubUser}/${project}:${imageTag}"
    } catch (Exception e) {
        // Handle any errors during the removal process
        echo "❌ Error while removing Docker image: ${e.getMessage()}"
        error "Failed to remove Docker image ${project}:${imageTag}"
    }
}
