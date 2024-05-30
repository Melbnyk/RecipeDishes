package com.example.recipesofdishes;

import android.content.Context;

import com.example.recipesofdishes.Listeners.RandomRecipeResponseListener;
import com.example.recipesofdishes.Listeners.RecipeDetailsListener;
import com.example.recipesofdishes.Models.RandomRecipeApiPesponce;
import com.example.recipesofdishes.Models.RecipeDetailsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {

    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListener Listener,List<String> tags){
        CallRandomRecipies callRandomRecipes = retrofit.create(CallRandomRecipies.class);
        Call<RandomRecipeApiPesponce> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key), "10",tags);
        call.enqueue(new Callback<RandomRecipeApiPesponce>() {
            @Override
            public void onResponse(Call<RandomRecipeApiPesponce> call, Response<RandomRecipeApiPesponce> response) {
                if(!response.isSuccessful()){
                    Listener.didError(response.message());
                    return;
                }
                Listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiPesponce> call, Throwable throwable) {
                Listener.didError(throwable.getMessage());
            }
        });
    }


    public void getRecipeDetails(RecipeDetailsListener listener,int id){
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());

            }

            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable throwable) {
                listener.didError(throwable.getMessage());

            }
        });


    }

    private interface CallRandomRecipies{
        @GET("recipes/random")
        Call<RandomRecipeApiPesponce> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("include-tags") List<String> tags
        );
    }

    private interface CallRecipeDetails{
        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(
               @Path("id") int id,
               @Query("apiKey") String apiKey
        );
    }

}
