package matheusfelipe.desafio.inchurch.data.data_sources

import android.content.SharedPreferences
import com.google.gson.Gson
import matheusfelipe.desafio.inchurch.core.App
import matheusfelipe.desafio.inchurch.core.exceptions.ResourceNotFoundThrowable
import matheusfelipe.desafio.inchurch.data.models.MovieModel

class MovieLocalDataSourceSharedPrefsImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : MovieLocalDataSource {


    override suspend fun cacheDetailMovie(movie: MovieModel) {
        InCacheDao.cacheMovie(movie)
    }

    override suspend fun getCachedDetailMovie(): MovieModel {
        return InCacheDao.getChachedMovie() ?: throw ResourceNotFoundThrowable()
    }

    override suspend fun cacheFavoriteMovies(movies: MutableList<MovieModel>) {
        val editor = sharedPreferences.edit()

        val hashSet = movies.map { gson.toJson(it) }.toHashSet()
        editor.putStringSet(SHARED_PREFERENCES_FAVORITE_MOVIES_KEY, hashSet)
        editor.apply()
    }

    override suspend fun getCachedFavoriteMovies(filter: String): MutableList<MovieModel> {
        var hashSet = hashSetOf<String>()
        hashSet = sharedPreferences.getStringSet(
            SHARED_PREFERENCES_FAVORITE_MOVIES_KEY,
            hashSet
        ) as HashSet<String>

        return hashSet.map {
            gson.fromJson(it, MovieModel::class.java)
        }.filter { it.title.contains(filter, ignoreCase = true) }.toMutableList()
    }

    companion object {
        const val SHARED_PREFERENCES_FAVORITE_MOVIES_KEY =
            "matheusfelipe.desafio.inchurch.sharedpreferences.favorite_movies"
        const val SHARED_PREFERENCES_KEY = "matheusfelipe.desafio.inchurch.sharedpreferences"
    }
}