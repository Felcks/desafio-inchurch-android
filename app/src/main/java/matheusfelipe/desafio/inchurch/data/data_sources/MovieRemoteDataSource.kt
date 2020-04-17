package matheusfelipe.desafio.inchurch.data.data_sources

import matheusfelipe.desafio.inchurch.data.models.MovieModel
import matheusfelipe.desafio.inchurch.data.models.PageModel

interface MovieRemoteDataSource {

    suspend fun getAllMovies(): PageModel<MovieModel>
    //suspend fun getAllMoviesGenres(): List<Genre>
}