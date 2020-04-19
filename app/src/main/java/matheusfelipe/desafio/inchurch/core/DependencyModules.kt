package matheusfelipe.desafio.inchurch.core

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import matheusfelipe.desafio.inchurch.core.api.MovieApi
import matheusfelipe.desafio.inchurch.core.api.RestApi
import matheusfelipe.desafio.inchurch.data.data_sources.*
import matheusfelipe.desafio.inchurch.data.data_sources.MovieLocalDataSourceSharedPrefsImpl.Companion.SHARED_PREFERENCES_KEY
import matheusfelipe.desafio.inchurch.data.repositories.MovieRepositoryImpl
import matheusfelipe.desafio.inchurch.domain.repositories.MovieRepository
import matheusfelipe.desafio.inchurch.domain.usecases.*
import matheusfelipe.desafio.inchurch.presentation.pages.favorite_movies.FavoriteMoviesViewModel
import matheusfelipe.desafio.inchurch.presentation.pages.movie_detail.MovieDetailViewModel
import matheusfelipe.desafio.inchurch.presentation.pages.movies.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DependencyModules {

    val appModule = module{

        single<MovieApi> {  RestApi.getRetrofit().create(MovieApi::class.java) }
        single<SharedPreferences> { App.instance.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE) }
        single<Gson> { Gson() }

//        single<MovieLocalDataSource> { MovieLocalDataSourceImpl() }
        single<MovieLocalDataSource> { MovieLocalDataSourceSharedPrefsImpl(get(), get()) }
        single<MovieRemoteDataSource> { MovieRemoteDataSourceImpl(get<MovieApi>()) }

        single<MovieRepository> { MovieRepositoryImpl(get(), get())}

        factory<FavoriteOrDisfavorMovie> { FavoriteOrDisfavorMovie(get()) }
        factory<GetAllMovies> { GetAllMovies(get())}
        factory<GetAllMoviesGenres> { GetAllMoviesGenres(get())}
        factory<GetFavoriteMovies> { GetFavoriteMovies(get())}
        factory<SelectDetailMovie> { SelectDetailMovie(get())}
        factory<ViewDetailMovie> { ViewDetailMovie(get())}

        viewModel<MoviesViewModel> { MoviesViewModel(get(), get(), get(), get()) }
        viewModel<MovieDetailViewModel> { MovieDetailViewModel(get(), get(), get()) }
        viewModel<FavoriteMoviesViewModel> { FavoriteMoviesViewModel(get(), get()) }
    }
}