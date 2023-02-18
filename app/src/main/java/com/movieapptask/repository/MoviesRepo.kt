package com.movieapptask.repository

import com.movieapptask.di.ApiInterface
import com.movieapptask.model.MoviesResponse
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

@ViewModelScoped
class MoviesRepo @Inject constructor(private val service: ApiInterface) {

    suspend fun getMovies(): Flow<ApiResult<List<MoviesResponse.Movie>>> = flow {
        emit(ApiResult.Loading)
        try {
            val response = service.getMovies(Locale.getDefault().language, 1)
            emit(ApiResult.Success(response.results))
        } catch (e: IOException) {
            emit(ApiResult.Error(e.localizedMessage.orEmpty()))
        } catch (e: HttpException) {
            emit(ApiResult.Error(e.localizedMessage.orEmpty()))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.localizedMessage.orEmpty()))
        }
    }

    suspend fun getMovieDetails(movie_id: Int): Flow<ApiResult<MoviesResponse.Movie>> = flow {
        emit(ApiResult.Loading)
        try {
            val response = service.getMovieDetails(movie_id, Locale.getDefault().language)
            emit(ApiResult.Success(response))
        } catch (e: IOException) {
            emit(ApiResult.Error(e.localizedMessage.orEmpty()))
        } catch (e: HttpException) {
            emit(ApiResult.Error(e.localizedMessage.orEmpty()))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.localizedMessage.orEmpty()))
        }
    }

}

sealed class ApiResult<out T> {
    object Loading : ApiResult<Nothing>()
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
}