package matheusfelipe.desafio.inchurch.presentation.pages.favorite_movies

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import matheusfelipe.desafio.inchurch.core.utils.Response
import matheusfelipe.desafio.inchurch.core.utils.Status

class FavoriteMoviesActivity: AppCompatActivity() {

    private lateinit var viewModel: FavoriteMoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this)[FavoriteMoviesViewModel::class.java]
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
        //pg_loading.visibility = View.VISIBLE
    }

    private fun showMovies(data: Any?){

//        pg_loading.visibility = View.GONE
//
        if(data is List<*>) {
            Log.i("script2", data.size.toString())
        }
//
//            movieAdapter = MovieAdapter(data.filterIsInstance<Movie>().toMutableList(), ::onItemClick, ::onFavoriteClick)
//
//            val layoutManager = GridLayoutManager(this, 2)
//            rv_movies.layoutManager = layoutManager
//            rv_movies.adapter = movieAdapter
//        }
    }

    private fun showError(throwable: Throwable?){
//        pg_loading.visibility = View.GONE
//        tv_error.text = throwable?.message
//        tv_error.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        //menuInflater.inflate(R.menu.menu_pedido_visualizacao, menu)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}