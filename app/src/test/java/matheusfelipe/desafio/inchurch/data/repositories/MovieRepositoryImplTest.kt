package matheusfelipe.desafio.inchurch.data.repositories

import io.mockk.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import matheusfelipe.desafio.inchurch.core.exceptions.InvalidApiKeyThrowable
import matheusfelipe.desafio.inchurch.core.exceptions.ResourceNotFoundThrowable
import matheusfelipe.desafio.inchurch.data.data_sources.MovieLocalDataSource
import matheusfelipe.desafio.inchurch.data.data_sources.MovieRemoteDataSource
import matheusfelipe.desafio.inchurch.data.models.GenreModel
import matheusfelipe.desafio.inchurch.data.models.GenreResultModel
import matheusfelipe.desafio.inchurch.data.models.MovieModel
import matheusfelipe.desafio.inchurch.data.models.PageModel
import matheusfelipe.desafio.inchurch.domain.entities.Genre
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
    private lateinit var mockLocalDataSource: MovieLocalDataSource

    private lateinit var tPageModel: PageModel<MovieModel>
    private lateinit var tMovieModel: MovieModel
    private lateinit var tMovie: Movie

    private lateinit var tGenreResultModel: GenreResultModel
    private lateinit var tGenreModel: GenreModel
    private lateinit var tGenre: Genre

    private lateinit var tMockMovie1: Movie
    private lateinit var tMockMovie2: Movie

    private lateinit var tMockMovieModel1: MovieModel
    private lateinit var tMockMovieModel2: MovieModel

    @get:Rule
    var thrown = ExpectedException.none()


    @Before
    fun setup(){
        mockRemoteDataSource = mockk()
        mockLocalDataSource = mockk()
        movieRepository = MovieRepositoryImpl(mockRemoteDataSource, mockLocalDataSource)

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

        tPageModel = PageModel<MovieModel>(
            page = 1,
            results = listOf(
                tMovieModel
            ),
            total_results = 50,
            total_pages = 1
        )

        tGenre = Genre(28, "Action")
        tGenreModel = GenreModel(28, "Action")
        tGenreResultModel = GenreResultModel(listOf(GenreModel(28, "Action")))

        tMockMovie1 = mockk()
        tMockMovie2 = mockk()

        tMockMovieModel1 = mockk()
        tMockMovieModel2 = mockk()
    }

    @Test
    fun `getAllMovies - should return movie data when response is successfull`() = runBlocking{
        // arrange
        coEvery { mockRemoteDataSource.getAllMovies() } returns tPageModel
        coEvery { mockLocalDataSource.getCachedFavoriteMovies() } returns mutableListOf()
        // act
        val result = async {movieRepository.getAllMovies()}.await()
        // assert
        assertEquals(listOf(tMovie), result)
        coVerify(exactly = 1) { mockRemoteDataSource.getAllMovies() }
    }

    @Test
    fun `getAllMovies - should return movie data and call dataSource with correct param`() = runBlocking{
        // arrange
        val tPage = 2
        coEvery { mockRemoteDataSource.getAllMovies(any()) } returns tPageModel
        coEvery { mockLocalDataSource.getCachedFavoriteMovies() } returns mutableListOf()
        // act
        val result = async {movieRepository.getAllMovies(tPage)}.await()
        // assert
        assertEquals(listOf(tMovie), result)
        coVerify(exactly = 1) { mockRemoteDataSource.getAllMovies(tPage) }
    }

    @Test
    fun `getAllMovies - should throw InvalidApiKeyException when error is 401`()  {
        // arrange
        coEvery { mockRemoteDataSource.getAllMovies() } throws InvalidApiKeyThrowable()
        thrown.expect(InvalidApiKeyThrowable::class.java)
        // act
        runBlocking {
            movieRepository.getAllMovies()
        }
    }

    @Test
    fun `getAllMovies - should throw ResourceNotFoundException when error is 404`()  {
        // arrange
        coEvery { mockRemoteDataSource.getAllMovies() } throws ResourceNotFoundThrowable()
        // act
        thrown.expect(ResourceNotFoundThrowable::class.java)
        runBlocking {
            movieRepository.getAllMovies()
        }
    }

    @Test
    fun `getAllMoviesGenres - should return genre data when response is sucessfull`() = runBlocking {
        // arange
        coEvery { mockRemoteDataSource.getAllMoviesGenres() } returns tGenreResultModel
        // act
        val result = async {movieRepository.getAllMoviesGenres()}.await()
        // assert
        assertEquals(listOf(tGenre), result)
        coVerify(exactly = 1) {
            mockRemoteDataSource.getAllMoviesGenres()
        }
    }

    @Test
    fun `getAllMoviesGenres- should throw InvalidApiKeyException when error is 401`()  {
        // arrange
        coEvery { mockRemoteDataSource.getAllMoviesGenres() } throws InvalidApiKeyThrowable()
        thrown.expect(InvalidApiKeyThrowable::class.java)
        // act
        runBlocking {
            movieRepository.getAllMoviesGenres()
        }
    }

    @Test
    fun `getAllMoviesGenres - should throw ResourceNotFoundException when error is 404`()  {
        // arrange
        coEvery { mockRemoteDataSource.getAllMoviesGenres() } throws ResourceNotFoundThrowable()
        // act
        thrown.expect(ResourceNotFoundThrowable::class.java)
        runBlocking {
            movieRepository.getAllMoviesGenres()
        }
    }

    @Test
    fun `getCachedDetailMovie - should return cached detail movie when response is sucessfull`() = runBlocking {
        // arange
        coEvery { mockLocalDataSource.getCachedDetailMovie() } returns tMovieModel
        coEvery { mockLocalDataSource.getCachedFavoriteMovies() } returns mutableListOf()
        // act
        val result = async {movieRepository.getCachedDetailMovie()}.await()
        // assert
        assertEquals(tMovie, result)
        coVerify(exactly = 1) {
            mockLocalDataSource.getCachedDetailMovie()
        }
    }

    @Test
    fun `getCachedDetailMovie - should throw ResourceNotFoundException when error is 404`()  {
        // arrange
        coEvery { mockLocalDataSource.getCachedDetailMovie() }  throws ResourceNotFoundThrowable()
        // act
        thrown.expect(ResourceNotFoundThrowable::class.java)
        runBlocking {
            mockLocalDataSource.getCachedDetailMovie()
        }
    }


    @Test
    fun `cacheDetailMovie - should call localDataSource`() = runBlocking {
        // arange
        coEvery { mockLocalDataSource.cacheDetailMovie(any()) } returns Unit
        // act
        async {movieRepository.cacheDetailMovie(tMovie)}.await()
        // assert
        coVerify(exactly = 1) {
            mockLocalDataSource.cacheDetailMovie(tMovieModel)
        }
    }

    @Test
    fun `getFavoriteMovies - should return cachedListMovie when there is favorite movies`() = runBlocking {
        // arrange
        every { tMockMovie1.id } returns 1
        every { tMockMovie2.id } returns 2
        val listMockedMovies = mutableListOf<Movie>(tMockMovie1, tMockMovie2)

        every { tMockMovieModel1.id } returns 1
        every { tMockMovieModel1.toEntity() } returns tMockMovie1
        every { tMockMovieModel2.id } returns 2
        every { tMockMovieModel2.toEntity() } returns tMockMovie2
        val listMockedMoviesModel = mutableListOf<MovieModel>(tMockMovieModel1, tMockMovieModel2)

        coEvery { mockLocalDataSource.getCachedFavoriteMovies() } returns listMockedMoviesModel
        // act
        val result = movieRepository.getFavoriteMovies()
        // assert
        assertEquals(listMockedMovies, result)
        coVerify(exactly = 1) {
            mockLocalDataSource.getCachedFavoriteMovies()
        }
    }

    @Test
    fun `getFavoriteMovies - should return filtered cachedListMovie when there is favorite movies`() = runBlocking {
        // arrange
        val tFilter = "filter"

        every { tMockMovie1.id } returns 1
        every { tMockMovie2.id } returns 2
        val listMockedMovies = mutableListOf<Movie>(tMockMovie1, tMockMovie2)

        every { tMockMovieModel1.id } returns 1
        every { tMockMovieModel1.toEntity() } returns tMockMovie1
        every { tMockMovieModel2.id } returns 2
        every { tMockMovieModel2.toEntity() } returns tMockMovie2
        val listMockedMoviesModel = mutableListOf<MovieModel>(tMockMovieModel1, tMockMovieModel2)

        coEvery { mockLocalDataSource.getCachedFavoriteMovies(any()) } returns listMockedMoviesModel
        // act
        val result = movieRepository.getFavoriteMovies(tFilter)
        // assert
        assertEquals(listMockedMovies, result)
        coVerify(exactly = 1) {
            mockLocalDataSource.getCachedFavoriteMovies(tFilter)
        }
    }

    @Test
    fun `favoriteOrDisfavorMovie - should call dataSource with incremented list when is for add`() = runBlocking {
        // arrange
        val tMockMovie1 = mockk<MovieModel>()
        val tMockMovie2 = mockk<MovieModel>()
        every { tMockMovie1.id } returns 1
        every { tMockMovie2.id } returns 2

        val tMockMovie3 = mockk<MovieModel>() //Not added to cached favorites
        every { tMockMovie3.id } returns 3

        val tMockMovieToAdd = mockk<Movie>()
        every { tMockMovieToAdd.id } returns 3

        mockkObject(MovieModel.Companion)
        every { MovieModel.fromEntity(any()) } returns tMockMovie3

        val listMockedMovies = mutableListOf(tMockMovie1, tMockMovie2)
        every { tMockMovieToAdd::isFavorite.setter.invoke(any()) } returns Unit
        coEvery { mockLocalDataSource.getCachedFavoriteMovies() } returns listMockedMovies
        coEvery { mockLocalDataSource.cacheFavoriteMovies(any()) } returns Unit
        // act
        async { movieRepository.favoriteOrDisfavorMovie(tMockMovieToAdd) }.await()
        // assert
        coVerify(exactly = 1) {
            mockLocalDataSource.getCachedFavoriteMovies()
            mockLocalDataSource.cacheFavoriteMovies(mutableListOf(tMockMovie1, tMockMovie2, tMockMovie3))
        }
    }

    @Test
    fun `favoriteOrDisfavorMovie - should call dataSource with decremented list when is for remove`() = runBlocking {
        // arrange
        val tMockMovie1 = mockk<MovieModel>()
        val tMockMovie2 = mockk<MovieModel>()
        every { tMockMovie1.id } returns 1
        every { tMockMovie2.id } returns 2

        val tMockMovieToRemove = mockk<Movie>()
        every { tMockMovieToRemove.id } returns 1
        every { tMockMovieToRemove::isFavorite.setter.invoke(any()) } returns Unit

        val listMockedMovies = mutableListOf(tMockMovie1, tMockMovie2)
        coEvery { mockLocalDataSource.getCachedFavoriteMovies() } returns listMockedMovies
        coEvery { mockLocalDataSource.cacheFavoriteMovies(any()) } returns Unit
        // act
        movieRepository.favoriteOrDisfavorMovie(tMockMovieToRemove)
        // assert
        coVerify(exactly = 1) {
            mockLocalDataSource.getCachedFavoriteMovies()
            mockLocalDataSource.cacheFavoriteMovies(mutableListOf(tMockMovie2))
        }
    }
}