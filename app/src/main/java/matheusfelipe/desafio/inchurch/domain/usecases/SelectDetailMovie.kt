package matheusfelipe.desafio.inchurch.domain.usecases

import matheusfelipe.desafio.inchurch.data.repositories.MovieRepositoryImpl
import matheusfelipe.desafio.inchurch.domain.entities.Movie
import matheusfelipe.desafio.inchurch.domain.repositories.MovieRepository

class SelectDetailMovie(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(movie: Movie) {
        return movieRepository.cacheDetailMovie(movie)
    }
}