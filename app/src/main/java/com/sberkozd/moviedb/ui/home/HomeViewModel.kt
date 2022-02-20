package com.sberkozd.moviedb.ui.home


import androidx.annotation.MainThread
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.sberkozd.moviedb.data.models.Movie
import com.sberkozd.moviedb.data.models.SortOptions
import com.sberkozd.moviedb.data.responses.ListResponse
import com.sberkozd.moviedb.util.plusAssign
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    state: SavedStateHandle
) :
    BindingViewModel() {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()
    val listResponse: LiveData<ListResponse>
    var listLiveData: MutableLiveData<MutableList<Movie>> =
        MutableLiveData<MutableList<Movie>>()

    var currentMovie: String? = null

    val index: MutableStateFlow<Int> = MutableStateFlow(1)

    val sortOption =
        state.get<String>("sort")?.let { sort -> SortOptions.values().find { sort == it.key } }!!

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var toast: String? by bindingProperty(null)

    init {

        listResponse = getResponse(index, sortOption)

        collectResponse(listResponse, listLiveData)

    }

    private fun collectResponse(
        response: LiveData<ListResponse>,
        list: MutableLiveData<MutableList<Movie>>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            response.asFlow().collect {
                if (it.results.isNullOrEmpty()) {
                    if (it.status_message != null) {
                        toast = it.status_message.toString()
                        notifyPropertyChanged(::toast)
                    } else {
                        eventChannel.send(Event.ShowNoDataWarning)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        list += it.results
                    }
                }
            }
        }
    }

    private fun getResponse(
        index: MutableStateFlow<Int>,
        sortOptions: SortOptions
    ): LiveData<ListResponse> {
        return index.asLiveData().switchMap { page ->
            homeRepository.getSortedMovies(
                sortOptions = sortOptions,
                page = page,
                onStart = { isLoading = true },
                onSuccess = { isLoading = false },
                onError = { toast = it }
            ).asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
        }
    }

    @MainThread
    fun fetchNextMovies(sortOptions: SortOptions) {
        if (!isLoading) {
            index.value++
        }
    }

    fun isOnLast(sortOptions: SortOptions): Boolean {
        val result = listResponse.value?.page!! >= listResponse.value?.total_pages!!
        if (result) {
            viewModelScope.launch(Dispatchers.Main) {
                eventChannel.send(Event.ShowNoDataWarning)
            }
        }
        return result
    }


    sealed class Event {
        object ShowNoDataWarning : Event()
    }
}