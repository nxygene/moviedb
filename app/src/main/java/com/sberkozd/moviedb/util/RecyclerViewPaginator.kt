package com.sberkozd.moviedb.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sberkozd.moviedb.R

class RecyclerViewPaginator(
    recyclerView: RecyclerView,
    private val isLoading: () -> Boolean,
    private val loadMore: (Int) -> Unit,
    private val onLast: () -> Boolean = { true }
) : RecyclerView.OnScrollListener() {

    var currentPage: Int = 0

    var endWithAuto = false

    var threshold = 5

    init {
        recyclerView.addOnScrollListener(this)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        recyclerView.layoutManager?.let {
            val visibleItemCount = it.childCount
            val totalItemCount = it.itemCount
            val lastVisibleItemPosition = (it as LinearLayoutManager).findLastVisibleItemPosition();

            if (onLast() || isLoading()) return

            if (endWithAuto) {
                if (visibleItemCount + lastVisibleItemPosition == totalItemCount) return
            }

            if ((visibleItemCount + lastVisibleItemPosition) + threshold >= totalItemCount) {
                loadMore(++currentPage)
            }

        }
    }
}
