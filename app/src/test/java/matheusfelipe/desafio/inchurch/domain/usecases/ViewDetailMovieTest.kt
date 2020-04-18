package matheusfelipe.desafio.inchurch.domain.usecases

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import matheusfelipe.desafio.inchurch.domain.entities.Movie
import matheusfelipe.desafio.inchurch.domain.repositories.MovieRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ViewDetailMovieTest {

    private lateinit var mockMovieRepository: MovieRepository
    private lateinit var usecase: ViewDetailMovie
    private lateinit var tMockMovie: Movie

    @Before
    fun setup() {
        mockMovieRepository = mockk()
        usecase = ViewDetailMovie(mockMovieRepository)
        tMockMovie = mockk()
    }

    @Test
    fun `should return all movies from the repository`() = runBlocking {
        // arrange
        coEvery { mockMovieRepository.getCachedDetailMovie() } returns tMockMovie
        // act
        val result = async { usecase() }.await()
        // assert
        Assert.assertEquals(tMockMovie, result)
        coVerify(exactly = 1) {
            mockMovieRepository.getCachedDetailMovie()
        }
    }
}