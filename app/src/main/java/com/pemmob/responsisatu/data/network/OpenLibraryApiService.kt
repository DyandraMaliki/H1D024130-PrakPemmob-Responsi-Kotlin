package com.pemmob.responsisatu.data.network

import com.pemmob.responsisatu.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLibraryApiService {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("limit") limit: Int = 20
    ): SearchResponse
}