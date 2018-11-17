package com.henallux.dondesang;

import android.widget.TextView;

public class UtilTest {

    public static boolean verificationLoginLongeur(TextView editLogin){
        if(editLogin.getText().length() > 5){
            return true;
        }
        else{
            editLogin.setError("Minimun 6 caractères");
            return false;
        }
    }
    public static boolean verificationLoginDisponibilite(TextView editLogin){
        if(false){
            return true;
        }else{
            editLogin.setError("Login");
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

}
