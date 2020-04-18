package matheusfelipe.desafio.inchurch.presentation.pages.favorite_movies

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
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
import matheusfelipe.desafio.inchurch.presentation.pages.movie_detail.MovieDetailActivity

class FavoriteMoviesActivity : AppCompatActivity() {

    private lateinit var viewModel: FavoriteMoviesViewModel
    private var favoriteMoviesAdapter: FavoriteMoviesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_movies)

        viewModel = ViewModelProviders.of(this)[FavoriteMoviesViewModel::class.java]
        viewModel.movies().observe(this, Observer { response -> processResponse(response) })
        viewModel.selectDetailMovieResponse()
            .observe(this, Observer { response -> processOnSelectMovieResponse(response) })
    }

    private fun processOnSelectMovieResponse(response: Response) {
        if (response.status == Status.SUCCESS) {
            if (response.data != null) {
                val intent = Intent(this, MovieDetailActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun processResponse(response: Response) {
        when (response.status) {
            Status.LOADING -> showLoading()
            Status.SUCCESS -> showMovies(response.data)
            Status.EMPTY_RESPONSE -> {
            }
            Status.ERROR -> showError(response.error)
        }
    }

    private fun showLoading() {
        if(favoriteMoviesAdapter == null)
            pg_loading.visibility = View.VISIBLE
    }

    private fun showMovies(data: Any?) {

        pg_loading.visibility = View.GONE

        if (data is List<*>) {

            if (data.isEmpty()) {
                showError(Throwable("Você não selecionou nenhum filme favorito"))
                favoriteMoviesAdapter?.updateAllItems(mutableListOf())
                return
            }

            if(favoriteMoviesAdapter == null) {
                favoriteMoviesAdapter =
                    FavoriteMoviesAdapter(
                        data.filterIsInstance<Movie>().toMutableList(),
                        ::onItemClick
                    )

                val layoutManager = LinearLayoutManager(this)
                rv_movies.layoutManager = layoutManager
                rv_movies.adapter = favoriteMoviesAdapter
            }
            else{
                favoriteMoviesAdapter?.updateAllItems(data.filterIsInstance<Movie>().toMutableList())
            }
        }
    }

    private fun showError(throwable: Throwable?) {
        pg_loading.visibility = View.GONE
        tv_error.text = throwable?.message
        tv_error.visibility = View.VISIBLE
    }

    private fun onItemClick(movie: Movie) {
        viewModel.selectDetailMovie(movie)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        menuInflater.inflate(R.menu.menu_favorite_movie, menu)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFavoriteMovies()
    }
}