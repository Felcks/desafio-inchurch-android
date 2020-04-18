package matheusfelipe.desafio.inchurch.data.data_sources

import io.mockk.*
import kotlinx.coroutines.runBlocking
import matheusfelipe.desafio.inchurch.core.exceptions.ResourceNotFoundThrowable
import matheusfelipe.desafio.inchurch.data.models.MovieModel
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import retrofit2.Response

class MovieLocalDataSourceImplTest {

    private lateinit var movieLocalDataSourceImpl: MovieLocalDataSourceImpl

    @get:Rule
    var thrown = ExpectedException.none()

    @Before
    fun setup(){
        movieLocalDataSourceImpl = MovieLocalDataSourceImpl()
    }

    @Test
    fun `getCachedDetailMovie - should return cachedMovie when there is a cached movie`() = runBlocking {
        // arrange
        val expected =  mockk<MovieModel>()
        mockkObject(InCacheDao)
        coEvery() { InCacheDao.getChachedMovie() } returns expected
        // act
        val result = movieLocalDataSourceImpl.getCachedDetailMovie()
        // assert
        assertEquals(expected, result)
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
        coVerify(exactly = 1){
            InCacheDao.cacheMovie(tMovieMocked)
        }
    }
}