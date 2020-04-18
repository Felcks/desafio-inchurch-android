package matheusfelipe.desafio.inchurch.presentation.pages.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_simple.view.*
import matheusfelipe.desafio.inchurch.R
import matheusfelipe.desafio.inchurch.domain.entities.Movie

class MovieAdapter(private val movieList: MutableList<Movie>): RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_simple, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = movieList[position]

        holder.itemView.tv_title.text = item.title
        //holder.itemView.tv
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view)
}