package matheusfelipe.desafio.inchurch.presentation.pages.movies

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import matheusfelipe.desafio.inchurch.core.utils.Response
import matheusfelipe.desafio.inchurch.domain.entities.Movie
import matheusfelipe.desafio.inchurch.domain.usecases.FavoriteOrDisfavorMovie
import matheusfelipe.desafio.inchurch.domain.usecases.GetAllMovies
import matheusfelipe.desafio.inchurch.domain.usecases.GetFavoriteMovies
import matheusfelipe.desafio.inchurch.domain.usecases.SelectDetailMovie

class MoviesViewModel(
    val favoriteOrDisfavorMovieUseCase: FavoriteOrDisfavorMovie,
    val selectDetailMovieUseCase: SelectDetailMovie,
    val getAllMovies: GetAllMovies,
    val getFavoriteMoviesUseCase: GetFavoriteMovies
) : ViewModel() {

    private var movies: MutableLiveData<Response> = MutableLiveData()
    fun movies() = movies

    private var moviesForUpdate: MutableLiveData<Response> = MutableLiveData()
    fun moviesForUpdate() = moviesForUpdate

    private var selectDetailMovieResponse: MutableLiveData<Response> = MutableLiveData()
    fun selectDetailMovieResponse() = selectDetailMovieResponse

    private var favoriteOrDisfavorMovieResponse: MutableLiveData<Response> = MutableLiveData()
    fun favoriteOrDisfavorMovieResponse() = favoriteOrDisfavorMovieResponse

    private var currentPage = 1

    init {
        fetchAllMovies()
    }

    fun fetchAllMovies() {

        movies.postValue(Response.loading())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = getAllMovies(currentPage)
                movies.postValue(Response.success(result))
            } catch (t: Throwable) {
                movies.postValue(Response.error(t))
            }
        }
    }

    fun loadMoreMovies() {
        currentPage += 1
        fetchAllMovies()
    }

    fun updateMovies(movies: MutableList<Movie>){

        moviesForUpdate.postValue(Response.loading())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val favoriteMovies = getFavoriteMoviesUseCase()
                val favoriteMoviesIDs = favoriteMovies.map { it.id }
                movies.forEach {
                    it.isFavorite = favoriteMoviesIDs.contains(it.id)
                }
                moviesForUpdate.postValue(Response.success(movies))
            } catch (t: Throwable) {
                moviesForUpdate.postValue(Response.error(t))
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

    fun favoriteOrDisfavorMovie(movie: Movie, pos: Int) {

        favoriteOrDisfavorMovieResponse.postValue(Response.loading())
        CoroutineScope(Dispatchers.IO).launch {
            favoriteOrDisfavorMovieUseCase(movie)
            favoriteOrDisfavorMovieResponse.postValue(Response.success(pos))
        }
    }

    override fun onCleared() {
        super.onCleared()
        selectDetailMovieResponse.postValue(Response.empty())
    }
}