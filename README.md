<h1 align="center">movieDB</h1>

<p align="center"> 
  movieDB is an application built for Android TV. The application demonstrates modern Android development with Hilt, Coroutines, Flow, Jetpack and Material Design based on MVVM architecture.
</p>
</br>
<img src="https://user-images.githubusercontent.com/42215231/154843378-6bf75242-1379-4ed9-b07c-3898a2911227.gif" width="600" height="366" />

## Tech stack & Open-source libraries
- Minimum SDK level 26
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- [Leanback](https://developer.android.com/jetpack/androidx/releases/leanback) provides UI templates that simplify creating Android TV apps.
- Jetpack
  - Lifecycle - Observe Android lifecycles and handle UI states upon the lifecycle changes.
  - ViewModel - Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
  - DataBinding - Binds UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - [Bindables](https://github.com/skydoves/bindables) - Android DataBinding kit for notifying data changes to UI layers.
  - Repository Pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs.
- [Moshi](https://github.com/square/moshi/) - A modern JSON library for Kotlin and Java.
- [Chucker](https://github.com/ChuckerTeam/chucker) - An HTTP inspector for Android & OkHTTP
- [Coil](https://github.com/coil-kt/coil) - An image loading library for Android backed by Kotlin Coroutines.
- Custom Views
  - [CarouselView](https://github.com/alirezat775/carousel-view) - CarouselView for android with showing horizontal and vertical, auto scrolling (with pause/resume), slider mode/ carousel mode options.
  - [DiscreteScrollView](https://github.com/yarolegovich/DiscreteScrollView) - A scrollable list of items that centers the current element and provides easy-to-use APIs for cool item animations.
  ## MAD Score
![summary](https://user-images.githubusercontent.com/42215231/154801143-1a5233d5-b421-4063-9401-9f14a15d0a11.png)
![kotlin](https://user-images.githubusercontent.com/42215231/154801133-45cc6db7-94ec-47f1-96fc-ad8c7c41b3d7.png)

