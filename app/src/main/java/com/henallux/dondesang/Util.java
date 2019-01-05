package com.henallux.dondesang;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Util {


    public static String verificationPasswordRepeat(String editPassword,String editPasswordRepeat) {
        if(editPassword.equals(editPasswordRepeat)){
            return null;
        }else{
            return "Les MDP ne sont pas identiques";
        }
    }

    public static String verificationEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        if(email.matches(regex)){
            return null;
        }else{
            return "Mauvaise syntaxe";
        }
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

    public static String verificationTailleminimal(String chaine, int tailleMin) {
        return (chaine.length()>= tailleMin)? null:"Minimun "+tailleMin+" caractéres";
    }

    public static String verificationTailleIntervale(String chaine, int min, int max) {
        if(chaine.length()<min){
            return "Minimun "+min+ " caractéres";
        }
        if(chaine.length()>max){
            return "Maximun "+max+" caractéres";
        }
        return null;
    }
}
