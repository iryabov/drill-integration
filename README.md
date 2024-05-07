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