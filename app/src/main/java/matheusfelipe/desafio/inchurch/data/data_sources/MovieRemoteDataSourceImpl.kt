package matheusfelipe.desafio.inchurch.data.data_sources

import matheusfelipe.desafio.inchurch.core.api.MovieApi
import matheusfelipe.desafio.inchurch.core.exceptions.InvalidApiKeyException
import matheusfelipe.desafio.inchurch.core.exceptions.ResourceNotFoundException
import matheusfelipe.desafio.inchurch.data.models.GenreResultModel
import matheusfelipe.desafio.inchurch.data.models.MovieModel
import matheusfelipe.desafio.inchurch.data.models.PageModel
import java.lang.Exception

class MovieRemoteDataSourceImpl(val movieApi: MovieApi) : MovieRemoteDataSource {

    override suspend fun getAllMovies(): PageModel<MovieModel> {
        val response = movieApi.getAllMovies()
        if (response.isSuccessful) {
            return response.body()!!
        }
        else{
            if(response.code() == 401)
                throw InvalidApiKeyException()
            else if(response.code() == 404)
                throw ResourceNotFoundException()

            throw Exception()
        }
    }

    override suspend fun getAllMoviesGenres(): GenreResultModel {
        val response = movieApi.getAllMoviesGenres()
        if (response.isSuccessful) {
            return response.body()!!
        }
        else{
            if(response.code() == 401)
                throw InvalidApiKeyException()
            else if(response.code() == 404)
                throw ResourceNotFoundException()

            throw Exception()
        }
    }
}