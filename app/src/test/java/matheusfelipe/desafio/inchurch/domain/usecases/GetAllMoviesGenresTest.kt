package matheusfelipe.desafio.inchurch.domain.usecases

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import matheusfelipe.desafio.inchurch.domain.entities.Genre
import matheusfelipe.desafio.inchurch.domain.repositories.MovieRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAllMoviesGenresTest {

    private lateinit var mockMovieRepository: MovieRepository
    private lateinit var usecase: GetAllMoviesGenres
    private lateinit var tMockGenreList: List<Genre>

    @Before
    fun setup() {
        mockMovieRepository = mockk()
        usecase = GetAllMoviesGenres(mockMovieRepository)
        tMockGenreList = listOf(mockk<Genre>(), mockk<Genre>())
    }

    @Test
    fun `should return all movies from the repository`() = runBlocking {
        // arrange
        coEvery { mockMovieRepository.getAllMoviesGenres() } returns tMockGenreList
        // act
        val result = async { usecase() }.await()
        // assert
        assertEquals(tMockGenreList, result)
        coVerify(exactly = 1) {
            mockMovieRepository.getAllMoviesGenres()
        }
    }
}