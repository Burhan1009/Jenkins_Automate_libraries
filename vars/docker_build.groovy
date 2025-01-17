// Yahan Function Define ho Raha hain | Define Function
def call(String projectName, String imageTag, String dockerHubUser) {
    try {
        // Log the build process | Build Process ke logs
        echo "Starting Docker build for ${projectName}:${imageTag} by ${dockerHubUser}..."
        
        // Docker image ko build karo caching ke saath taaki build faster ho
        sh """
            docker build \\
                --pull \\
                --no-cache \\
                --tag ${dockerHubUser}/${projectName}:${imageTag} .
        """
        
        // Log success
        echo "Docker image ${dockerHubUser}/${projectName}:${imageTag} built successfully!"
    } catch (Exception e) {
        // Handle errors
        echo "Error during Docker build: ${e.getMessage()}"
        error "Failed to build Docker image ${projectName}:${imageTag}"
    }
}
