package matheusfelipe.desafio.inchurch.domain.usecases

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import matheusfelipe.desafio.inchurch.domain.entities.Movie
import matheusfelipe.desafio.inchurch.domain.repositories.MovieRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAllMoviesTest {

    private lateinit var mockMovieRepository: MovieRepository
    private lateinit var usecase: GetAllMovies
    private lateinit var tMockMovieList: List<Movie>

    @Before
    fun setup() {
        mockMovieRepository = mockk()
        usecase = GetAllMovies(mockMovieRepository)
        tMockMovieList = listOf(mockk<Movie>(), mockk<Movie>())
    }

    @Test
    fun `should return all movies from the repository`() = runBlocking {
        // arrange
        coEvery { mockMovieRepository.getAllMovies() } returns tMockMovieList
        // act
        val result = async { usecase() }.await()
        // assert
        assertEquals(tMockMovieList, result)
        coVerify(exactly = 1) {
            mockMovieRepository.getAllMovies()
        }
    }
}