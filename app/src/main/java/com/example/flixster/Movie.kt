package com.example.flixster

import org.json.JSONArray


data class Movie (
    val movieId: Int,
    private val posterPath: String,
    val title : String,
    val overview: String,
){
    // This is needed in order to render actual images
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
                        movieJson.getInt("id"),
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