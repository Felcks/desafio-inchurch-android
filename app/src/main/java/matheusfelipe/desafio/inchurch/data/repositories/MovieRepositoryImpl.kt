package matheusfelipe.desafio.inchurch.data.repositories

import matheusfelipe.desafio.inchurch.data.data_sources.MovieRemoteDataSource
import matheusfelipe.desafio.inchurch.domain.entities.Genre
import matheusfelipe.desafio.inchurch.domain.entities.Movie
import matheusfelipe.desafio.inchurch.domain.repositories.MovieRepository

class MovieRepositoryImpl(val remoteDataSource: MovieRemoteDataSource): MovieRepository {

    override suspend fun getAllMovies(): List<Movie> {
        return remoteDataSource.getAllMovies().results.map {
            it.toEntity()
        }
    }

    override suspend fun getAllMoviesGenres(): List<Genre> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}