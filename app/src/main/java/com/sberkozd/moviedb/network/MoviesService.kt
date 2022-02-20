package com.sberkozd.moviedb.network

import com.sberkozd.moviedb.data.responses.DetailResponse
import com.sberkozd.moviedb.data.responses.ListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("discover/movie?vote_count.gte=500")
    suspend fun discover(
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int
    ): Response<ListResponse>

    @GET("movie/{movieId}")
    suspend fun detail(
        @Path("movieId") movieId: Int
    ): Response<DetailResponse>

}