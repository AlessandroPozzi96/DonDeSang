package com.henallux.dondesang.DataAcces;

import com.google.gson.Gson;
import com.henallux.dondesang.model.Token;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiAuthentification {

    public Token getUtilisateurPresent() throws Exception{
        URL url = new URL("https://croixrougeapi.azurewebsites.net/api/Jwt");

        JSONObject postData = new JSONObject();
        postData.put("UserName","Gwynbleidd");
        postData.put("Password","MotDePasseNonHashé");

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

        if(responseCode == HttpURLConnection.HTTP_OK)
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
            return convertJSONToToken(builder.toString());
        }else{
            //return new String("false "+responseCode);
            return null;
        }
    }

    public Token convertJSONToToken(String JSONString)
    {
        Gson g = new Gson();
        Token token = g.fromJson(JSONString,Token.class);
        return token;
    }


}
