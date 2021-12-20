# SimpleWeatherApp

## Introduction to features of the app

- Sample app showing weather details for a given city

- A user can type the name of a city

- A user can add/remove/view favourite cities already searched

- The app is showing results using the current location on start up

- Full weather details are shown when a successful search has completed

- The app is storing locally the last search result, and showing it in case of no network connection

## Tech Stack

- Android Views, and JetPack Compose. Assembled in a way that would fit in an existing application
  needing hybrid functionality

- Kotlin, Coroutines, Flow (StateFlow)

- Ktor Http Client

- DataStore for persistence

- Dagger for DI. Use of a simple component, with few dagger modules.

- FragmentFactory usage, for constructor injection in fragments and facilitating ui-testing

- Kotlinx Serialization, for network calls and serialising / deserializing purposes of persistence

## Patterns

- Clean Architecture for domain layer

- MVI for presentation . States, Intents, Reducers, orchestrated by VMs

- Single Activity hosting a fragment for the demo purposes, but can easily support any number

- With the package structure applied, is easy to extract modules, per type and functionality. e.g.
  core ones for networking, etc and feature ones.

- Between each layer, networking, persistence, Repositories, UseCase there are different Result
  types, that wrap results of execution and also apply function needed per layer, applying
  transformations

- The whole solution is implemented with a quite functional approach that is easy to pick up

## Testing

- Use of strikt for asserting

- Use of mockk for mocking.

- Since this is a demo, it is not 100% test covered, but many of the core components are well tested

- UI Test is easy to add with the current set up as well

## Extensibility

- Extracting modules is fairly easy

- There is a thought of detekt but not applied, given the time allocated for this project

- Built in Dark theme support from Jetpack Compose. However to make it right, there would be a need
  to refine the colours of the app.

- Some UI tests would be easy to add