package com.henallux.dondesang;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Util {

    public static boolean verificationLoginLongeur(TextView editLogin){
        if(editLogin.getText().length() > 2){
            return true;
        }
        else{
            editLogin.setError("Minimun 3 caractères");
            return false;
        }
    }
    public static boolean verificationLoginDisponible(TextView editLogin) {
        if (true) { // Chercher en bd
            return true;
        } else {
            editLogin.setError("Le login n'est pas disponible");
            return false;
        }
    }
    public static boolean verificationLoginPresent(TextView editLogin){
        if(false){ // vérif si login déja présent
            return true;
        }else{
            editLogin.setError("Le login est présent dans la BD");
            return false;
        }
    }
    public static boolean verificationPassword(TextView editPassword){
        if(editPassword.length()>7){
            // !!! FAIRE D'AUTRE VERIF SI BESOIN
            return true;
        }else{
            editPassword.setError("Minimun 8 caractères");
            return false;
        }
    }

    public static boolean verificationPasswordRepeat(TextView editPassword,TextView editPasswordRepeat) {
        if(editPassword.getText().toString().equals(editPasswordRepeat.getText().toString())){
            return true;
        }else{
            editPasswordRepeat.setError("Les MDP ne sont pas identiques");
            return false;
        }
    }

    public static boolean verificationEmail(TextView email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return email.getText().toString().matches(regex);
    }
    public static boolean verificationEmailDansBD(TextView email){
        return true; // VERIF SUR EMAIL DANS BD
    }

    public static boolean verificationCodePostal(TextView codePostal){
        if(codePostal.getText().length() > 1){
            return true;
        }
        else{
            codePostal.setError("L'adresse ne peut être vide !");
            return false;
        }
    }

    public static String getJourSemaine(int index, Context context) {
        String jour = "";
        switch (index) {
            case 0 : jour = context.getResources().getString(R.string.sunday);
                break;
            case 1 : jour = context.getResources().getString(R.string.monday);
                break;
            case 2 : jour = context.getResources().getString(R.string.tuesday);
                break;
            case 3 : jour = context.getResources().getString(R.string.wednesday);
                break;
            case 4 : jour = context.getResources().getString(R.string.thursday);
                break;
            case 5 : jour = context.getResources().getString(R.string.friday);
                break;
            case 6 : jour = context.getResources().getString(R.string.saturday);
                break;
            default: jour =  context.getResources().getString(R.string.jourNonDisponible);
            break;
        }
        return jour;
    }

    public static TextView stylingTextView(String text, float textSize, Context context) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(16);
        textView.setTypeface(null, Typeface.ITALIC);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setPadding(30, 20, 20, 30);
        textView.setTextSize(textSize);

        return textView;
    }
}
