package prus.justweatherapp.core.common.result

sealed class RequestResult<T>(internal val data: T? = null) {
    class Loading<T>(data: T? = null) : RequestResult<T>(data)
    class Success<T>(data: T) : RequestResult<T>(data)
    class Error<T>(data: T?) : RequestResult<T>()
}

fun <T> Result<T>.toRequestResult(): RequestResult<T> {
    return when {
        isSuccess -> RequestResult.Success(getOrThrow())
        isFailure -> RequestResult.Error(null)
        else -> error("Impossible error")
    }
}
