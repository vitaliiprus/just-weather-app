package prus.justweatherapp.data.weather.mergestrategy

import prus.justweatherapp.core.common.result.RequestResult

interface MergeStrategy<E> {
    fun merge(cache: E, server: E): E
}

class ForecastWeatherMergeStrategy<T : Any> : MergeStrategy<RequestResult<T>> {
    override fun merge(cache: RequestResult<T>, server: RequestResult<T>): RequestResult<T> {
        return when {
            cache is RequestResult.Loading && server is RequestResult.Loading ->
                merge(cache, server)

            cache is RequestResult.Success && server is RequestResult.Loading ->
                merge(cache, server)

            cache is RequestResult.Success && server is RequestResult.Success ->
                merge(cache, server)

            cache is RequestResult.Success && server is RequestResult.Error ->
                merge(cache, server)

            else -> error("")
        }
    }

    private fun merge(
        cache: RequestResult.Loading<T>,
        server: RequestResult.Loading<T>,
    ): RequestResult<T> {
        return when {
            server.data != null -> RequestResult.Loading(server.data)
            else -> RequestResult.Loading(cache.data)
        }
    }

    private fun merge(
        cache: RequestResult.Success<T>,
        server: RequestResult.Loading<T>,
    ): RequestResult<T> {
        return RequestResult.Loading(cache.data)
    }

    private fun merge(
        cache: RequestResult.Success<T>,
        server: RequestResult.Success<T>,
    ): RequestResult<T> {
        return when {
            server.data != null -> RequestResult.Success(server.data!!)
            else -> RequestResult.Success(checkNotNull(cache.data))
        }
    }

    private fun merge(
        cache: RequestResult.Success<T>,
        server: RequestResult.Error<T>,
    ): RequestResult<T> {
        return RequestResult.Error(data = cache.data, error = server.error)
    }

}