package matheusfelipe.desafio.inchurch.presentation.pages.favorite_movies

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_favorite_movies.*
import matheusfelipe.desafio.inchurch.R
import matheusfelipe.desafio.inchurch.core.utils.Response
import matheusfelipe.desafio.inchurch.core.utils.Status
import matheusfelipe.desafio.inchurch.domain.entities.Movie
import matheusfelipe.desafio.inchurch.presentation.pages.movie_detail.MovieDetailActivity
import org.koin.android.ext.android.inject

class FavoriteMoviesActivity : AppCompatActivity() {

    private val viewModel: FavoriteMoviesViewModel by inject()
    private var favoriteMoviesAdapter: FavoriteMoviesAdapter? = null
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_movies)

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
        tv_error.visibility = View.GONE
        if(favoriteMoviesAdapter == null)
            pg_loading.visibility = View.VISIBLE
    }

    private fun showMovies(data: Any?) {

        pg_loading.visibility = View.GONE
        tv_error.visibility = View.GONE

        if (data is List<*>) {

            if (data.isEmpty()) {
                showError(Throwable("Nenhum filme favorito encontrado"))
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

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        this.searchView = (menu?.findItem(R.id.ic_search)?.actionView as SearchView).apply {

            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
            inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS

            val closeButton = findViewById<ImageView>(R.id.search_close_btn)
            closeButton.setOnClickListener {
                setQuery(null, false)
                clearFocus()
                viewModel.searchMovies("")
            }

            val queryTextListener = object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.searchMovies(newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    return true
                }
            }

            this.setOnQueryTextListener(queryTextListener)
        }

        return super.onCreateOptionsMenu(menu)
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