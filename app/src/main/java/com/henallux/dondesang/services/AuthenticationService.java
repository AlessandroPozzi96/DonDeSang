package com.henallux.dondesang.services;

import com.henallux.dondesang.model.Login;
import com.henallux.dondesang.model.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthenticationService {

    @POST("Jwt")
    Call<Token> getToken(@Body Login login);



}
