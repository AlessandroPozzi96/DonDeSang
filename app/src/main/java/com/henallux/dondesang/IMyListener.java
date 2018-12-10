package com.henallux.dondesang;

import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;

public interface IMyListener {

    void setToken(Token token);

    void setUtilisateur(Utilisateur result);

    Token getToken();
}
