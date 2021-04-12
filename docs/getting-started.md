<!--docs:
title: "Getting Started"
layout: landing
section: docs
path: /docs/getting-started/
-->

### 1. Depend on our library

Eyejet Library is available through Maven Repository.
To use it:

1.  Open the `build.gradle` file for your application.
2.  Make sure that the `repositories` section includes Maven Repository
    `mavenCentral()`. For example:
```groovy
  allprojects {
    repositories {
      mavenCentral()
    }
  }
```

3.  Add the library to the `dependencies` section:
```groovy
dependencies {
    // ...

    // declare eyejet version
    def eyejet_version = "x.y.z"

    // Eyejet Library
    implementation("com.zeoflow:eyejet:$eyejet_version")

    // Optional - the eyejet library works without these dependencies
    implementation("com.zeoflow:eyejet-annotation:$eyejet_version")
    implementation("com.zeoflow:eyejet-arch:$eyejet_version")

    // ...
}
```

Visit [MVN Repository](https://mvnrepository.com/artifact/com.zeoflow/eyejet)
to find the latest version of the library.

## Contributors

Eyejet welcomes contributions from the community. Check out our [contributing guidelines](contributing.md)
before getting started.
