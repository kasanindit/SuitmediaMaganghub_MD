package com.example.suitmediamaganghub_md.data.repository

import com.example.suitmediamaganghub_md.data.API.ApiService
import com.example.suitmediamaganghub_md.data.model.DataItem
import com.example.suitmediamaganghub_md.data.model.ListUserResponse

class ListUserRepository(private val apiService: ApiService) {

    suspend fun getListUsers(apikey: String, page: Int, perPage: Int): ListUserResponse {
        return apiService.getUsers(apikey = apikey, page = page, perPage )
    }

}