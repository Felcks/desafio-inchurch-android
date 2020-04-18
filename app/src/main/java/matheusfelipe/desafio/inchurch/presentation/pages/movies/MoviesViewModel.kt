package matheusfelipe.desafio.inchurch.presentation.pages.movies

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import matheusfelipe.desafio.inchurch.core.api.MovieApi
import matheusfelipe.desafio.inchurch.core.api.RestApi
import matheusfelipe.desafio.inchurch.core.utils.Response
import matheusfelipe.desafio.inchurch.data.data_sources.MovieLocalDataSource
import matheusfelipe.desafio.inchurch.data.data_sources.MovieLocalDataSourceImpl
import matheusfelipe.desafio.inchurch.data.data_sources.MovieRemoteDataSource
import matheusfelipe.desafio.inchurch.data.data_sources.MovieRemoteDataSourceImpl
import matheusfelipe.desafio.inchurch.data.repositories.MovieRepositoryImpl
import matheusfelipe.desafio.inchurch.domain.entities.Movie
import matheusfelipe.desafio.inchurch.domain.repositories.MovieRepository
import matheusfelipe.desafio.inchurch.domain.usecases.GetAllMovies
import matheusfelipe.desafio.inchurch.domain.usecases.SelectDetailMovie

class MoviesViewModel: ViewModel(){


    private lateinit var selectDetailMovieUseCase: SelectDetailMovie
    private lateinit var getAllMovies: GetAllMovies
    private lateinit var movieRepository: MovieRepository
    private lateinit var movieRemoteDataSource: MovieRemoteDataSource
    private lateinit var movieLocalDataSource: MovieLocalDataSource

    private var movies: MutableLiveData<Response> = MutableLiveData()
    fun movies() = movies

    private var selectDetailMovieResponse: MutableLiveData<Response> = MutableLiveData()
    fun selectDetailMovieResponse() = selectDetailMovieResponse

    init {
        val api = RestApi.getRetrofit().create(MovieApi::class.java)
        movieRemoteDataSource = MovieRemoteDataSourceImpl(api)
        movieLocalDataSource = MovieLocalDataSourceImpl()
        movieRepository = MovieRepositoryImpl(movieRemoteDataSource, movieLocalDataSource)
        getAllMovies = GetAllMovies(movieRepository)
        selectDetailMovieUseCase = SelectDetailMovie(movieRepository)

        fetchAllMovies()
    }

    private fun fetchAllMovies() {

        movies.postValue(Response.loading())
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val result = getAllMovies()
                movies.postValue(Response.success(result))
            } catch (t: Throwable) {
                movies.postValue(Response.error(t))
            }
        }
    }

    fun selectDetailMovie(movie: Movie){

        selectDetailMovieResponse.postValue(Response.loading())
        CoroutineScope(Dispatchers.IO).launch {
            async { selectDetailMovieUseCase(movie) }.await()
            Log.i("script2", "tudo certo no usecase")
            selectDetailMovieResponse.postValue(Response.success(true))
        }
    }
}