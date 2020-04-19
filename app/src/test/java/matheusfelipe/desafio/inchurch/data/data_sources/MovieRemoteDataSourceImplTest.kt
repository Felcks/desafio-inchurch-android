package matheusfelipe.desafio.inchurch.data.data_sources

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import matheusfelipe.desafio.inchurch.core.api.MovieApi
import matheusfelipe.desafio.inchurch.core.exceptions.InvalidApiKeyThrowable
import matheusfelipe.desafio.inchurch.core.exceptions.ResourceNotFoundThrowable
import matheusfelipe.desafio.inchurch.data.models.GenreResultModel
import matheusfelipe.desafio.inchurch.data.models.MovieModel
import matheusfelipe.desafio.inchurch.data.models.PageModel
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import retrofit2.Response

class MovieRemoteDataSourceImplTest {

    private lateinit var mockMovieApi: MovieApi
    private lateinit var movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl

    @get:Rule
    var thrown = ExpectedException.none()

    @Before
    fun setup(){
        mockMovieApi = mockk()
        movieRemoteDataSourceImpl = MovieRemoteDataSourceImpl(mockMovieApi)
    }

    @Test
    fun `getAllMovies - should return success when api returns success`() = runBlocking {
        // arrange
        val expect = mockk<PageModel<MovieModel>>()
        coEvery { mockMovieApi.getAllMovies(any()) } returns Response.success(expect)
        // act
        val result = movieRemoteDataSourceImpl.getAllMovies()
        //assert
        assertEquals(expect, result)
    }

    @Test
    fun `getAllMovies - should call api with correct params and returns success`() = runBlocking {
        // arrange
        val tPage = 2
        val expect = mockk<PageModel<MovieModel>>()
        coEvery { mockMovieApi.getAllMovies(any(), any()) } returns Response.success(expect)
        // act
        val result = movieRemoteDataSourceImpl.getAllMovies(tPage)
        //assert
        assertEquals(expect, result)
        coVerify(exactly = 1) {
            mockMovieApi.getAllMovies(any(), tPage)
        }
    }

    @Test
    fun `getAllMovies - should throw invalidApiKeyException when api returns 401`() {
        // arrange
        coEvery { mockMovieApi.getAllMovies(any()) } returns Response.error(401, mockk<ResponseBody>())
        thrown.expect(InvalidApiKeyThrowable::class.java)
        // act
        runBlocking {
            movieRemoteDataSourceImpl.getAllMovies()
        }
        // assert
    }

    @Test
    fun `getAllMovies - should throw ResourceNotFoundException when api returns 404`() {
        // arrange
        coEvery { mockMovieApi.getAllMovies(any()) } returns Response.error(404, mockk<ResponseBody>())
        thrown.expect(ResourceNotFoundThrowable::class.java)
        // act
        runBlocking {
            movieRemoteDataSourceImpl.getAllMovies()
        }
        // assert
    }

    @Test
    fun `getAllMoviesGenres - should return success when api returns success`() = runBlocking {
        // arrange
        val expect = mockk<GenreResultModel>()
        coEvery { mockMovieApi.getAllMoviesGenres(any()) } returns Response.success(expect)
        // act
        val result = movieRemoteDataSourceImpl.getAllMoviesGenres()
        //assert
        assertEquals(expect, result)
    }

    @Test
    fun `getAllMoviesGenres - should throw invalidApiKeyException when api returns 401`() {
        // arrange
        coEvery { mockMovieApi.getAllMoviesGenres(any()) } returns Response.error(401, mockk<ResponseBody>())
        thrown.expect(InvalidApiKeyThrowable::class.java)
        // act
        runBlocking {
            movieRemoteDataSourceImpl.getAllMoviesGenres()
        }
        // assert
    }

    @Test
    fun `getAllMoviesGenres - should throw ResourceNotFoundException when api returns 404`() {
        // arrange
        coEvery { mockMovieApi.getAllMoviesGenres(any()) } returns Response.error(404, mockk<ResponseBody>())
        thrown.expect(ResourceNotFoundThrowable::class.java)
        // act
        runBlocking {
            movieRemoteDataSourceImpl.getAllMoviesGenres()
        }
        // assert
    }
}