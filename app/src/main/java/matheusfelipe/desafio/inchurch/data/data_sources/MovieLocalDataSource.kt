package matheusfelipe.desafio.inchurch.data.data_sources

import matheusfelipe.desafio.inchurch.data.models.MovieModel
import matheusfelipe.desafio.inchurch.domain.entities.Movie

interface MovieLocalDataSource {

    suspend fun cacheDetailMovie(movie: MovieModel)
    suspend fun getCachedDetailMovie(): MovieModel

    suspend fun cacheFavoriteMovies(movies: MutableList<MovieModel>)
    suspend fun getCachedFavoriteMovies(filter: String = ""): MutableList<MovieModel>
}