package com.sberkozd.moviedb.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sberkozd.moviedb.data.models.Movie
import com.sberkozd.moviedb.data.models.SortOptions
import com.sberkozd.moviedb.ui.adapters.MovieAdapter
import com.sberkozd.moviedb.ui.home.HomeViewModel
import com.sberkozd.moviedb.util.HorizontalCarouselRecyclerView
import com.sberkozd.moviedb.util.RecyclerViewPaginator


object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("paginator", "sortParameter")
    fun pagination(
        view: RecyclerView,
        viewModel: HomeViewModel,
        sortOptions: SortOptions
    ) {
        RecyclerViewPaginator(
            recyclerView = view,
            isLoading = { viewModel.isLoading },
            loadMore = { viewModel.fetchNextMovies(sortOptions) },
            onLast = { viewModel.isOnLast(sortOptions) }
        )
    }

    @JvmStatic
    @BindingAdapter("adapterMovieList")
    fun bindAdapterMovieList(view: HorizontalCarouselRecyclerView, movieList: List<Movie>?) {
        if (view.adapter == null) {
            view.initialize(MovieAdapter().apply {
                stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            })
        }
        if (!movieList.isNullOrEmpty()) {
            (view.adapter as MovieAdapter).setMovieList(movieList)

        }
    }
}