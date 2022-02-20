package com.sberkozd.moviedb.ui.home

import androidx.annotation.WorkerThread
import com.sberkozd.moviedb.data.models.SortOptions
import com.sberkozd.moviedb.data.responses.ListResponse
import com.sberkozd.moviedb.network.MoviesService
import com.sberkozd.moviedb.network.NetworkRepository
import com.sberkozd.moviedb.network.NetworkResult
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val moviesService: MoviesService
) : NetworkRepository {

    @WorkerThread
    fun getSortedMovies(
        sortOptions: SortOptions,
        page: Int,
        onStart: () -> Unit,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {

        val result: NetworkResult<ListResponse> = wrapNetworkResult(call = {
            moviesService.discover(sortOptions.key, page)
        })

        when (result) {
            is NetworkResult.SuccessfulNetworkResult -> {
                emit(result.body)
                onSuccess()
            }
            is NetworkResult.EmptyNetworkResult -> onError(result.customErrorMessage)
            is NetworkResult.ErrorNetworkResult -> {
                try {
                    val moshi = Moshi.Builder().build()
                    val jsonAdapter: JsonAdapter<ListResponse> =
                        moshi.adapter(ListResponse::class.java)
                    jsonAdapter.fromJson(result.errorMessage)?.let { emit(it) }

                } catch (e: Exception) {
                    onError(result.errorMessage)
                }
            }
            is NetworkResult.ExceptionResult -> onError(result.errorMessage)
        }

    }.onStart { onStart() }.flowOn(Dispatchers.IO)


}