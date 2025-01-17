def call(String scanPath = './', String odcInstallation = 'OWASP', String reportPattern = '**/dependency-check-report.xml') {
    try {
        // Logging the start of Dependency-Check
        echo "🔍 Starting OWASP Dependency-Check scan on path: ${scanPath}"
        
        // Run the OWASP Dependency-Check
        dependencyCheck additionalArguments: "--scan ${scanPath}", odcInstallation: odcInstallation
        
        // Publish the Dependency-Check report
        echo "📄 Publishing Dependency-Check report with pattern: ${reportPattern}"
        dependencyCheckPublisher pattern: reportPattern
        
        // Success log
        echo "✅ OWASP Dependency-Check scan and report publishing completed successfully!"
    } catch (Exception e) {
        // Error handling
        echo "❌ Error during OWASP Dependency-Check: ${e.getMessage()}"
        error "Failed to complete OWASP Dependency-Check"
    }
}
