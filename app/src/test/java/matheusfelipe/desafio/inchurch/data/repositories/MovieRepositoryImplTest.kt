package matheusfelipe.desafio.inchurch.data.repositories

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import matheusfelipe.desafio.inchurch.core.exceptions.InvalidApiKeyException
import matheusfelipe.desafio.inchurch.core.exceptions.ResourceNotFoundException
import matheusfelipe.desafio.inchurch.data.data_sources.MovieRemoteDataSource
import matheusfelipe.desafio.inchurch.data.models.MovieModel
import matheusfelipe.desafio.inchurch.data.models.PageModel
import matheusfelipe.desafio.inchurch.domain.entities.Movie
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.text.SimpleDateFormat
import java.util.*


class MovieRepositoryImplTest {

    private lateinit var movieRepository: MovieRepositoryImpl
    private lateinit var mockRemoteDataSource: MovieRemoteDataSource

    private lateinit var tPageModel: PageModel<MovieModel>
    private lateinit var tMovieModel: MovieModel
    private lateinit var tMovie: Movie

    @get:Rule
    var thrown = ExpectedException.none()


    @Before
    fun setup(){
        mockRemoteDataSource = mockk()
        movieRepository = MovieRepositoryImpl(mockRemoteDataSource)

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
            genreIds = listOf(14, 28, 80)
        )

        tPageModel = PageModel<MovieModel>(
            page = 1,
            results = listOf(
                tMovieModel
            ),
            total_results = 50,
            total_pages = 1
        )
    }

    @Test
    fun `should return movie data when response is successfull`() = runBlocking{
        // arrange
        coEvery { mockRemoteDataSource.getAllMovies() } returns tPageModel
        // act
        val result = async {movieRepository.getAllMovies()}.await()
        // assert
        assertEquals(listOf(tMovie), result)
        coVerify(exactly = 1) { mockRemoteDataSource.getAllMovies() }
    }

    @Test
    fun `should throw InvalidApiKeyException when error is 401`()  {
        // arrange
        coEvery { mockRemoteDataSource.getAllMovies() } throws InvalidApiKeyException()
        thrown.expect(InvalidApiKeyException::class.java)
        // act
        runBlocking {
            movieRepository.getAllMovies()
        }
    }

    @Test
    fun `should throw ResourceNotFoundException when error is 404`()  {
        // arrange
        coEvery { mockRemoteDataSource.getAllMovies() } throws ResourceNotFoundException()
        // act
        thrown.expect(ResourceNotFoundException::class.java)
        runBlocking {
            movieRepository.getAllMovies()
        }
    }
}