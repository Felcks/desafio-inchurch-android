package matheusfelipe.desafio.inchurch.presentation.pages.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_movies.*
import matheusfelipe.desafio.inchurch.R
import matheusfelipe.desafio.inchurch.core.utils.Response
import matheusfelipe.desafio.inchurch.core.utils.Status
import matheusfelipe.desafio.inchurch.domain.entities.Movie
import matheusfelipe.desafio.inchurch.presentation.pages.favorite_movies.FavoriteMoviesActivity
import matheusfelipe.desafio.inchurch.presentation.pages.movie_detail.MovieDetailActivity
import matheusfelipe.desafio.inchurch.presentation.widgets.InfiniteScrollListener

class MoviesActivity : AppCompatActivity() {

    private lateinit var viewModel: MoviesViewModel
    private var movieAdapter: MovieAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        viewModel = ViewModelProviders.of(this)[MoviesViewModel::class.java]
        viewModel.movies().observe(this, Observer { response -> processResponse(response) })
        viewModel.selectDetailMovieResponse()
            .observe(this, Observer { response -> processOnSelectMovieResponse(response) })
        viewModel.favoriteOrDisfavorMovieResponse()
            .observe(this, Observer { response -> processOnFavorOrDisfavorMovieResponse(response) })

        ll_error.setOnClickListener {
            viewModel.fetchAllMovies()
        }
    }

    private fun processOnSelectMovieResponse(response: Response) {
        if (response.status == Status.SUCCESS) {
            if (response.data != null) {
                val intent = Intent(this, MovieDetailActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun processOnFavorOrDisfavorMovieResponse(response: Response) {
        if (response.status == Status.SUCCESS) {
            if (response.data != null) {
                movieAdapter?.updateItem(response.data as Int)
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
        if (movieAdapter == null) {
            pg_loading.visibility = View.VISIBLE
            rv_movies.visibility = View.GONE
            ll_error.visibility = View.GONE
        }
    }

    private fun showMovies(data: Any?) {
        pg_loading.visibility = View.GONE
        rv_movies.visibility = View.VISIBLE

        if (data is List<*>) {

            if (movieAdapter == null) {
                movieAdapter = MovieAdapter(
                    data.filterIsInstance<Movie>().toMutableList(),
                    ::onItemClick,
                    ::onFavoriteClick
                )

                val layoutManager = GridLayoutManager(this, 2)
                rv_movies.layoutManager = layoutManager
                rv_movies.addOnScrollListener(
                    InfiniteScrollListener({
                        viewModel.loadMoreMovies()
                    }, layoutManager)
                )
                rv_movies.adapter = movieAdapter
            } else {
                movieAdapter?.addItems(data.filterIsInstance<Movie>().toMutableList())
                //movieAdapter?.updateAllItems(data.filterIsInstance<Movie>().toMutableList())
            }
        }
    }


    private fun showError(throwable: Throwable?) {
        pg_loading.visibility = View.GONE
        ll_error.findViewById<TextView>(R.id.tv_error).text = throwable?.message
        ll_error.visibility = View.VISIBLE
    }

    private fun onItemClick(movie: Movie) {
        viewModel.selectDetailMovie(movie)
    }

    private fun onFavoriteClick(movie: Movie, pos: Int) {
        viewModel.favoriteOrDisfavorMovie(movie, pos)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_all_movies, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.ic_bookmark -> {
                val intent = Intent(this, FavoriteMoviesActivity::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        //viewModel.fetchAllMovies()
    }
}
