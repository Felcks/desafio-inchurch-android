package matheusfelipe.desafio.inchurch.data.models

import br.com.mobfiq.search.core.readJsonFile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class PageModelTest {

    private lateinit var tPageModelEmpty: PageModel<MovieModel>
    private lateinit var tPageModel: PageModel<MovieModel>
    private lateinit var tMovieModel: MovieModel

    @Before
    fun setup(){
        tMovieModel = MovieModel(
            id = 297761,
            original_title =  "Suicide Squad",
            original_language = "en",
            title = "Suicide Squad",
            poster_path =  "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
            adult = false,
            overview = "Movie description",
            release_date = "2016-08-03",
            popularity = 48.261451,
            vote_count = 1466,
            video = false,
            vote_average = 5.91,
            genre_ids = listOf(14, 28, 80),
            backdrop_path = "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg"
        )

        tPageModelEmpty = PageModel(
            page = 1,
            results = listOf(),
            total_results = 50,
            total_pages = 1
        );

        tPageModel = PageModel<MovieModel>(
            page = 1,
            results = listOf(
                tMovieModel
            ),
            total_results = 50,
            total_pages = 1
        );
    }

    @Test
    fun `should return a valid model when JSON is ok`(){
        // arrange
        val type = object : TypeToken<PageModel<MovieModel>>() {}.type
        // act
        val result = Gson().fromJson<PageModel<MovieModel>>(readJsonFile("page_movie.json"), type)
        // assert
        Assert.assertEquals(tPageModel, result)
    }

    @Test
    fun `should return a valid model when JSON is ok but empty`(){
        // arrange
        // act
        val result = Gson().fromJson<PageModel<MovieModel>>(readJsonFile("page_empty.json"), PageModel::class.java)
        // assert
        Assert.assertEquals(tPageModelEmpty, result)
    }
}