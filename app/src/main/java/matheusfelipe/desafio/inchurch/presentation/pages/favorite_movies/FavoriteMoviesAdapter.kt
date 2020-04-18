package matheusfelipe.desafio.inchurch.presentation.pages.favorite_movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_complete.view.*
import matheusfelipe.desafio.inchurch.BuildConfig
import matheusfelipe.desafio.inchurch.R
import matheusfelipe.desafio.inchurch.core.App
import matheusfelipe.desafio.inchurch.domain.entities.Movie
import java.text.SimpleDateFormat

class FavoriteMoviesAdapter (private val movieList: MutableList<Movie>,
                             private val onClick: (movie: Movie) -> Unit): RecyclerView.Adapter<FavoriteMoviesAdapter.MyViewHolder>() {

    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_complete, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = movieList[position]

        holder.itemView.tv_title.text = item.title
        holder.itemView.tv_overview.text = item.overview
        holder.itemView.tv_release_date.text = simpleDateFormat.format(item.releaseDate)
        Picasso.with(App.instance).load("${BuildConfig.IMAGE_URL}${item.posterPath}").into(holder.itemView.iv_poster)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view)
}