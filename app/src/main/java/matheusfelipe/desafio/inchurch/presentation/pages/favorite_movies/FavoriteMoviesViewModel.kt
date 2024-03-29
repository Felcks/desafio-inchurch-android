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

class FavoriteMoviesViewModel(val getFavoriteMoviesUseCase: GetFavoriteMovies,
                              val selectDetailMovieUseCase: SelectDetailMovie) : ViewModel() {

    private var movies: MutableLiveData<Response> = MutableLiveData()
    fun movies() = movies

    private var selectDetailMovieResponse: MutableLiveData<Response> = MutableLiveData()
    fun selectDetailMovieResponse() = selectDetailMovieResponse

    private var filter = ""

    fun fetchFavoriteMovies() {

        movies.postValue(Response.loading())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = getFavoriteMoviesUseCase(filter)
                movies.postValue(Response.success(result))
            } catch (t: Throwable) {
                movies.postValue(Response.error(t))
            }
        }
    }

    fun searchMovies(filter: String){
        this.filter = filter
        fetchFavoriteMovies()
    }

    fun selectDetailMovie(movie: Movie) {

        selectDetailMovieResponse.postValue(Response.loading())
        CoroutineScope(Dispatchers.IO).launch {
            selectDetailMovieUseCase(movie)
            selectDetailMovieResponse.postValue(Response.success(true))
        }
    }

}