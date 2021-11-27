package com.achmadalfiansyah.moviecatalog.data.source.remote.response
import com.achmadalfiansyah.moviecatalog.data.source.remote.response.StatusResponse.ERROR
import com.achmadalfiansyah.moviecatalog.data.source.remote.response.StatusResponse.SUCCESS

class ApiResponse<T>(val status: StatusResponse, val body: T?, val message: String?) {
    companion object {
        fun <T> success(body: T): ApiResponse<T> = ApiResponse(SUCCESS, body, null)

        fun <T> error(msg: String): ApiResponse<T> = ApiResponse(ERROR, null, msg)
    }
}