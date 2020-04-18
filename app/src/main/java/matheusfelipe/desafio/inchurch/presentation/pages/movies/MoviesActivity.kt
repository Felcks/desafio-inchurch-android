package matheusfelipe.desafio.inchurch.presentation.pages.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_movies.*
import matheusfelipe.desafio.inchurch.R
import matheusfelipe.desafio.inchurch.core.utils.Response
import matheusfelipe.desafio.inchurch.core.utils.Status

class MoviesActivity : AppCompatActivity() {

    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        viewModel = ViewModelProviders.of(this)[MoviesViewModel::class.java]
        viewModel.movies().observe(this, Observer { response -> processResponse(response) })
    }

    private fun processResponse(response: Response){
        when(response.status){
            Status.LOADING -> showLoading()
            Status.SUCCESS -> showMovies()
            Status.EMPTY_RESPONSE -> {}
            Status.ERROR -> showError(response.error)
        }
    }

    private fun showLoading(){
        tv_hello_world.text = "Loading"
    }

    private fun showMovies(){
        tv_hello_world.text = "Carrregamento concluido"
    }

    private fun showError(throwable: Throwable?){
        tv_hello_world.text = throwable?.message ?: "Unexpected error"
    }

}
