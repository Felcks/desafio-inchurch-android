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

class SelectDetailMovieTest {

    private lateinit var mockMovieRepository: MovieRepository
    private lateinit var usecase: SelectDetailMovie
    private lateinit var tMockMovie: Movie

    @Before
    fun setup() {
        mockMovieRepository = mockk()
        usecase = SelectDetailMovie(mockMovieRepository)
        tMockMovie = mockk()
    }

    @Test
    fun `should return call cacheDetailMovie from the repository`() = runBlocking {
        // arrange
        coEvery { mockMovieRepository.cacheDetailMovie(any()) } returns Unit
        // act
        val result = async { usecase(tMockMovie) }.await()
        // assert
        coVerify(exactly = 1) {
            mockMovieRepository.cacheDetailMovie(tMockMovie)
        }
    }
}