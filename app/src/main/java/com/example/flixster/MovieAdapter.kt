package com.example.flixster

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

private const val TAG = "MovieAdapter"
class MovieAdapter(private val context: Context, private val movies: List<Movie>)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    /*
    * What exactly is this doing?
    * Expensive operation: create a view
    * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    /*
    * Take data at that position and bind it to the viewHolder
    * Cheap: simply bind data to an existing viewHolder
    * */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder position $position")
        val movie = movies[position]
        holder.bind(movie)
    }

    // Returns the number of movies in movies list
    /* same as  () : Int { return movies.size }
    * */
    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        // It seems like this is grabbing the views from the layout
        private val imageViewPoster = itemView.findViewById<ImageView>(R.id.imageViewPoster)
        private val textViewTitle = itemView.findViewById<TextView>(R.id.textViewTitle)
        private val textViewOverview = itemView.findViewById<TextView>(R.id.textViewOverview)

        // is this putting all together? how does this annotation work?
        fun bind(movie: Movie){
            textViewTitle.text = movie.title
            textViewOverview.text = movie.overview
            Glide.with(context).load(movie.posterImageUrl).into(imageViewPoster)


        }
    }

}
