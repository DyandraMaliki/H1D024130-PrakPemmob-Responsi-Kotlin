package com.pemmob.responsisatu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pemmob.responsisatu.data.model.Book
import com.pemmob.responsisatu.data.repository.BookRepository
import com.pemmob.responsisatu.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookViewModel(
    private val repository: BookRepository = BookRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Book>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Book>>> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("kotlin")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        searchBooks("kotlin")
    }

    fun onQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun searchBooks(query: String = _searchQuery.value) {
        if (query.isBlank()) return
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val books = repository.searchBooks(query.trim())
                _uiState.value = UiState.Success(books)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Terjadi kesalahan saat memuat data")
            }
        }
    }
}