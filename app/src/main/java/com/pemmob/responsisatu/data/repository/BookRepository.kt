package com.pemmob.responsisatu.data.repository

import com.pemmob.responsisatu.data.model.Book
import com.pemmob.responsisatu.data.model.toBook
import com.pemmob.responsisatu.data.network.OpenLibraryApiService
import com.pemmob.responsisatu.data.network.RetrofitClient

class BookRepository(
    private val apiService: OpenLibraryApiService = RetrofitClient.apiService
) {
    suspend fun searchBooks(query: String): List<Book> {
        val response = apiService.searchBooks(query)
        return response.docs?.map { it.toBook() } ?: emptyList()
    }
}