package com.example.recipesofdishes.Listeners;

import com.example.recipesofdishes.Models.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response,String message);
    void didError(String message);
}
