package matheusfelipe.desafio.inchurch.domain.usecases

import matheusfelipe.desafio.inchurch.domain.entities.Movie
import matheusfelipe.desafio.inchurch.domain.repositories.MovieRepository

class ViewDetailMovie(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(): Movie {
        return movieRepository.getCachedDetailMovie()
    }
}