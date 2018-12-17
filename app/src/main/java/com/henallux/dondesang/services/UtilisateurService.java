package com.henallux.dondesang.services;

import com.henallux.dondesang.model.Utilisateur;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UtilisateurService {

    // un seul id
    @GET("Utilisateurs/{login}") // {} correspond a notre path du dessous
    Call<Utilisateur> getUtilisateur(@Header("Authorization") String tokenAcces, @Path("login")String login);

    @POST("Utilisateurs")
    Call<Utilisateur> createUtilisateur(@Body Utilisateur newUtilisateur);

    @DELETE("Utilisateurs/{login}")
    Call<Void> deleteUtilisateur(@Header("Authorization") String tokenAcces, @Path("login")String login);

    @PUT("Utilisateurs/{login}")
    Call<Utilisateur> putUtilisateur(@Header("Authorization") String tokenAcces, @Path("login")String login, @Body Utilisateur utilisateurModif);
}
