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

class GetFavoriteMoviesTest {

    private lateinit var mockMovieRepository: MovieRepository
    private lateinit var usecase: GetFavoriteMovies
    private lateinit var tMockMovieList: List<Movie>

    @Before
    fun setup() {
        mockMovieRepository = mockk()
        usecase = GetFavoriteMovies(mockMovieRepository)
        tMockMovieList = listOf(mockk<Movie>(), mockk<Movie>())
    }

    @Test
    fun `should return get favorite movies from the repository`() = runBlocking {
        // arrange
        coEvery { mockMovieRepository.getFavoriteMovies() } returns tMockMovieList
        // act
        val result = async { usecase() }.await()
        // assert
        Assert.assertEquals(tMockMovieList, result)
        coVerify(exactly = 1) {
            mockMovieRepository.getFavoriteMovies()
        }
    }

    @Test
    fun `should return get favorite movies from the repository and call with correct params`() = runBlocking {
        // arrange
        val tFilter = "filter"
        coEvery { mockMovieRepository.getFavoriteMovies(any()) } returns tMockMovieList
        // act
        val result = async { usecase(tFilter) }.await()
        // assert
        Assert.assertEquals(tMockMovieList, result)
        coVerify(exactly = 1) {
            mockMovieRepository.getFavoriteMovies(tFilter)
        }
    }
}