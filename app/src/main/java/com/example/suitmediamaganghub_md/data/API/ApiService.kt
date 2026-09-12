package com.example.suitmediamaganghub_md.data.API

import com.example.suitmediamaganghub_md.data.model.ListUserResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import javax.annotation.processing.Generated

interface ApiService {

    @GET("/api/users")
    suspend fun getUsers(
        @Header("x-api-key") apikey: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): ListUserResponse

}