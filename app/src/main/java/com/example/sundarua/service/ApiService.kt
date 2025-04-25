package com.example.sundarua.service

import com.example.sundarua.data.WordResponse
import retrofit2.http.GET

interface ApiService {
    @GET("undakusukbasa")
    suspend fun getWords(): WordResponse
}

