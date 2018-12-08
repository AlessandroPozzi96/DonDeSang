package com.henallux.dondesang;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UtilisateurDAO {
    public boolean getUtilisateurPresent() throws Exception{
        URL url = new URL("");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String stringJSON = "",line;

        return false;
    }
}
