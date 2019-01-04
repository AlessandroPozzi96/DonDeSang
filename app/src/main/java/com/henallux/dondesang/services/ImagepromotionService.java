package com.henallux.dondesang.services;

import com.henallux.dondesang.model.Imagepromotion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ImagepromotionService {
    @GET("ImagesPromotions")
    Call<List<Imagepromotion>> getImagesPromotions();
}
