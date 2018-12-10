package com.henallux.dondesang.DataAcces;

import android.util.Log;

import com.google.gson.Gson;
import com.henallux.dondesang.exception.ErreurConnectionException;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DataUtilisateur {

    public Utilisateur getUtilisateur(String login, Token token) throws IOException, ErreurConnectionException {

        URL url = new URL("https://croixrougeapi.azurewebsites.net/api/Utilisateurs/"+login);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization","Bearer "+token.getAccess_token());

        int responseCode = connection.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(connection.getInputStream())));
            StringBuilder builder = new StringBuilder();
            String stringJSON = "", line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            bufferedReader.close();
            stringJSON = builder.toString();
            return jsonToUtilisateur(stringJSON);
        }
        else{
            throw new ErreurConnectionException(responseCode);
        }
    }

    public boolean supprimerUtilisateur(String login,Token token) throws IOException, ErreurConnectionException {

        URL url = new URL("https://croixrougeapi.azurewebsites.net/api/Utilisateurs/"+login);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Authorization","Bearer "+token.getAccess_token());

        int responseCode = connection.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK) {
            return true;
        }
        else{
            throw new ErreurConnectionException(responseCode);
        }

    }

    private Utilisateur jsonToUtilisateur(String stringJSON) {
        Gson g = new Gson();
        Log.i("tag","OK : "+stringJSON);
        Utilisateur utilisateur = g.fromJson(stringJSON,Utilisateur.class);
        Log.i("tag","login : "+utilisateur.getLogin());
        return utilisateur;
    }

}
