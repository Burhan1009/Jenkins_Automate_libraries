def call(int timeoutMinutes = 1, boolean abortPipelineOnFailure = false) {
    try {
        // Logging the start of the quality gate check
        echo "⏳ Waiting for SonarQube Quality Gate results (Timeout: ${timeoutMinutes} minute(s))..."

        // Timeout for the quality gate check
        timeout(time: timeoutMinutes, unit: "MINUTES") {
            waitForQualityGate abortPipeline: abortPipelineOnFailure
        }

        // Log success
        echo "✅ SonarQube Quality Gate passed successfully!"
    } catch (Exception e) {
        // Error handling
        echo "❌ Quality Gate check failed or timed out: ${e.getMessage()}"

        if (abortPipelineOnFailure) {
            error "Pipeline aborted due to SonarQube Quality Gate failure."
        } else {
            echo "⚠️ Proceeding with the pipeline despite Quality Gate failure."
        }
    }
}
