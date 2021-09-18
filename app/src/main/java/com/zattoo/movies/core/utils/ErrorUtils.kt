package com.zattoo.movies.core.utils

import com.zattoo.movies.R

fun errorMapper(exception: Int): Int {
    return when (exception) {
        204 -> R.string.empty_list
        403 -> R.string.forbidden_error_message
        404 -> R.string.not_found_error_message
        500 -> R.string.internal_server_error_message
        else -> R.string.general_error_message
    }
}