# android-task

Android App using custom Weather Open API

## Description

This app has following basic functionality.
- Fetch user city weather based on user location, search user city and view its weather detail.
- All to bookmark favourite cities and can see weather any time
- Show weather notification every day at 6am morning
- View weather in celsius and forheignhit based on user choice


## Top Level App Design
Application has 3 basic modules (app, base and weather). Here is brief detail about each module

    1st - App Module:
    This is main project module that has all navigation rules (mobile_navigation) and process configurations like
    - Setup hilt
    - Setup logging using Timber
    - Setup notification channel
    - Setup worker manager configurations

    2nd - Base Module:
    This module is responsible for basic common features like
    - Has rules about network connections (all common network communication logic is defined here and all others modules use it). For example i did two network configurations setups here
        - Setup query parameter intercepter for okhttp. This helps to add common used parameters again and again in every request
        - Setup network logger
    - App Notification channel settings that is used and configure by main (app) module
    Note: This is right place to configure database as well because sometimes we need to provide data encryption or custom behaviour so if we have one common database for all modules then we do not need to configuration such type settings in every module. One use case is, people use CpherDatabase for data safety

    3rd - Weather Module:
    This project is related weather so this main module that have all weather specific features like
    - It has use cases for each task like get weather by user location, get weather by city name, add user fav city and view its weather
    - It has notification use case that shows weather notification once in a day at 6am morning


## Tech Stack
- Kotlin - Programming language (Sealed classes for UI state handling at first UI for state handling demonstration)
- Hilt - Used to provide all dependencies to classes
- Retrofit with OkHttp3 - Parse http request/response API and also add intercepter to minimize common query parameters
- Moshi - Advanced library for json parsing from http api response
- Coroutines - for managing threads UI and background threads
- LiveData - use LiveData to see UI update with data changes.
- Navigation API - use navigation api for navigation between main UI, search or detail UI
- Work Manager - Used it to schedule weather notification once in a day at 6am


## App arch components
- Use modular design to build 3 basic modules and how they communicate with each other
- ViewModel: provide state to view for UI classes
- UseCase: Keep all business rules and logic
- Repository: Have all data fetching logic and provide date to use case of view model
- Service: Api that fetch data from server and provide to Repository

## Unit testing
- Added unit tests for view model, useCase and repository classes for demonstration


## Possible Improvements
- We can refine and polish UI
- We can replace shared preference with room database [but i used shared preference due to easiness and shortage of time. This is beauty of repository pattern we can replace shared preference with room without impacting code on other modules or classes]
- We can handle state handling at search and detail UI like I handled on main UI
- Add unit test for all remaining classes
- Can use flow instead of live data
- We can move favourite in user module from weather module (Note: But i keep it weather module because this feature has relation with weather and there is no too much user specific)