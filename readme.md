# eShop example App
## Requirements and task description
1. [Copy from Github](docs/task.md)

# Project structure
1. **androidTest** - contains minimum of instrumental tests to check general UI
2. **main** - contains all the general UI/UX, API and business logic code
3. **mock** - contains only **MockApiInterceptor** class that allows to emulate network response
4. **prod** - contains **MockApiInterceptor** without any logic
5. **test** - contains unit tests for models, expectations, view models and repositories

## Related documentation
1. [Worklog](docs/worklog.md) - describes execution order and project growing progress
2. [Build notes](app/destribution.notes) - information about what is done and ready to test
3. Aliases of testers for [Release](app/distribution-release.testers) and [QA](distribution-qa.testers) builds to be shared with
4. Description about shared test build located in [Notes](app/distribution.notes)

## Minimum features that available in the app
* On startup access remote api to get data about available categories
* Display the Timeline as a grid with two columns
* Display the number of `likes` and `comments` on the grid
* For `sold out` items, display a `sold out` label on the grid
* Display a floating button
* Screen rotation not locked
* Layouts is the same for any orientations
* Application is done with production-level quality
* The project written in Kotlin
* The minimum SDK is 5.0 (Lollipop)
* Minimum of open source libraries is used
* All internal graphic were taken from requirements source
* All images for mock data are free to use and not require for usage rights
* Application includes naive analytics logic to get more details about target audience

## Links on application
Application can be tested not only by assembling from the source codes,
    but as well with follow links:
1. [Google Play](https://play.google.com/store/apps/details?id=tregub.miniMercariApp) - using that link you can register as beta tester on Google Play
    and get access to application in simplest and quickest way