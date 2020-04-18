package matheusfelipe.desafio.inchurch.presentation.pages.favorite_movies

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_favorite_movies.*
import matheusfelipe.desafio.inchurch.R
import matheusfelipe.desafio.inchurch.core.utils.Response
import matheusfelipe.desafio.inchurch.core.utils.Status
import matheusfelipe.desafio.inchurch.domain.entities.Movie

class FavoriteMoviesActivity: AppCompatActivity() {

    private lateinit var viewModel: FavoriteMoviesViewModel
    private lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_movies)

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
        pg_loading.visibility = View.VISIBLE
    }

    private fun showMovies(data: Any?){

        pg_loading.visibility = View.GONE

        if(data is List<*>) {

            if(data.isEmpty()) {
                showError(Throwable("Lista de filmes favoritos vazia"))
                return
            }

            favoriteMoviesAdapter = FavoriteMoviesAdapter(data.filterIsInstance<Movie>().toMutableList(), ::onItemClick)

            val layoutManager = LinearLayoutManager(this)
            rv_movies.layoutManager = layoutManager
            rv_movies.adapter = favoriteMoviesAdapter
        }
    }

    private fun showError(throwable: Throwable?){
        pg_loading.visibility = View.GONE
        tv_error.text = throwable?.message
        tv_error.visibility = View.VISIBLE
    }

    private fun onItemClick(movie: Movie){
        //viewModel.selectDetailMovie(movie)
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