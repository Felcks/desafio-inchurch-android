package matheusfelipe.desafio.inchurch.domain.entities

import java.time.ZonedDateTime

data class Movie(
    val id: Int,
    val originalTitle: String,
    val originalLanguage: String,
    val title: String,
    val posterPath: String,
    val isAdult: Boolean,
    val overview: String,
    val releaseDate: ZonedDateTime,
    val popularity: Double,
    val voteCount: Int,
    val video: Boolean,
    val voteAverage: Double,
    var genreIds: List<Int>
) {

    fun getGenres(allGenres: List<Genre>): List<Genre> {
        return allGenres.filter {
            genreIds.contains(it.id)
        }
    }
}