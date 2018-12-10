package com.henallux.dondesang.DataAcces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.dondesang.model.Collecte;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CollecteDAO {
    public ArrayList<Collecte> getAllCollectes() throws Exception
    {
        URL urlAPI = new URL("https://croixrougeapi.azurewebsites.net/api/Collectes");
        HttpURLConnection connection = (HttpURLConnection) urlAPI.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String stringJson = "", line;

        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        reader.close();
        stringJson = builder.toString();

        return jsonToCollectes(stringJson);
    }

    public ArrayList<Collecte> jsonToCollectes(String stringJson) throws Exception{
        ArrayList<Collecte> collectes = new ArrayList<Collecte>();
        Collecte collecte;
        JSONArray collectesJSON = new JSONArray(stringJson);
        JSONObject jsonObject;
        Gson gson = new GsonBuilder().create();

        for (int i = 0; i < collectesJSON.length(); i++) {
            jsonObject = collectesJSON.getJSONObject(i);
            collecte = gson.fromJson(jsonObject.toString(), Collecte.class);
            collectes.add(collecte);
        }
        return collectes;
    }
}
