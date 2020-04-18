package matheusfelipe.desafio.inchurch.domain.usecases

import matheusfelipe.desafio.inchurch.domain.entities.Movie
import matheusfelipe.desafio.inchurch.domain.repositories.MovieRepository

class FavoriteOrDisfavorMovie(private val repository: MovieRepository) {

    suspend operator fun invoke(movie: Movie) {
        repository.favoriteOrDisfavorMovie(movie)
    }
}