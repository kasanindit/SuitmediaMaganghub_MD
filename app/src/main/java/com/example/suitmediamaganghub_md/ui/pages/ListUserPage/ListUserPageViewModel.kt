package com.example.suitmediamaganghub_md.ui.pages.ListUserPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suitmediamaganghub_md.data.API.ApiConfig
import com.example.suitmediamaganghub_md.data.model.DataItem
import com.example.suitmediamaganghub_md.data.repository.ListUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListUserPageViewModel : ViewModel() {
    private val repository = ListUserRepository(ApiConfig.getApiService())

    private val _users = MutableStateFlow<List<DataItem>>(emptyList())
    val users = _users.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore = _isLoadingMore.asStateFlow()

    private val _isRefresh = MutableStateFlow(false)
    val isRefresh = _isRefresh.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private var currentPage = 1
    private var totalPages = 1
    private var perPage = 10

    private var apiKey: String = ""

    fun getUsers(
        apikey: String
    ) {
        if (_isLoading.value) return

        apiKey = apikey

        viewModelScope.launch {

            _isLoading.value = true
            _isError.value = false
            _errorMessage.value = null

            try {

                val response = repository.getListUsers(
                    apikey = apikey,
                    page = 1,
                    perPage = perPage
                )

                _users.value =
                    response.data
                        ?.filterNotNull()
                        ?: emptyList()

                currentPage = response.page ?: 1
                totalPages = response.totalPages ?: 1

            } catch (e: Exception) {

                _isError.value = true

                _errorMessage.value =
                    e.message ?: "Failed to load users"

            } finally {

                _isLoading.value = false
            }
        }
    }

    fun refreshUsers() {
        if (_isRefresh.value) return
        if (apiKey.isBlank()) return

        viewModelScope.launch {
            _isRefresh.value = true
            _isError.value = false
            _errorMessage.value = null

            try {
                val response =
                    repository.getListUsers(
                        apikey = apiKey,
                        page = 1,
                        perPage = perPage
                    )

                _users.value =
                    response.data
                        ?.filterNotNull()
                        ?: emptyList()

                currentPage =
                    response.page ?: 1

                totalPages =
                    response.totalPages ?: 1

            } catch (e: Exception) {

                _isError.value = true
                _errorMessage.value =
                    e.message ?: "Failed to refresh users"

            } finally {
                _isRefresh.value = false
            }
        }
    }

    fun loadMoreUsers() {

        if (_isLoading.value ||
            _isLoadingMore.value ||
            _isRefresh.value
        ) return

        if (currentPage >= totalPages) return

        viewModelScope.launch {
            _isLoadingMore.value = true

            try {
                val nextPage = currentPage + 1

                val response =
                    repository.getListUsers(
                        apikey = apiKey,
                        page = nextPage,
                        perPage = perPage
                    )

                val newUsers =
                    response.data
                        ?.filterNotNull()
                        ?: emptyList()

                _users.value =
                    (_users.value + newUsers)
                        .distinctBy { it.id }

                currentPage =
                    response.page ?: nextPage

                totalPages =
                    response.totalPages ?: totalPages

            } catch (e: Exception) {

                _errorMessage.value =
                    e.message ?: "Failed to load more users"

            } finally {
                _isLoadingMore.value = false
            }
        }
    }
}