package matheusfelipe.desafio.inchurch.presentation.pages.movie_detail

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*
import matheusfelipe.desafio.inchurch.R
import matheusfelipe.desafio.inchurch.core.App

class MovieDetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)

        Picasso.with(App.instance).load("https://image.tmdb.org/t/p/w500//ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg").fit().into(iv_poster)
        //tv_title.text = "Suicide Squad"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        //menuInflater.inflate(R.menu.menu_pedido_visualizacao, menu)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}