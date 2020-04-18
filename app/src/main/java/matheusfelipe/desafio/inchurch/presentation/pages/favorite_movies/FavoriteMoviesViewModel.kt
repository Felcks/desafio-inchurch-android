package matheusfelipe.desafio.inchurch.presentation.pages.favorite_movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
import matheusfelipe.desafio.inchurch.domain.usecases.GetFavoriteMovies
import matheusfelipe.desafio.inchurch.domain.usecases.SelectDetailMovie

class FavoriteMoviesViewModel : ViewModel() {

    private lateinit var movieRemoteDataSource: MovieRemoteDataSource
    private lateinit var movieLocalDataSource: MovieLocalDataSource
    private lateinit var movieRepository: MovieRepository
    private lateinit var getFavoriteMoviesUseCase: GetFavoriteMovies
    private lateinit var selectDetailMovieUseCase: SelectDetailMovie

    private var movies: MutableLiveData<Response> = MutableLiveData()
    fun movies() = movies

    private var selectDetailMovieResponse: MutableLiveData<Response> = MutableLiveData()
    fun selectDetailMovieResponse() = selectDetailMovieResponse

    init {
        val api = RestApi.getRetrofit().create(MovieApi::class.java)
        movieRemoteDataSource = MovieRemoteDataSourceImpl(api)
        movieLocalDataSource = MovieLocalDataSourceImpl()
        movieRepository = MovieRepositoryImpl(movieRemoteDataSource, movieLocalDataSource)
        getFavoriteMoviesUseCase = GetFavoriteMovies(movieRepository)
        selectDetailMovieUseCase = SelectDetailMovie(movieRepository)

        fetchFavoriteMovies()
    }

    fun fetchFavoriteMovies() {

        movies.postValue(Response.loading())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = getFavoriteMoviesUseCase()
                movies.postValue(Response.success(result))
            } catch (t: Throwable) {
                movies.postValue(Response.error(t))
            }
        }
    }

    fun selectDetailMovie(movie: Movie) {

        selectDetailMovieResponse.postValue(Response.loading())
        CoroutineScope(Dispatchers.IO).launch {
            selectDetailMovieUseCase(movie)
            selectDetailMovieResponse.postValue(Response.success(true))
        }
    }

}