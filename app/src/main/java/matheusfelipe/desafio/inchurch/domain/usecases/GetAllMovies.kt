package matheusfelipe.desafio.inchurch.domain.usecases

import matheusfelipe.desafio.inchurch.domain.entities.Movie
import matheusfelipe.desafio.inchurch.domain.repositories.MovieRepository

class GetAllMovies(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(page: Int = 1): List<Movie> {
        return movieRepository.getAllMovies(page)
    }
}