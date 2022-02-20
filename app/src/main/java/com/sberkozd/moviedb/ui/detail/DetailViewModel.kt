package com.sberkozd.moviedb.ui.detail

import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.sberkozd.moviedb.data.models.Movie
import com.sberkozd.moviedb.data.responses.DetailResponse
import com.sberkozd.moviedb.util.Constants.argKeyMovie
import com.sberkozd.moviedb.util.toMovieDetailModel
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository,
    state: SavedStateHandle
) :
    BindingViewModel() {

    var detailResponse: LiveData<DetailResponse> = MutableLiveData()
    var movieModel: MutableLiveData<DetailResponse> = MutableLiveData()

    val movie = state.get<Movie>(argKeyMovie)

    @get:Bindable
    var toastMessage: String? by bindingProperty(String())
        private set

    @get:Bindable
    var isLoading: Boolean by bindingProperty(true)
        private set

    init {
        movieModel.value = movie?.toMovieDetailModel()
        fetchMovieDetail()
    }

    fun fetchMovieDetail() {
        detailResponse = detailRepository.detail(movieId = movie!!.id,
            onStart = { isLoading = true },
            onSuccess = { isLoading = false },
            onError = { toastMessage = it })
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }
}