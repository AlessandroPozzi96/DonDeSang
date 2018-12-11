package com.henallux.dondesang.DataAcces;

import android.util.Log;

import com.google.gson.Gson;
import com.henallux.dondesang.exception.ErreurConnectionException;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DataUtilisateur {

    public Utilisateur getUtilisateur(String login, Token token) throws IOException, ErreurConnectionException {

        URL url = new URL("https://croixrougeapi.azurewebsites.net/api/Utilisateurs/" + login);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", "Bearer " + token.getAccess_token());

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(connection.getInputStream())));
            StringBuilder builder = new StringBuilder();
            String stringJSON = "", line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            bufferedReader.close();
            stringJSON = builder.toString();
            return jsonToUtilisateur(stringJSON);
        } else {
            throw new ErreurConnectionException(responseCode);
        }
    }

    public boolean supprimerUtilisateur(String login, Token token) throws IOException, ErreurConnectionException {

        URL url = new URL("https://croixrougeapi.azurewebsites.net/api/Utilisateurs/" + login);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Authorization", "Bearer " + token.getAccess_token());

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            return true;
        } else {
            throw new ErreurConnectionException(responseCode);
        }

    }

    public Utilisateur CreationUtilisateur(Utilisateur utilisateur) throws IOException, ErreurConnectionException, JSONException {
        URL url = new URL("https://croixrougeapi.azurewebsites.net/api/Utilisateurs");

        JSONObject postData = new JSONObject();
        postData.put("login",utilisateur.getLogin());
        postData.put("password",utilisateur.getPassword());
        postData.put("mail",utilisateur.getMail());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setDoInput(true);
        connection.setDoOutput(true);

        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os,"UTF-8")
        );

        writer.write(postData.toString());

        writer.flush();
        writer.close();
        os.close();

        int responseCode = connection.getResponseCode();
        Log.i("tag","Status code : "+responseCode);
        if(responseCode == HttpURLConnection.HTTP_CREATED)
        {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder("");
            String line = "";
            while((line = buffer.readLine())!=null)
            {
                builder.append(line);
                break;
            }
            buffer.close();
            //return builder.toString();
            return jsonToUtilisateur(builder.toString());
        }else{
            throw new ErreurConnectionException(responseCode);
        }
    }


    private Utilisateur jsonToUtilisateur(String stringJSON) {
        Gson g = new Gson();
        Utilisateur utilisateur = g.fromJson(stringJSON,Utilisateur.class);
        return utilisateur;
    }
    private String utilisateurToJson(Utilisateur utilisateur) {
        Gson g = new Gson();
        String stringJSON = g.toJson(utilisateur,Utilisateur.class);//  g.fromJson(stringJSON,Utilisateur.class);
        return stringJSON;
    }

    public Utilisateur modificationUtilisateur(Utilisateur utilisateur) throws IOException, ErreurConnectionException {
        URL url = new URL("https://croixrougeapi.azurewebsites.net/api/Utilisateurs/"+utilisateur.getLogin());
        Log.i("tag","ici");

        String stringJSON = utilisateurToJson(utilisateur);
        Log.i("tag","la");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setDoInput(true);
        connection.setDoOutput(true);

        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os,"UTF-8")
        );

        writer.write(stringJSON);

        writer.flush();
        writer.close();
        os.close();

        int responseCode = connection.getResponseCode();
        Log.i("tag","Status code : "+responseCode);
        if(responseCode == HttpURLConnection.HTTP_CREATED)
        {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder("");
            String line = "";
            while((line = buffer.readLine())!=null)
            {
                builder.append(line);
                break;
            }
            buffer.close();
            //return builder.toString();
            return jsonToUtilisateur(builder.toString());
        }else{
            throw new ErreurConnectionException(responseCode);
        }
    }
}
