package com.henallux.dondesang.services;

import com.henallux.dondesang.model.Statistique;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface StatistiqueService {
    @GET("Statistiques/{login}")
    Call<Statistique> getStatistique(@Header("Authorization") String tokenAcces, @Path("login")String login);
}
