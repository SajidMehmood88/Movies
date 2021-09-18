package com.zattoo.movies.presentation.home

import com.zattoo.movies.domain.DomainMovies

sealed class UIState {
    object Loading : UIState()
    data class Ready(val list: List<DomainMovies>) : UIState()
    data class Error(val error: Int) : UIState()
}