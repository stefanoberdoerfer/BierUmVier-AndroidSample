package de.stefanoberdoerfer.bierumvier.data.network.model

import androidx.annotation.StringRes
import de.stefanoberdoerfer.bierumvier.R
import retrofit2.Response

sealed class NetReqState {
    object Loading : NetReqState()
    object Success : NetReqState()
    class Error(val cause: NetworkError) : NetReqState() {
        companion object {
            fun fromErrorResponse(response: Response<*>) = Error(NetworkError.from(response.code()))
        }
    }
}

enum class NetworkError(val code: Int?, @StringRes val uiStringRes: Int) {
    E404(404, R.string.network_error),
    E500(500, R.string.network_error),
    Unknown(null, R.string.network_error),
    NoInternet(null, R.string.no_internet);

    companion object {
        fun from(code: Int): NetworkError = values().find { it.code == code } ?: Unknown
    }
}
