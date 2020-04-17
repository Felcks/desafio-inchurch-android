package matheusfelipe.desafio.inchurch.data.models

import br.com.mobfiq.search.core.readJsonFile
import com.google.gson.Gson
import matheusfelipe.desafio.inchurch.domain.entities.Genre
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GenreModelTest {

    private lateinit var tGenreModel: GenreModel
    private lateinit var tGenre: Genre

    @Before
    fun setup(){
        tGenre = Genre(28, "Action")
        tGenreModel = GenreModel(28, "Action")
    }

    @Test
    fun `should return a valid entity when fields are ok`(){
        // arrange
        // act
        val result = tGenreModel.toEntity()
        // assert
        Assert.assertEquals(tGenre, result)
    }

    @Test
    fun `should return a valid model when JSON is ok`(){
        // arrange
        // act
        val result = Gson().fromJson<GenreModel>(readJsonFile("genre.json"), GenreModel::class.java)
        // assert
        Assert.assertEquals(tGenreModel, result)
    }

}