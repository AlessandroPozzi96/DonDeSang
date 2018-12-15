package com.henallux.dondesang.services;

import com.henallux.dondesang.model.Collecte;
import com.henallux.dondesang.model.Utilisateur;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CollecteService {

    @GET("Collectes/{id}")
    Call<Collecte> getCollecte(@Path("id")int id);

}
