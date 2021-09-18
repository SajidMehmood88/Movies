package com.zattoo.movies.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zattoo.movies.core.utils.errorMapper
import com.zattoo.movies.domain.*
import com.zattoo.movies.domain.home.GetMoviesUseCaseImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

class HomeViewModel(private val getMoviesUseCase: GetMoviesUseCaseImpl) : ViewModel() {

    private val _viewState = MutableLiveData<UIState>()
    val viewState: LiveData<UIState> = _viewState

    fun fetchMovies() {
        viewModelScope.launch {
            getMoviesUseCase.execute()
                .onStart { onLoading() }
                .catch { exception -> onError(exception) }
                .collect {
                    onComplete(it)
                }
        }
    }

    private fun onComplete(it: Results<List<DomainMovies>>) {
        if (it is Success) {
            it.onSuccess { item ->
                _viewState.value = UIState.Ready(item)
            }
        } else {
            it.onError { item ->
                _viewState.value = UIState.Error(errorMapper(item.hashCode()))
            }
        }
    }

    private fun onError(exception: Throwable) {
        UIState.Error(errorMapper(exception.hashCode()))
    }

    private fun onLoading() {
        _viewState.value = UIState.Loading
    }
}