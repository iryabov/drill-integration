# Drill Integration Overview

Tools for integration with third-party systems such as CI/CD.

## Modules

- **common**: Common library
- **gradle-plugin**: Gradle plugin

## Build

Build common module:
```shell
cd common
gradle build
gradle publishToMavenLocal
```

Build gradle plugin:
```shell
cd ../gradle-plugin
gradle build
gradle publish
```