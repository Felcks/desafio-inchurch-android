package matheusfelipe.desafio.inchurch.data.data_sources

import matheusfelipe.desafio.inchurch.core.exceptions.ResourceNotFoundThrowable
import matheusfelipe.desafio.inchurch.data.models.MovieModel

class MovieLocalDataSourceImpl: MovieLocalDataSource {

    override suspend fun cacheDetailMovie(movie: MovieModel) {
        InCacheDao.cacheMovie(movie)
    }

    override suspend fun getCachedDetailMovie(): MovieModel {
        return InCacheDao.getChachedMovie() ?: throw ResourceNotFoundThrowable()
    }

    override suspend fun cacheFavoriteMovies(movies: MutableList<MovieModel>) {
        InCacheDao.setCachedFavoriteMovies(movies.toMutableList())
    }

    override suspend fun getCachedFavoriteMovies(filter: String): MutableList<MovieModel> {
        return InCacheDao.getCachedFavoriteMovies().filter {
            it.title.contains(filter)
        }.toMutableList()
    }
}