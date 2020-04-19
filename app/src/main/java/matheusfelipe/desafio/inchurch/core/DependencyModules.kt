package matheusfelipe.desafio.inchurch.core

import matheusfelipe.desafio.inchurch.core.api.MovieApi
import matheusfelipe.desafio.inchurch.core.api.RestApi
import matheusfelipe.desafio.inchurch.presentation.pages.movies.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DependencyModules {

    val appModule = module{

        single<MovieApi> {  RestApi.getRetrofit().create(MovieApi::class.java) }

        viewModel<MoviesViewModel> { MoviesViewModel()}
    }
}