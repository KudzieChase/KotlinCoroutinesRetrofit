package com.chase.kotlincoroutines.network

import com.chase.kotlincoroutines.model.Post
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {
    @GET("/posts")
    fun getPosts(): Deferred<Response<List<Post>>>
}