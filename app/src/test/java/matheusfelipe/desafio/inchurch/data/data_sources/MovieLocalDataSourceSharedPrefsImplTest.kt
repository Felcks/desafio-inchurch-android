package matheusfelipe.desafio.inchurch.data.data_sources

import android.content.SharedPreferences
import com.google.gson.Gson
import io.mockk.*
import kotlinx.coroutines.runBlocking
import matheusfelipe.desafio.inchurch.core.exceptions.ResourceNotFoundThrowable
import matheusfelipe.desafio.inchurch.data.data_sources.MovieLocalDataSourceSharedPrefsImpl.Companion.SHARED_PREFERENCES_FAVORITE_MOVIES_KEY
import matheusfelipe.desafio.inchurch.data.models.MovieModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class MovieLocalDataSourceSharedPrefsImplTest {

    private lateinit var movieLocalDataSourceImpl: MovieLocalDataSourceSharedPrefsImpl
    private lateinit var mockSharedPreferences: SharedPreferences
    private lateinit var mockSharedPreferencesEditor: SharedPreferences.Editor
    private lateinit var mockGson: Gson

    private lateinit var tMovieModel1: MovieModel
    private lateinit var tMovieModel2: MovieModel

    private lateinit var tHashSet: HashSet<String>

    @get:Rule
    var thrown = ExpectedException.none()

    @Before
    fun setup() {
        mockGson = spyk()
        mockSharedPreferences = mockk()
        mockSharedPreferencesEditor = mockk()
        every { mockSharedPreferences.edit() } returns mockSharedPreferencesEditor
        movieLocalDataSourceImpl =
            MovieLocalDataSourceSharedPrefsImpl(mockSharedPreferences, mockGson)

        tMovieModel1 = MovieModel(
            id = 297761,
            original_title = "Suicide Squad",
            original_language = "en",
            title = "Suicide Squad 2",
            poster_path = "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
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
        tMovieModel2 = tMovieModel1.copy(title = "Suicide Squad 1")

        tHashSet = hashSetOf(
            "{\"poster_path\":\"/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg\",\"adult\":false,\"overview\":\"Movie description\",\"release_date\":\"2016-08-03\",\"genre_ids\":[14,28,80],\"id\":297761,\"original_title\":\"Suicide Squad\",\"original_language\":\"en\",\"title\":\"Suicide Squad 2\",\"backdrop_path\":\"/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg\",\"popularity\":48.261451,\"vote_count\":1466,\"video\":false,\"vote_average\":5.91}",
            "{\"poster_path\":\"/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg\",\"adult\":false,\"overview\":\"Movie description\",\"release_date\":\"2016-08-03\",\"genre_ids\":[14,28,80],\"id\":297761,\"original_title\":\"Suicide Squad\",\"original_language\":\"en\",\"title\":\"Suicide Squad 1\",\"backdrop_path\":\"/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg\",\"popularity\":48.261451,\"vote_count\":1466,\"video\":false,\"vote_average\":5.91}"
        )
    }

    @Test
    fun `getCachedDetailMovie - should return cachedMovie when there is a cached movie`() =
        runBlocking {
            // arrange
            val expected = mockk<MovieModel>()
            mockkObject(InCacheDao)
            coEvery() { InCacheDao.getChachedMovie() } returns expected
            // act
            val result = movieLocalDataSourceImpl.getCachedDetailMovie()
            // assert
            Assert.assertEquals(expected, result)
        }

    @Test
    fun `getCachedDetailMovie - should throw ResourceNotFoundException when movie is null`() {
        // arrange
        mockkObject(InCacheDao)
        coEvery() { InCacheDao.getChachedMovie() } returns null
        thrown.expect(ResourceNotFoundThrowable::class.java)
        // act
        runBlocking {
            movieLocalDataSourceImpl.getCachedDetailMovie()
        }
    }

    @Test
    fun `cacheDetailMovie - should call InCacheDao with correct parameter`() = runBlocking {
        // arrange
        val tMovieMocked = mockk<MovieModel>()
        mockkObject(InCacheDao)
        coEvery() { InCacheDao.cacheMovie(any()) } returns Unit
        // act
        movieLocalDataSourceImpl.cacheDetailMovie(tMovieMocked)
        // expect
        coVerify(exactly = 1) {
            InCacheDao.cacheMovie(tMovieMocked)
        }
    }

    @Test
    fun `getCachedFavoriteMovies - should return cachedFavoriteMovies`() = runBlocking {
        // arrange
        val moviesList = mutableListOf<MovieModel>(tMovieModel1, tMovieModel2)
        every { mockSharedPreferences.getStringSet(any(), any()) } returns tHashSet
        // act
        val result = movieLocalDataSourceImpl.getCachedFavoriteMovies()
        // assert
        Assert.assertEquals(moviesList, result)
        coVerify(exactly = 1) {
            mockSharedPreferences.getStringSet(SHARED_PREFERENCES_FAVORITE_MOVIES_KEY, any())
        }
    }

    @Test
    fun `getCachedFavoriteMovies - should return cachedFavoriteMovies with correct param`() =
        runBlocking {
            // arrange
            val tFilter = "Suicide Squad 2"
            val moviesList = mutableListOf(tMovieModel1, tMovieModel2)
            val moviesListFiltered = mutableListOf(tMovieModel1)

            every { mockSharedPreferences.getStringSet(any(), any()) } returns tHashSet
            // act
            val result = movieLocalDataSourceImpl.getCachedFavoriteMovies(tFilter)
            // assert
            Assert.assertEquals(moviesListFiltered, result)
            coVerify(exactly = 1) {
                mockSharedPreferences.getStringSet(SHARED_PREFERENCES_FAVORITE_MOVIES_KEY, any())
            }
        }

    @Test
    fun `getCachedFavoriteMovies - should return cachedFavoriteMovies ignoreCase with correct param`() =
        runBlocking {
            // arrange
            val tFilter = "suiciDE squad 2"
            val moviesList = mutableListOf(tMovieModel1, tMovieModel2)
            val moviesListFiltered = mutableListOf(tMovieModel1)

            every { mockSharedPreferences.getStringSet(any(), any()) } returns tHashSet
            // act
            val result = movieLocalDataSourceImpl.getCachedFavoriteMovies(tFilter)
            // assert
            Assert.assertEquals(moviesListFiltered, result)
            coVerify(exactly = 1) {
                mockSharedPreferences.getStringSet(SHARED_PREFERENCES_FAVORITE_MOVIES_KEY, any())
            }
        }

    @Test
    fun `cacheFavoriteMovies - should call sharedPreferences with correct parameters`() =
        runBlocking {
            // arrange
            val sharedPreferencesKey =
                "matheusfelipe.desafio.inchurch.sharedpreferences.favorite_movies"


            every { mockGson.toJson(any()) } returns "TMovieToJsonString"

            val moviesList = mutableListOf<MovieModel>(tMovieModel1, tMovieModel2)
            every {
                mockSharedPreferencesEditor.putStringSet(
                    any(),
                    any()
                )
            } returns mockSharedPreferencesEditor
            every { mockSharedPreferencesEditor.apply() } returns Unit
            // act
            movieLocalDataSourceImpl.cacheFavoriteMovies(moviesList)
            // assert
            coVerify(exactly = 1) {
                mockSharedPreferencesEditor.putStringSet(
                    sharedPreferencesKey, tHashSet
                )
            }
        }
}