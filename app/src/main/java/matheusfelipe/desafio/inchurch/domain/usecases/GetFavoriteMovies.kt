package matheusfelipe.desafio.inchurch.domain.usecases

import matheusfelipe.desafio.inchurch.domain.entities.Movie
import matheusfelipe.desafio.inchurch.domain.repositories.MovieRepository

class GetFavoriteMovies(private val repository: MovieRepository) {

    suspend operator fun invoke(): List<Movie> {
        return repository.getFavoriteMovies()
    }
}