package matheusfelipe.desafio.inchurch.data.models

import br.com.mobfiq.search.core.readJsonFile
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GenreResultModelTest {

    private lateinit var tGenreModel: GenreModel
    private lateinit var tGenreResultModel: GenreResultModel
    private lateinit var tGenreResultModelEmpty: GenreResultModel


    @Before
    fun setup(){
        tGenreModel = GenreModel(28, "Action")
        tGenreResultModel = GenreResultModel(listOf(GenreModel(28, "Action")))
        tGenreResultModelEmpty = GenreResultModel(listOf())
    }

    @Test
    fun `should return a valid model when JSON is ok`(){
        // arrange
        // act
        val result = Gson().fromJson<GenreResultModel>(readJsonFile("genre_result.json"), GenreResultModel::class.java)
        // assert
        assertEquals(tGenreResultModel, result)
    }

    @Test
    fun `should return a valid model when JSON is ok but empty`(){
        // arrange
        // act
        val result = Gson().fromJson<GenreResultModel>(readJsonFile("genre_result_empty.json"), GenreResultModel::class.java)
        // assert
        assertEquals(tGenreResultModelEmpty, result)
    }
}