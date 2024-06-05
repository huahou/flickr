# CVS Flickr App

## Architecture

This app was implemented by following CLEAN architecture:

1. Data layer: Retrofit + Gson
2. Domain layer: Coroutine
3. UI layer:
* MVVM
* LiveData
* Jetpack Compose + Navigation
* Coil
4. Dependency Injection: Dagger 2

## Extra Features Implemented:
1. Support landscape orientation
2. Testing: Unit test
3. Custom font
4. Add a button in the detail view to share the image and metadata
5. Security: SSL Pinning
6. Memory leak monitoring: LeakCanary
7. CI/CD: Github action

## Future Improvements:
1. Testing: Espresso
2. Health monitoring: Crashlytics / Sentry
3. Analytics: Firebase Analytics / Segment
4. RemoteConfig: Firebase RemoteConfig / Optimizely
5. CI/CD:
* Static code analysis: SonarCube
6. App update (force / optional)
