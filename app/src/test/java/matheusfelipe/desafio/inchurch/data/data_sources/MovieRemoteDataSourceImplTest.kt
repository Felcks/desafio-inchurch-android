package matheusfelipe.desafio.inchurch.data.data_sources

import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import matheusfelipe.desafio.inchurch.core.api.MovieApi
import matheusfelipe.desafio.inchurch.core.exceptions.InvalidApiKeyException
import matheusfelipe.desafio.inchurch.core.exceptions.ResourceNotFoundException
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
        coEvery { mockMovieApi.getAllMovies() } returns Response.success(expect)
        // act
        val result = movieRemoteDataSourceImpl.getAllMovies()
        //assert
        assertEquals(expect, result)
    }

    @Test
    fun `getAllMovies - should throw invalidApiKeyException when api returns 401`() {
        // arrange
        coEvery { mockMovieApi.getAllMovies() } returns Response.error(401, mockk<ResponseBody>())
        thrown.expect(InvalidApiKeyException::class.java)
        // act
        runBlocking {
            movieRemoteDataSourceImpl.getAllMovies()
        }
        // assert
    }

    @Test
    fun `getAllMovies - should throw ResourceNotFoundException when api returns 404`() {
        // arrange
        coEvery { mockMovieApi.getAllMovies() } returns Response.error(404, mockk<ResponseBody>())
        thrown.expect(ResourceNotFoundException::class.java)
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
        coEvery { mockMovieApi.getAllMoviesGenres() } returns Response.success(expect)
        // act
        val result = movieRemoteDataSourceImpl.getAllMoviesGenres()
        //assert
        assertEquals(expect, result)
    }

    @Test
    fun `getAllMoviesGenres - should throw invalidApiKeyException when api returns 401`() {
        // arrange
        coEvery { mockMovieApi.getAllMoviesGenres() } returns Response.error(401, mockk<ResponseBody>())
        thrown.expect(InvalidApiKeyException::class.java)
        // act
        runBlocking {
            movieRemoteDataSourceImpl.getAllMoviesGenres()
        }
        // assert
    }

    @Test
    fun `getAllMoviesGenres - should throw ResourceNotFoundException when api returns 404`() {
        // arrange
        coEvery { mockMovieApi.getAllMoviesGenres() } returns Response.error(404, mockk<ResponseBody>())
        thrown.expect(ResourceNotFoundException::class.java)
        // act
        runBlocking {
            movieRemoteDataSourceImpl.getAllMoviesGenres()
        }
        // assert
    }
}