package prus.justweatherapp.data.weather.mergestrategy

import prus.justweatherapp.core.common.result.RequestResult
import timber.log.Timber

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

            cache is RequestResult.Loading && server is RequestResult.Success ->
                merge(cache, server)

            cache is RequestResult.Success && server is RequestResult.Success ->
                merge(cache, server)

            cache is RequestResult.Success && server is RequestResult.Error ->
                merge(cache, server)

            cache is RequestResult.Error && server is RequestResult.Success ->
                merge(cache, server)

            else -> {
                Timber.e("ForecastWeatherMergeStrategy IllegalState: cache is ${cache.javaClass.simpleName}, server is ${server.javaClass.simpleName}")
                error("")
            }
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
        cache: RequestResult.Loading<T>,
        server: RequestResult.Success<T>,
    ): RequestResult<T> {
        return RequestResult.Success(checkNotNull(server.data))
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

    private fun merge(
        cache: RequestResult.Error<T>,
        server: RequestResult.Success<T>,
    ): RequestResult<T> {
        return RequestResult.Success(data = checkNotNull(server.data))
    }

}