package matheusfelipe.desafio.inchurch.domain.usecases

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import matheusfelipe.desafio.inchurch.domain.entities.Movie
import matheusfelipe.desafio.inchurch.domain.repositories.MovieRepository
import org.junit.Before
import org.junit.Test

class FavoriteOrDesfavorMovieTest {

    private lateinit var mockMovieRepository: MovieRepository
    private lateinit var usecase: FavoriteOrDisfavorMovie
    private lateinit var tMockMovie: Movie

    @Before
    fun setup() {
        mockMovieRepository = mockk()
        usecase = FavoriteOrDisfavorMovie(mockMovieRepository)
        tMockMovie = mockk()
    }

    @Test
    fun `should call favorite movie from the repository`() = runBlocking {
        // arrange
        every { tMockMovie.isFavorite } returns true
        coEvery { mockMovieRepository.favoriteOrDisfavorMovie(any()) } returns tMockMovie
        // act
        val result = usecase(tMockMovie)
        // assert
        coVerify(exactly = 1) {
            mockMovieRepository.favoriteOrDisfavorMovie(tMockMovie)
        }
    }
}