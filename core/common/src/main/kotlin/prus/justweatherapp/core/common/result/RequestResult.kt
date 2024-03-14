package prus.justweatherapp.core.common.result

sealed class RequestResult<out T>(val data: T? = null) {
    class Success<T>(data: T) : RequestResult<T>(data)
    class Loading<T>(data: T? = null) : RequestResult<T>(data)
    class Error<T>(data: T? = null, val error: Throwable? = null) : RequestResult<T>()
}

fun <I, O> RequestResult<I>.map(mapper: (I) -> O): RequestResult<O> {
    return when (this) {
        is RequestResult.Success -> {
            RequestResult.Success(
                data = mapper(checkNotNull(data))
            )
        }

        is RequestResult.Loading -> {
            RequestResult.Loading(
                data = data?.let(mapper)
            )
        }

        is RequestResult.Error -> {
            RequestResult.Error(
                data = data?.let(mapper),
                error = error
            )
        }
    }
}

fun <T : Any> Result<T>.toRequestResult(): RequestResult<T> {
    return when {
        isSuccess -> RequestResult.Success(getOrThrow())
        isFailure -> RequestResult.Error(error = this.exceptionOrNull())
        else -> error("Impossible error")
    }
}
