package matheusfelipe.desafio.inchurch.core.api

import matheusfelipe.desafio.inchurch.data.models.GenreResultModel
import matheusfelipe.desafio.inchurch.data.models.MovieModel
import matheusfelipe.desafio.inchurch.data.models.PageModel
import retrofit2.Response
import retrofit2.http.GET

interface MovieApi {

    @GET("movie/popular")
    suspend fun getAllMovies(): Response<PageModel<MovieModel>>

    @GET("genre/movie/list")
    suspend fun getAllMoviesGenres(): Response<GenreResultModel>
}