package com.example.flixster

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/* IMPORTANT TERMINOLOGY:
*   Adapter : The glues between the views and the recycler views and the underlying dataset
*
*
* */


const val MOVIE_EXTRA = "MOVIE_EXTRA"
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

    // WEEK 2 : Implement the View.OnClickListener
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        // It seems like this is grabbing the views from the layout
        private val imageViewPoster = itemView.findViewById<ImageView>(R.id.imageViewPoster)
        private val textViewTitle = itemView.findViewById<TextView>(R.id.textViewTitle)
        private val textViewOverview = itemView.findViewById<TextView>(R.id.textViewOverview)

        // Need to initialize the View.OnClickListener
        init {
            itemView.setOnClickListener(this)
        }

        // is this putting all together? how does this annotation work?
        // Logic for the adapter
        // Get notified of clip on the recyclerView? -- Need to understand this..
        fun bind(movie: Movie){
            textViewTitle.text = movie.title
            textViewOverview.text = movie.overview
            Glide.with(context).load(movie.posterImageUrl).into(imageViewPoster)
        }

        override fun onClick(p0: View?) {
            // 1. Get notified of the particular movie which was clicked
            // Tells me the position of this particular viewholder within the movie set (??)
            val movie = movies[adapterPosition]

            // Make sure to add .show()
            Toast.makeText(context, movie.title, Toast.LENGTH_SHORT).show()

            // 2. Use the intent system to navigate to the new activity.
            //    Make sure to create the new activity
            //    Once the new activity is created in the folder, declare a reference
            val intent = Intent(context, DetailActivity::class.java) // This is the class I want to navigate to

            // Pass the movie component into the intent
            // Need to learn about some other special data structs, use type Parcelable
            intent.putExtra(MOVIE_EXTRA, movie)
            context.startActivity(intent)

        }
    }

}
