package matheusfelipe.desafio.inchurch.presentation.pages.movies

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import matheusfelipe.desafio.inchurch.core.api.MovieApi
import matheusfelipe.desafio.inchurch.core.api.RestApi
import matheusfelipe.desafio.inchurch.core.utils.Response
import matheusfelipe.desafio.inchurch.data.data_sources.MovieRemoteDataSource
import matheusfelipe.desafio.inchurch.data.data_sources.MovieRemoteDataSourceImpl
import matheusfelipe.desafio.inchurch.data.repositories.MovieRepositoryImpl
import matheusfelipe.desafio.inchurch.domain.repositories.MovieRepository
import matheusfelipe.desafio.inchurch.domain.usecases.GetAllMovies

class MoviesViewModel: ViewModel(){

    private lateinit var getAllMovies: GetAllMovies
    private lateinit var movieRepository: MovieRepository
    private lateinit var movieDataSource: MovieRemoteDataSource

    private var movies: MutableLiveData<Response> = MutableLiveData()
    fun movies() = movies

    init {
        val api = RestApi.getRetrofit().create(MovieApi::class.java)
        movieDataSource = MovieRemoteDataSourceImpl(api)
        movieRepository = MovieRepositoryImpl(movieDataSource)
        getAllMovies = GetAllMovies(movieRepository)

        fetchAllMovies()
    }

    private fun fetchAllMovies() {

        movies.value = Response.loading()
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val result = getAllMovies()
                movies.postValue(Response.success(result))
            } catch (t: Throwable) {
                movies.postValue(Response.error(t))
            }
        }
    }
}