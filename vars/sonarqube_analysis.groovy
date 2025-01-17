def call(String sonarQubeAPI, String projectName, String projectKey, Map<String, String> additionalParams = [:]) {
    try {
        // Logging the start of the process
        echo "🔍 Starting SonarQube scan for project: ${projectName} (Key: ${projectKey})"

        // Construct additional parameters string
        String additionalArgs = additionalParams.collect { key, value -> "-D${key}=${value}" }.join(' ')

        // Execute the SonarQube scan
        withSonarQubeEnv("${sonarQubeAPI}") {
            sh """
                ${env.SONAR_HOME}/bin/sonar-scanner \\
                    -Dsonar.projectName=${projectName} \\
                    -Dsonar.projectKey=${projectKey} \\
                    ${additionalArgs} \\
                    -X
            """
        }

        // Log success
        echo "✅ SonarQube scan completed successfully for project: ${projectName}"
    } catch (Exception e) {
        // Error handling
        echo "❌ Error during SonarQube scan: ${e.getMessage()}"
        error "Failed to execute SonarQube scan for project: ${projectName}"
    }
}
