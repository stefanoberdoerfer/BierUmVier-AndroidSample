# BierUmVier
Android MVVM sample project in Kotlin using hybrid views (XML + Compose), Coroutines, Flow, AndroidX Navigation, Room, Retrofit, Moshi, Coil
written in about 10h

![list](/screenshots/1_list.png?raw=true)
![detail](/screenshots/2_detail.png?raw=true)


## Selected design decisions
 - minSDK 21: 99% of active devices according to Google (02/2023)
 - MVVM:
   - Good separation of business logic and android/view specific code
   - Handling of configuration changes
   - Pushed by google to be the "modern android" way
 - Hybrid UI (XML + Compose): Personal learning challenge
 - Jetpack Navigation: No hassle of dealing with the fragment backstack manually
 - Kotlin Coroutines + Flow:
   - Automatic UI / Database updates
   - good readability
   - platform independent
 - Retrofit + Moshi: Field-tested stack for consuming HTTP-Rest APIs


## Possible improvements
 - Dependency Injection for improved testability
 - Material Design 3 dynamic colors and darkmode


## Image Licenses
 - App icon: Google Noto Emoji (Open Font License)
 - Other icons: Google Material Design Icons (Apache 2.0)
