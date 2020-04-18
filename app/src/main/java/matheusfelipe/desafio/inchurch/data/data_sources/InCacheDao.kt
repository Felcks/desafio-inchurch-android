package matheusfelipe.desafio.inchurch.data.data_sources

import matheusfelipe.desafio.inchurch.data.models.MovieModel

object InCacheDao {

    private var movieDetail: MovieModel? = null

    suspend fun getChachedMovie(): MovieModel? = this.movieDetail

    suspend fun cacheMovie(movie: MovieModel){
        this.movieDetail = movie
    }
}