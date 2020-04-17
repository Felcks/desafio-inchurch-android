package matheusfelipe.desafio.inchurch.domain.usecases

import matheusfelipe.desafio.inchurch.domain.entities.Genre
import matheusfelipe.desafio.inchurch.domain.repositories.MovieRepository

class GetAllMoviesGenres(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(): List<Genre> {
        return movieRepository.getAllMoviesGenres()
    }
}