# Drill CI/CD Integration 

## Overview

Tools for integration with CI/CD systems such as Gitlab and GitHub.

## Modules

- **common**: Common library
- **gitlab**: Gitlab integration services
- **github**: GitHub integration services
- **gradle-plugin**: Gradle plugin for CI/CD integration
- **cli**: CLI Application for CI/CD integration

## Build

Build all modules:
```shell
gradle build
```

Publish common libraries
```shell
cd common
gradle publishToMavenLocal
cd gitlab
gradle publishToMavenLocal
cd github
gradle publishToMavenLocal
```

Publish Gradle plugin:
```shell
cd ../gradle-plugin
gradle publish
```

## Usage

```kotlin
drillCiCd {
    version = "0.0.1"
    group = "realworld"
    agentId = "realworld-backend"
    gitlabUrl = "https://gitlab.com"
    gitlabPrivateToken = "your-token-here"
    drillUrl = "http://localhost:8090"          //drill admin base url
    drillApiKey = "api-key"
    projectId = System.getenv("CI_PROJECT_ID")
    mergeRequestId = System.getenv("CI_MERGE_REQUEST_IID")
    sourceBranch = System.getenv("CI_MERGE_REQUEST_SOURCE_BRANCH_NAME")
    targetBranch = System.getenv("CI_MERGE_REQUEST_TARGET_BRANCH_NAME")
    latestCommitSha = System.getenv("CI_COMMIT_SHA")      //last commit in source branch
}
```
