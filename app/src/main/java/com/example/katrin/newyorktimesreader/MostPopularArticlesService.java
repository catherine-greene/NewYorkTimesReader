package com.example.katrin.newyorktimesreader;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MostPopularArticlesService {

    @GET("{type}/all-sections/30.json")
    Call<Result> getMostPopular(
            @Path("type") String type,
            @Query("api-key") String apiKey
    );
}
