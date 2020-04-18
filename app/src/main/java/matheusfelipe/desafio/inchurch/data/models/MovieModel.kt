package matheusfelipe.desafio.inchurch.data.models

import matheusfelipe.desafio.inchurch.domain.entities.Movie
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*

data class MovieModel(
    val poster_path: String,
    val adult: Boolean,
    val overview: String,
    val release_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_title: String,
    val original_language: String,
    val title: String,
    val backdrop_path: String?,
    val popularity: Double,
    val vote_count: Int,
    val video: Boolean,
    val vote_average: Double
) {

    fun toEntity(): Movie {
        return Movie(
            posterPath = poster_path,
            isAdult = adult,
            overview = overview,
            releaseDate = SimpleDateFormat("yyyy-MM-dd").parse(release_date) ?: Date(),
            genreIds = genre_ids,
            id = id,
            originalTitle = original_title,
            originalLanguage = original_language,
            title = title,
            popularity = popularity,
            voteCount = vote_count,
            video = video,
            backdropPath = backdrop_path ?: "",
            voteAverage = vote_average
        )
    }

    companion object {
        fun fromEntity(entity: Movie): MovieModel {
            with(entity) {
                return MovieModel(
                    id = id,
                    original_title = originalTitle,
                    original_language = originalLanguage,
                    title = title,
                    poster_path = posterPath,
                    adult = isAdult,
                    overview = overview,
                    release_date = SimpleDateFormat("yyyy-MM-dd").format(releaseDate),
                    popularity = popularity,
                    vote_count = voteCount,
                    video = video,
                    vote_average = voteAverage,
                    genre_ids = genreIds,
                    backdrop_path = backdropPath
                )
            }
        }
    }
}