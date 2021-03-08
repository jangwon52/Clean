package com.mongoose.clean.data.source.remote

import com.mongoose.clean.data.model.user.UserResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  RandomUserApi.kt
 *
 *  Created by jangwon on 2021/03/05
 *
 */

interface UserApi {
    @GET("api/")
    fun getUsers(
        @Query(value = "page") page: Int? = 1,
        @Query(value = "results") results: Int = 20,
        @Query(value = "seed") seed: String = "abc",
    ): Single<Response<UserResponse>>

    companion object {
        const val BASE_URL = "https://randomuser.me/"
    }
}