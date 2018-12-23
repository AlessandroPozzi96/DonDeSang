package com.henallux.dondesang.services;

import com.henallux.dondesang.model.Information;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InformationService {
    @GET("Informations")
    Call<List<Information>> getInformations();
}
