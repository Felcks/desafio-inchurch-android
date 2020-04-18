package matheusfelipe.desafio.inchurch.data.data_sources

import matheusfelipe.desafio.inchurch.data.models.MovieModel

interface MovieLocalDataSource {

    suspend fun cacheDetailMovie(movie: MovieModel)
    suspend fun getCachedDetailMovie(): MovieModel
}