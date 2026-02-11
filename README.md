# neutrino

un sistema de eventos hecho en java para mi mod [sputnik](https://github.com/retuci0/sputnik) y para mi [renderer 3d](https://github.com/retuci0/3d-renderer)

## cómo usar:

en `build.gradle`:
```gradle
repositories {
    ...
    maven { url 'https://jitpack.io' }
}

...

dependencies {
    modImplementation  "com.github.retuci0:neutrino:${project.neutrino_version}"
}
```

y en gradle.properties:

```properties
neutrino_version=1.0.1
```