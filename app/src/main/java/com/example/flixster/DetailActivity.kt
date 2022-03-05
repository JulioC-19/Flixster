package com.example.flixster


import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import okhttp3.Headers


/*
* - Need to have an API KEY to be able to communicate with outside API's
* - What is the TAG used for exactly? it seems like just a display message
*/

private const val YOUTUBE_API_KEY = "AIzaSyBkaZOTwM28C4fSceQ4jv-mE6P-KnUcjvs"
private const val TAG = "DetailActivity"
private const val TRAILERS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
// This is creating another screen to transition to
// If using youtube API, make sure to extend YoutubeBaseActivity
class DetailActivity : YouTubeBaseActivity() {

    // Need variables to store the references to the views from activity_details.xml
    private lateinit var textViewTitle: TextView
    private lateinit var textViewOverview: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var ytPlayerView : YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        /*
        * - I'm starting to see the transition now, we need references to
        *   whatever we have in the .xml file to be able to operate on them
        */
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Initialize the references by finding the ID views in activity_detail.xml
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewOverview = findViewById(R.id.textViewOverview)
        ratingBar = findViewById(R.id.ratingBarVoteAv)
        ytPlayerView = findViewById(R.id.player)

        /*
        * Get movie object out of the intent extra
        * and populate to the movie class for format
        * Investigate where does MOVIE_EXTRA come from?
        * */
        val movie = intent.getParcelableExtra<Movie>(MOVIE_EXTRA) as Movie
        Log.i(TAG, "Movie is $movie")
        textViewTitle.text = movie.title
        textViewOverview.text = movie.overview
        ratingBar.rating = movie.voteAverage.toFloat()

        val client = AsyncHttpClient() // NEED TO DO THE HTTP GET REQUEST TO GET THE CORRECT TRAILER FOR EACH MOVIE
        // Notice the parameters, one is the trailer URL and what I want is the movieID
        // Then the Object type itself is a class and therefore needs to be create with its respective methods and implement members
        client.get(TRAILERS_URL.format(movie.movieId), object: JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?
            ) {
                Log.e(TAG, "onFailure $statusCode")
            }

            // Here is where we will do the parsing logic to the movie ID, dealing with JSON format
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) { // cuz this is on success don't use '?' after JSON
                Log.i(TAG, "onSuccess")
                val results = json.jsonObject.getJSONArray("results")
                if(results.length() == 0){
                    Log.w(TAG, "No movie trailers found")
                    return
                }
                val movieTrailerJson = results.getJSONObject(0)
                val youtubeKey = movieTrailerJson.getString("key")
                //this method will play youtube video with this trailer
                initializeYoutube(youtubeKey)
            }
        })
    }

    /*
    * This is the logic to play the youtube video
    * */
    private fun initializeYoutube(youtubeKey: String) {
        // Investigate, how does this work exactly?
        ytPlayerView.initialize(YOUTUBE_API_KEY, object: YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                p2: Boolean // what is p2 and why is it a Boolean
            ) {
                Log.i(TAG, "onInitializationSuccess")
                player?.cueVideo(youtubeKey)

            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Log.i(TAG,"onInitializationFailure")
            }

        })


    }
}