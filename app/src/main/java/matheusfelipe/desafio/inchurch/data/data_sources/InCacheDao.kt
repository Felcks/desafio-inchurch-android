package matheusfelipe.desafio.inchurch.data.data_sources

import android.util.Log
import matheusfelipe.desafio.inchurch.data.models.MovieModel

object InCacheDao {

    private var movieDetail: MovieModel? = null

    fun getChachedMovie(): MovieModel? = this.movieDetail

    fun cacheMovie(movie: MovieModel){
        this.movieDetail = movie
    }
}