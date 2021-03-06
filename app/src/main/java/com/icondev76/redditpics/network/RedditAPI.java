package com.icondev76.redditpics.network;

import com.icondev76.redditpics.model.Feed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RedditAPI {

    String BASE_URL = "https://www.reddit.com/r/";

    //Non-static feed name
    @GET("{feed_name}/new/.json")
    Call<Feed> getFeed(@Path("feed_name") String feed_name,
                @Query("limit") int n);
}
