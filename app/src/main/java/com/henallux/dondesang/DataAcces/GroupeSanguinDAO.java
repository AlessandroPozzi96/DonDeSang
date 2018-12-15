package com.henallux.dondesang.DataAcces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.dondesang.exception.DeserialisationException;
import com.henallux.dondesang.model.Collecte;
import com.henallux.dondesang.model.GroupeSanguin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GroupeSanguinDAO {
    private String tag = "GroupeSanguinDAO";

    public ArrayList<GroupeSanguin> getAllGroupesSanguin() throws DeserialisationException, Exception
    {
        URL urlAPI = new URL("https://croixrougeapi.azurewebsites.net/api/GroupesSanguins");
        HttpURLConnection connection = (HttpURLConnection) urlAPI.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String stringJson = "", line;

        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        reader.close();
        stringJson = builder.toString();

        return jsonToGroupesSanguins(stringJson);
    }

    public ArrayList<GroupeSanguin> jsonToGroupesSanguins(String stringJson) throws DeserialisationException {
        ArrayList<GroupeSanguin> groupesSanguins = new ArrayList<>();
        GroupeSanguin groupeSanguin;
        JSONObject jsonObject;
        Gson gson = new GsonBuilder().create();

        try
        {
            JSONArray collectesJSON = new JSONArray(stringJson);
            for (int i = 0; i < collectesJSON.length(); i++) {
                jsonObject = collectesJSON.getJSONObject(i);
                groupeSanguin = gson.fromJson(jsonObject.toString(), GroupeSanguin.class);
                groupesSanguins.add(groupeSanguin);
            }
        }
        catch (JSONException e)
        {
            throw new DeserialisationException("JSON", e.getMessage());
        }

        return groupesSanguins;
    }
}
