package matheusfelipe.desafio.inchurch.core.api

import matheusfelipe.desafio.inchurch.data.models.GenreResultModel
import matheusfelipe.desafio.inchurch.data.models.MovieModel
import matheusfelipe.desafio.inchurch.data.models.PageModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getAllMovies(@Query(value = "api_key") apiKey: String): Response<PageModel<MovieModel>>

    @GET("genre/movie/list")
    suspend fun getAllMoviesGenres(@Query(value = "api_key") apiKey: String): Response<GenreResultModel>
}