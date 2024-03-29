package matheusfelipe.desafio.inchurch.presentation.pages.movie_detail

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
import matheusfelipe.desafio.inchurch.domain.usecases.*

class MovieDetailViewModel(
    val favoriteOrDisfavorMovieUseCase: FavoriteOrDisfavorMovie,
    val viewDetailMovie: ViewDetailMovie,
    val getAllMoviesGenres: GetAllMoviesGenres
): ViewModel() {


    private var genres: MutableLiveData<Response> = MutableLiveData()
    fun genres() = genres

    private var detailMovie: MutableLiveData<Response> = MutableLiveData()
    fun detailMovie() = detailMovie

    private var favoriteOrDisfavorMovieResponse: MutableLiveData<Response> = MutableLiveData()
    fun favoriteOrDisfavorMovieResponse() = favoriteOrDisfavorMovieResponse

    init {
        fetchDetailMovie()
        fetchAllGenres()
    }

    private fun fetchAllGenres() {

        genres.value = Response.loading()
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val result = getAllMoviesGenres()
                genres.postValue(Response.success(result))
            } catch (t: Throwable) {
                genres.postValue(Response.error(t))
            }
        }
    }

    private fun fetchDetailMovie(){

        detailMovie.value = Response.loading()
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val result = viewDetailMovie()
                detailMovie.postValue(Response.success(result))
            } catch (t: Throwable) {
                detailMovie.postValue(Response.error(t))
            }
        }
    }

    fun favoriteOrDisfavorMovie() {

        if(detailMovie.value != null && detailMovie.value?.data is Movie){

            val movie = detailMovie.value?.data as Movie
            favoriteOrDisfavorMovieResponse.postValue(Response.loading())
            CoroutineScope(Dispatchers.IO).launch {
                favoriteOrDisfavorMovieUseCase(movie)
                favoriteOrDisfavorMovieResponse.postValue(Response.success(Unit))
            }
        }
    }
}