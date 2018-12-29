package com.henallux.dondesang.services;

import com.henallux.dondesang.model.GroupeSanguin;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GroupeSanguinService {
    @GET("GroupesSanguins")
    Call<ArrayList<GroupeSanguin>> getGroupesSanguins();
}
