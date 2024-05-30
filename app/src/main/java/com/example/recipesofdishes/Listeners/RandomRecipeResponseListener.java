package com.example.recipesofdishes.Listeners;

import com.example.recipesofdishes.Models.RandomRecipeApiPesponce;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiPesponce response, String message);
    void didError(String message);
}
