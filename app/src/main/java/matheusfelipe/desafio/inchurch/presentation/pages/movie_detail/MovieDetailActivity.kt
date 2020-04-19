package matheusfelipe.desafio.inchurch.presentation.pages.movie_detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*
import matheusfelipe.desafio.inchurch.BuildConfig
import matheusfelipe.desafio.inchurch.R
import matheusfelipe.desafio.inchurch.core.App
import matheusfelipe.desafio.inchurch.core.utils.Response
import matheusfelipe.desafio.inchurch.core.utils.Status
import matheusfelipe.desafio.inchurch.domain.entities.Genre
import matheusfelipe.desafio.inchurch.domain.entities.Movie
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat

class MovieDetailActivity : AppCompatActivity() {

    private val viewModel: MovieDetailViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)
        
        viewModel.detailMovie().observe(this, Observer { response -> processResponse(response) })
        viewModel.genres().observe(this, Observer { response -> processResponse(response) })
        viewModel.favoriteOrDisfavorMovieResponse().observe(this, Observer { response -> processResponse(response) })

        toolbar.title = ""
    }

    private fun processResponse(response: Response) {
        when (response.status) {
            Status.LOADING -> showLoading()
            Status.SUCCESS -> showMovie(response.data)
            Status.EMPTY_RESPONSE -> {
            }
            Status.ERROR -> showError(response.error)
        }
    }

    private fun showLoading() {
        pg_loading.visibility = View.VISIBLE
    }

    private fun showMovie(data: Any?) {

        if (viewModel.detailMovie().value != null &&
            viewModel.detailMovie().value?.status == Status.SUCCESS &&
            viewModel.genres().value != null &&
            viewModel.genres().value?.status == Status.SUCCESS
        ) {
            pg_loading.visibility = View.GONE
            this.fillScreen(
                (viewModel.genres().value?.data as List<*>).filterIsInstance<Genre>(),
                viewModel.detailMovie().value?.data as Movie
            )
        }
    }

    private fun showError(throwable: Throwable?) {
        pg_loading.visibility = View.GONE
        Toast.makeText(applicationContext, throwable?.message, Toast.LENGTH_SHORT).show()
        onBackPressed()
    }

    private fun fillScreen(genres: List<Genre>, movie: Movie) {
        toolbar.title = movie.title

        Picasso.with(App.instance)
            .load("${BuildConfig.IMAGE_URL}${movie.backdropPath}").fit()
            .into(iv_poster)

        tv_title.text = movie.title
        tv_release_date.text = SimpleDateFormat("dd/MM/yyyy").format(movie.releaseDate)
        tv_genres.text = movie.getGenres(genres).joinToString { it.name }
        tv_overview.text = movie.overview

        toolbar.menu.findItem(R.id.ic_favor_disfavor).setIcon(
            if(movie.isFavorite)
                R.drawable.ic_star_white_24dp
            else
                R.drawable.ic_star_border_white_24dp
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        menuInflater.inflate(R.menu.menu_detail_movie, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.ic_favor_disfavor -> {
                viewModel.favoriteOrDisfavorMovie()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}