package matheusfelipe.desafio.inchurch.data.data_sources

import matheusfelipe.desafio.inchurch.data.models.GenreResultModel
import matheusfelipe.desafio.inchurch.data.models.MovieModel
import matheusfelipe.desafio.inchurch.data.models.PageModel

interface MovieRemoteDataSource {

    suspend fun getAllMovies(page: Int = 1): PageModel<MovieModel>
    suspend fun getAllMoviesGenres(): GenreResultModel
}