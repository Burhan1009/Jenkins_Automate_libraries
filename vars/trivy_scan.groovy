def call(String scanPath = ".", Map<String, String> additionalArgs = [:]) {
    try {
        // Logging the start of the Trivy scan
        echo "🔍 Starting Trivy scan on path: ${scanPath}"

        // Construct additional arguments for the Trivy command
        String additionalParams = additionalArgs.collect { key, value -> "--${key}=${value}" }.join(' ')

        // Execute the Trivy scan
        sh """
            trivy fs ${scanPath} ${additionalParams}
        """

        // Log success
        echo "✅ Trivy scan completed successfully for path: ${scanPath}"
    } catch (Exception e) {
        // Error handling
        echo "❌ Error during Trivy scan: ${e.getMessage()}"
        error "Failed to execute Trivy scan on path: ${scanPath}"
    }
}
