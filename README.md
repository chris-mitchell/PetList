# Pet List App

This app is structured to allow users to track dog names, their owners, and a photo of the dog.

It was build using an MVVM achitecture.  To connect the ViewModels to Views, we use Databinding.  To help the Models populate their data, we use a Repository pattern.  In addition, the app relies on LiveData to provide reactive streams.  The LiveData objects depend upon the LifecycleOwner and appropriate monitor the Activity or Fragment lifecycle.  For the persistence layer, Room was chosen for its integration with the rest of Android Architecture Components.

For more details, please see: https://developer.android.com/topic/libraries/architecture/viewmodel

A simple utility called `InjectorUtils` was written to provide basic principals of depency injection for the project.  Dagger could have been utilized, but felt a bit heavy handed for the size of the project.  As the project grows, this can be reevaluated and incorporated when needed.

A single thread is used in a serial fashion for long running tasks.  This is provided via `AppExecutor`.  RxJava could have been utilized, but similar to Dagger, this felt overly complex.

## Modules

The app is split into two modules to help with reusability, testing and compile times.

* Core
    * Data, persistence, and logic shared across all platforms.
* App
    * ViewModels, Views and UI
    * Features should be broken out into their own package

## Testing

Unit tests were configured for both the core module and app module.  The core module should test the functionality of the persistence layer.  The app module should effectively test the ViewModels.

Unit tests try to follow a "Given-When-Then" approach to the tests - https://martinfowler.com/bliki/GivenWhenThen.html

Unfortunately, the time limit was reached and Espresso tests were not incorporate.

## Building the App

To build the app for a debug build:
```
./gradlew assembleDebug
```

To build the app for a release build:
```
TBD: Appropriate keys need to be configured prior to a release build being available
```

## Running Tests

To run the all the tests, ensure a device / emulator is connected and run:
```
./gradlew check
```
