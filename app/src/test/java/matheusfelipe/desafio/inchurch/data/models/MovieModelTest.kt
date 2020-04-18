package matheusfelipe.desafio.inchurch.data.models

import br.com.mobfiq.search.core.readJsonFile
import com.google.gson.Gson
import matheusfelipe.desafio.inchurch.domain.entities.Movie
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class MovieModelTest {

    private lateinit var tMovieModel: MovieModel
    private lateinit var tMovie: Movie

    @Before
    fun setup(){
        tMovieModel = MovieModel(
            id = 297761,
            original_title =  "Suicide Squad",
            original_language = "en",
            title = "Suicide Squad",
            poster_path =  "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
            adult = false,
            overview = "Movie description",
            release_date = "2016-08-03",
            popularity = 48.261451,
            vote_count = 1466,
            video = false,
            vote_average = 5.91,
            genre_ids = listOf(14, 28, 80),
            backdrop_path = "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg"
        )

        tMovie = Movie(
            id = 297761,
            originalTitle =  "Suicide Squad",
            originalLanguage = "en",
            title = "Suicide Squad",
            posterPath =  "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
            isAdult = false,
            overview = "Movie description",
            releaseDate = SimpleDateFormat("yyyy-MM-dd").parse("2016-08-03") ?: Date(),
            popularity = 48.261451,
            voteCount = 1466,
            video = false,
            voteAverage = 5.91,
            backdropPath = "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg",
            genreIds = listOf(14, 28, 80)
        )
    }

    @Test
    fun `should return a valid entity when fields are ok`(){
        // arrange
        // act
        val result = tMovieModel.toEntity()
        // assert
        Assert.assertEquals(tMovie, result)
    }

    @Test
    fun `should return a valid model when JSON is ok`(){
        // arrange
        // act
        val result = Gson().fromJson<MovieModel>(readJsonFile("movie.json"), MovieModel::class.java)
        // assert
        Assert.assertEquals(tMovieModel, result)
    }
}