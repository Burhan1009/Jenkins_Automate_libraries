def call(String composeFile = "docker-compose.yml", boolean removeOrphans = true, boolean forceRecreate = false) {
    try {
        // Logging the start of the Docker Compose restart process
        echo "🔄 Restarting Docker Compose services using file: ${composeFile}"

        // Construct additional flags
        String additionalFlags = ""
        if (removeOrphans) {
            additionalFlags += "--remove-orphans "
        }
        if (forceRecreate) {
            additionalFlags += "--force-recreate "
        }

        // Execute the Docker Compose commands
        sh """
            docker-compose -f ${composeFile} down
            docker-compose -f ${composeFile} up -d ${additionalFlags}
        """

        // Log success
        echo "✅ Docker Compose services restarted successfully!"
    } catch (Exception e) {
        // Error handling
        echo "❌ Error during Docker Compose restart: ${e.getMessage()}"
        error "Failed to restart Docker Compose services."
    }
}
