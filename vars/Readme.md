# Git Checkout Function

This `Groovy` function is designed to perform a Git checkout operation in Jenkins pipelines. It provides flexibility, error handling, and logging for seamless integration with your CI/CD workflows.

## Function Overview

```groovy
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
```

## Features

1. **Parameterization**:
   - **`gitUrl`**: URL of the Git repository to clone.
   - **`gitBranch`**: Branch to checkout.
   - **`credentialsId`**: Optional credentials ID for accessing private repositories.
   - **`shallowClone`**: Whether to perform a shallow clone (default: `true`).

2. **Enhanced Logging**:
   - Logs the start and successful completion of the Git checkout operation.

3. **Error Handling**:
   - Captures errors during the checkout process and provides detailed error messages.

4. **Default Behavior**:
   - Supports unauthenticated access by default when `credentialsId` is not provided.

## Usage

### **Public Repository**
```groovy
call("https://github.com/user/repo.git", "main")
```
- Clones the `main` branch from a public repository without credentials.

### **Private Repository**
```groovy
call("https://github.com/user/private-repo.git", "develop", "my-git-credentials")
```
- Clones the `develop` branch from a private repository using the specified credentials ID.

### **Full Clone**
```groovy
call("https://github.com/user/repo.git", "feature-branch", null, false)
```
- Performs a full clone of the `feature-branch` without shallow cloning.

## Requirements

- **Git Plugin**: Ensure the Jenkins Git plugin is installed and configured.
- **Credentials**: Add a valid credentials ID in Jenkins for private repository access.
- **Permissions**: The Jenkins agent must have access to the Git repository.

## Example Pipeline Integration

```groovy
pipeline {
    agent any
    stages {
        stage('Git Checkout') {
            steps {
                script {
                    // Checkout a Git repository
                    call("https://github.com/user/repo.git", "main", "my-git-credentials")
                }
            }
        }
    }
}
```

## Benefits

- **Flexibility**: Supports both public and private repositories.
- **Efficiency**: Shallow clone option for faster operations.
- **Resilience**: Comprehensive error handling ensures pipeline stability.
- **Simplicity**: Defaults make it easy to use with minimal configuration.

## Notes

- Always validate the repository URL and branch name before using this function.
- Use shallow cloning (`shallowClone = true`) for faster builds unless a full history is required.
- Ensure credentials are securely managed using Jenkins credentials store.

With this function, managing Git checkouts in your CI/CD pipelines becomes efficient, secure, and reliable. 🚀

# Docker Compose Restart Function

This `Groovy` function simplifies restarting Docker Compose services in a Jenkins pipeline. It provides flexibility, error handling, and improved logging to ensure seamless execution.

## Function Overview

```groovy
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
```

## Features

1. **Parameterization**:
   - **`composeFile`**: Specify the Docker Compose file to use. Defaults to `docker-compose.yml`.
   - **`removeOrphans`**: Remove orphan containers during the restart (default: `true`).
   - **`forceRecreate`**: Force the recreation of containers, even if there are no changes (default: `false`).

2. **Enhanced Logging**:
   - Provides detailed logs for the start, success, or failure of the Docker Compose restart process.

3. **Error Handling**:
   - Gracefully manages errors and provides clear feedback to the pipeline.

4. **Customizable Flags**:
   - Dynamically applies options like `--remove-orphans` and `--force-recreate` based on the parameters.

## Usage

### **Default Restart**
```groovy
call()
```
- Uses the default `docker-compose.yml` file.
- Removes orphan containers but does not force recreation of containers.

### **Custom Docker Compose File**
```groovy
call("my-compose-file.yml")
```
- Restarts services using the `my-compose-file.yml` file.

### **Full Options**
```groovy
call("custom-compose.yml", true, true)
```
- Restarts services using `custom-compose.yml`.
- Removes orphan containers.
- Forces the recreation of all containers.

## Requirements

- **Docker Compose Installed**: Ensure Docker Compose is installed and available in the system path.
- **Jenkins Configuration**: The Jenkins agent running this script must have Docker installed and properly configured.

## Benefits

- **Simplicity**: Handles the entire restart process with a single function call.
- **Flexibility**: Adapts to various use cases with customizable parameters.
- **Resilience**: Handles errors gracefully, ensuring pipeline stability.
- **Efficiency**: Supports faster restarts with optional removal of orphan containers and forced recreation only when needed.

## Notes

- Ensure the `docker-compose.yml` file and required resources (like images and volumes) are accessible to the Docker host.
- Use appropriate permissions and credentials if running Docker commands in a secured environment.

## Example Pipeline Integration

```groovy
pipeline {
    agent any
    stages {
        stage('Restart Docker Services') {
            steps {
                script {
                    // Restarting Docker Compose services
                    call("docker-compose.override.yml", true, false)
                }
            }
        }
    }
}
