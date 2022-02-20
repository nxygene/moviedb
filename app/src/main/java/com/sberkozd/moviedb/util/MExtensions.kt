package com.sberkozd.moviedb.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.sberkozd.moviedb.data.models.Movie
import com.sberkozd.moviedb.data.responses.DetailResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(values: List<T>) {
    val value = this.value ?: arrayListOf()
    value.addAll(values)
    this.value = value
}

fun Context.isConnected(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }
        else -> {
            // Use depreciated methods only on older devices
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            nwInfo.isConnected
        }
    }
}


class FlowObserver<T>(
    lifecycleOwner: LifecycleOwner,
    private val flow: Flow<T>,
    private val collector: suspend (T) -> Unit
) {

    private var job: Job? = null

    init {
        lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { source: LifecycleOwner, event: Lifecycle.Event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    job = source.lifecycleScope.launch {
                        flow.collect { collector(it) }
                    }
                }
                Lifecycle.Event.ON_STOP -> {
                    job?.cancel()
                    job = null
                }
                else -> {
                }
            }
        })
    }
}

inline fun <reified T> Flow<T>.observeInLifecycle(
    lifecycleOwner: LifecycleOwner
) = FlowObserver(lifecycleOwner, this, {})

fun Movie.toMovieDetailModel(): DetailResponse {

    return DetailResponse(
        adult = this.adult,
        backdrop_path = this.backdrop_path,
        belongs_to_collection = null,
        budget = null,
        genres = null,
        homepage = null,
        id = this.id,
        imdb_id = null,
        original_language = this.original_language,
        original_title = this.original_title,
        overview = this.overview,
        popularity = this.popularity,
        poster_path = this.poster_path,
        production_companies = null,
        production_countries = null,
        release_date = this.release_date,
        revenue = null,
        runtime = null,
        spoken_languages = null,
        status = null,
        tagline = null,
        title, video, vote_average, vote_count


    )

}