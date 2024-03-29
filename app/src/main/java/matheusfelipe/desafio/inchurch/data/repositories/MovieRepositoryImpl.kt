package matheusfelipe.desafio.inchurch.data.repositories

import matheusfelipe.desafio.inchurch.data.data_sources.MovieLocalDataSource
import matheusfelipe.desafio.inchurch.data.data_sources.MovieRemoteDataSource
import matheusfelipe.desafio.inchurch.data.models.MovieModel
import matheusfelipe.desafio.inchurch.domain.entities.Genre
import matheusfelipe.desafio.inchurch.domain.entities.Movie
import matheusfelipe.desafio.inchurch.domain.repositories.MovieRepository

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {

    override suspend fun getAllMovies(page: Int): List<Movie> {
        val movieList = remoteDataSource.getAllMovies(page).results.map {
            it.toEntity()
        }

        val favoriteList = localDataSource.getCachedFavoriteMovies()
        for (movie in movieList) {
            if (favoriteList.firstOrNull { it.id == movie.id } != null)
                movie.isFavorite = true
        }

        return movieList
    }

    override suspend fun getAllMoviesGenres(): List<Genre> {
        return remoteDataSource.getAllMoviesGenres().genres.map {
            it.toEntity()
        }
    }

    override suspend fun cacheDetailMovie(movie: Movie) {
        localDataSource.cacheDetailMovie(MovieModel.fromEntity(movie))
    }

    override suspend fun getCachedDetailMovie(): Movie {
        val movie = localDataSource.getCachedDetailMovie().toEntity()

        val favoriteList = localDataSource.getCachedFavoriteMovies()
        if (favoriteList.firstOrNull { it.id == movie.id } != null)
            movie.isFavorite = true

        return movie
    }

    override suspend fun favoriteOrDisfavorMovie(movie: Movie): Movie {
        var favoriteMovies = localDataSource.getCachedFavoriteMovies()
        if (favoriteMovies.firstOrNull { it.id == movie.id } == null) {
            favoriteMovies = favoriteMovies.apply { this.add(MovieModel.fromEntity(movie)) }
            localDataSource.cacheFavoriteMovies(favoriteMovies)
            return movie.apply { this.isFavorite = true }
        } else {
            favoriteMovies = favoriteMovies.apply { this.removeAll { it.id == movie.id } }
            localDataSource.cacheFavoriteMovies(favoriteMovies)
            return movie.apply { this.isFavorite = false }
        }
    }

    override suspend fun getFavoriteMovies(filter: String): List<Movie> {
        return localDataSource.getCachedFavoriteMovies(filter).map {
            it.toEntity()
        }
    }
}