package matheusfelipe.desafio.inchurch.data.data_sources

import matheusfelipe.desafio.inchurch.BuildConfig
import matheusfelipe.desafio.inchurch.core.api.MovieApi
import matheusfelipe.desafio.inchurch.core.exceptions.InvalidApiKeyThrowable
import matheusfelipe.desafio.inchurch.core.exceptions.ResourceNotFoundThrowable
import matheusfelipe.desafio.inchurch.data.models.GenreResultModel
import matheusfelipe.desafio.inchurch.data.models.MovieModel
import matheusfelipe.desafio.inchurch.data.models.PageModel
import java.lang.Exception

class MovieRemoteDataSourceImpl(private val movieApi: MovieApi) : MovieRemoteDataSource {

    override suspend fun getAllMovies(): PageModel<MovieModel> {
        val response = movieApi.getAllMovies(BuildConfig.API_KEY)
        if (response.isSuccessful) {
            return response.body()!!
        }
        else{
            if(response.code() == 401)
                throw InvalidApiKeyThrowable()
            else if(response.code() == 404)
                throw ResourceNotFoundThrowable()

            throw Exception()
        }
    }

    override suspend fun getAllMoviesGenres(): GenreResultModel {
        val response = movieApi.getAllMoviesGenres(BuildConfig.API_KEY)
        if (response.isSuccessful) {
            return response.body()!!
        }
        else{
            if(response.code() == 401)
                throw InvalidApiKeyThrowable()
            else if(response.code() == 404)
                throw ResourceNotFoundThrowable()

            throw Exception()
        }
    }
}