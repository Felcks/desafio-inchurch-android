package matheusfelipe.desafio.inchurch.data.data_sources

import matheusfelipe.desafio.inchurch.core.exceptions.ResourceNotFoundThrowable
import matheusfelipe.desafio.inchurch.data.models.MovieModel
import matheusfelipe.desafio.inchurch.domain.entities.Movie

class MovieLocalDataSourceImpl: MovieLocalDataSource {

    override suspend fun cacheDetailMovie(movie: MovieModel) {
        InCacheDao.cacheMovie(movie)
    }

    override suspend fun getCachedDetailMovie(): MovieModel {
        return InCacheDao.getChachedMovie() ?: throw ResourceNotFoundThrowable()
    }

    override suspend fun cacheFavoriteMovies(movies: List<Movie>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getCachedFavoriteMovies(): List<Movie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}