package com.henallux.dondesang;

import android.widget.TextView;

public class Util {

    public static boolean verificationLoginLongeur(TextView editLogin){
        if(editLogin.getText().length() > 5){
            return true;
        }
        else{
            editLogin.setError("Minimun 6 caractères");
            return false;
        }
    }
    public static boolean verificationLoginDisponible(TextView editLogin) {
        if (false) { // Chercher en bd
            return true;
        } else {
            editLogin.setError("Le login n'est pas disponible");
            return false;
        }
    }
    public static boolean verificationLoginPresent(TextView editLogin){
        if(false){
            return true;
        }else{
            editLogin.setError("Le login n'est pas présent dans la BD");
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
            codePostal.setError("L'addresse ne peut être vide !");
            return false;
        }
    }
}
