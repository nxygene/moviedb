package com.sberkozd.moviedb.data.models

enum class SortOptions(val key : String) {
    POPULAR("popularity.desc"),
    TOP_RATED("vote_average.desc"),
    REVENUE("revenue.desc"),
    RELEASE_DATE("primary_release_date.desc")
}