def call(String gitUrl, String gitBranch, String credentialsId = null, boolean shallowClone = true) {
    try {
        // Logging the start of the Git clone process
        echo "🔄 Starting Git checkout for repository: ${gitUrl} on branch: ${gitBranch}"

        // Git clone with optional credentials and shallow clone configuration
        git([
            url: gitUrl,
            branch: gitBranch,
            credentialsId: credentialsId,
            shallow: shallowClone
        ])

        // Log success
        echo "✅ Successfully checked out branch: ${gitBranch} from repository: ${gitUrl}"
    } catch (Exception e) {
        // Error handling
        echo "❌ Error during Git checkout: ${e.getMessage()}"
        error "Failed to checkout branch: ${gitBranch} from repository: ${gitUrl}"
    }
}
