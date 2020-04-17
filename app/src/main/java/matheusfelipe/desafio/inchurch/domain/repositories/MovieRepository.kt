package matheusfelipe.desafio.inchurch.domain.repositories

import matheusfelipe.desafio.inchurch.domain.entities.Genre
import matheusfelipe.desafio.inchurch.domain.entities.Movie

interface MovieRepository {
   fun getAllMovies(): List<Movie>
   fun getAllMoviesGenres(): List<Genre>
}