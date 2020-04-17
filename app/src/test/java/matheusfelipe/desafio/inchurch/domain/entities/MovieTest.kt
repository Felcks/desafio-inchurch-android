package matheusfelipe.desafio.inchurch.domain.entities

import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MovieTest {

    private lateinit var tMovieAllGenres: Movie
    private lateinit var tMovieOnlyAdventure: Movie
    private lateinit var tMovieActionAndComedy: Movie
    private lateinit var tMovieNoGenre: Movie

    private lateinit var tGenreList: List<Genre>
    private lateinit var tGenreAction: Genre
    private lateinit var tGenreAdventure: Genre
    private lateinit var tGenreDrama: Genre
    private lateinit var tGenreComedy: Genre

    @Before
    fun setup() {

        tGenreAction = Genre(1, "Ação");
        tGenreAdventure = Genre(2, "Aventura");
        tGenreDrama = Genre(3, "Drama");
        tGenreComedy = Genre(4, "Comédia");

        tGenreList = listOf(
            tGenreAction,
            tGenreAdventure,
            tGenreDrama,
            tGenreComedy
        )

        tMovieAllGenres = spyk(mockk<Movie>()).apply { this.genreIds = listOf(1, 2, 3, 4) }
        tMovieOnlyAdventure = spyk(mockk<Movie>()).apply { this.genreIds = listOf(2) }
        tMovieActionAndComedy = spyk(mockk<Movie>()).apply { this.genreIds = listOf(1, 4) }
        tMovieNoGenre = spyk(mockk<Movie>()).apply { this.genreIds = listOf<Int>() }
    }

    @Test
    fun `should return all genres when movie has all genres ids`() {
        // arrange
        val expected = tGenreList;
        // act
        val result = tMovieAllGenres.getGenres(tGenreList);
        // assert
        assertEquals(expected, result)
    }

    @Test
    fun `should return adveture genre when movie has adventure genre id`() {
        // arrange
        val expected = listOf(tGenreAdventure);
        // act
        val result = tMovieOnlyAdventure.getGenres(tGenreList);
        // assert
        assertEquals(expected, result)
    }

    @Test
    fun `should return action and comedy genre when movie has action and comedy id`() {
        // arrange
        val expected = listOf(tGenreAction, tGenreComedy);
        // act
        val result = tMovieActionAndComedy.getGenres(tGenreList);
        // assert
        assertEquals(expected, result)
    }

    @Test
    fun `should return no genre when movie has no genre id`() {
        // arrange
        val expected = listOf<Genre>();
        // act
        val result = tMovieNoGenre.getGenres(tGenreList);
        // assert
        assertEquals(expected, result)
    }
}