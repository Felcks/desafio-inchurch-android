package matheusfelipe.desafio.inchurch.domain.repositories

import matheusfelipe.desafio.inchurch.domain.entities.Genre
import matheusfelipe.desafio.inchurch.domain.entities.Movie

interface MovieRepository {
   suspend fun getAllMovies(): List<Movie>
   suspend fun getAllMoviesGenres(): List<Genre>
}