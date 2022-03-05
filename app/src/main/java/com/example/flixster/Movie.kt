package com.example.flixster

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel
import org.json.JSONArray

// Need Parcelize to be able to use for Bundle
@Parcelize
data class Movie (
    val movieId: Int,
    val voteAverage: Double,
    private val posterPath: String,
    val title : String,
    val overview: String,
) : Parcelable { // This is extending parcelable
    // This is needed in order to render actual images
    @IgnoredOnParcel
    val posterImageUrl = "https://image.tmdb.org/t/p/w342/$posterPath"

    /* Allows us to call methods on the movie class
       Without having an instance of it
     */
    companion object {
        /*
        * This function returns a mutable list of type Movie
        * */
        fun fromJsonArray(movieJsonArray: JSONArray): List<Movie> {
            val movies = mutableListOf<Movie>()
            //Loop to fetch each movie object
            for (i in 0 until movieJsonArray.length()){
                val movieJson = movieJsonArray.getJSONObject(i)
                // Populate the Movie class with the respective fields using .add()
                movies.add(
                    Movie(
                        // Make sure to have the correct key, JUST LIKE THE API JSON
                        movieJson.getInt("id"),
                        movieJson.getDouble("vote_average"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("title"),
                        movieJson.getString("overview")
                    )
                )


            }
            return movies
        }

    }
}