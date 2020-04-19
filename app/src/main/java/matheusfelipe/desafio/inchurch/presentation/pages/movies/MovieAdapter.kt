package matheusfelipe.desafio.inchurch.presentation.pages.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_simple.view.*
import matheusfelipe.desafio.inchurch.BuildConfig
import matheusfelipe.desafio.inchurch.R
import matheusfelipe.desafio.inchurch.core.App
import matheusfelipe.desafio.inchurch.domain.entities.Movie

class MovieAdapter(
    private var movieList: MutableList<Movie>,
    private val onClick: (movie: Movie) -> Unit,
    private val onFavoriteClick: (movie: Movie, pos: Int) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    fun movieList() = movieList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_simple, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = movieList[position]

        holder.itemView.tv_title.text = item.title
        Picasso.with(App.instance).load("${BuildConfig.IMAGE_URL}${item.posterPath}").into(holder.itemView.iv_poster)
        holder.itemView.iv_poster.setOnClickListener {
            onClick(item)
        }
        holder.itemView.iv_fav.setOnClickListener {
            onFavoriteClick(item, position)
        }

        holder.itemView.iv_fav.setImageResource(
            if (item.isFavorite)
                R.drawable.ic_star_primary_24dp
            else
                R.drawable.ic_star_border_primary_24dp
        )
    }

    fun updateItem(pos: Int) {
        notifyItemChanged(pos)
    }

    fun updateAllItems(list: MutableList<Movie>) {
        this.movieList = list
        notifyDataSetChanged()
    }

    fun addItems(list: MutableList<Movie>) {
        this.movieList.addAll(list)
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}