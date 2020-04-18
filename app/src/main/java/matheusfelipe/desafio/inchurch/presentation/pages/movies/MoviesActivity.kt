package matheusfelipe.desafio.inchurch.presentation.pages.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_movies.*
import matheusfelipe.desafio.inchurch.R
import matheusfelipe.desafio.inchurch.core.utils.Response
import matheusfelipe.desafio.inchurch.core.utils.Status
import matheusfelipe.desafio.inchurch.domain.entities.Movie

class MoviesActivity : AppCompatActivity() {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        viewModel = ViewModelProviders.of(this)[MoviesViewModel::class.java]
        viewModel.movies().observe(this, Observer { response -> processResponse(response) })
    }

    private fun processResponse(response: Response){
        when(response.status){
            Status.LOADING -> showLoading()
            Status.SUCCESS -> showMovies(response.data)
            Status.EMPTY_RESPONSE -> {}
            Status.ERROR -> showError(response.error)
        }
    }

    private fun showLoading(){
        pg_loading.visibility = View.VISIBLE
    }

    private fun showMovies(data: Any?){
        pg_loading.visibility = View.GONE

        if(data is List<*>) {

            movieAdapter = MovieAdapter(data.filterIsInstance<Movie>().toMutableList())

            val layoutManager = GridLayoutManager(this, 2)
            rv_movies.layoutManager = layoutManager
            rv_movies.adapter = movieAdapter
        }

    }

    private fun showError(throwable: Throwable?){
        pg_loading.visibility = View.GONE
        //tv_hello_world.text = throwable?.message ?: "Unexpected error"
    }

}
