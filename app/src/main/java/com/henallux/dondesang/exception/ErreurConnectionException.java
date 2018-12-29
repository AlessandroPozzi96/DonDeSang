package com.henallux.dondesang.exception;

import com.henallux.dondesang.R;

public class ErreurConnectionException extends Throwable {

    public int statusCode;

    public ErreurConnectionException(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public String getMessage()
    {
        if(statusCode >= 400 && statusCode <500)
        {
            return "" + R.string.erreur_client_statusCode + statusCode;
        }
        else
        {
            return "" + R.string.erreur_serveur_statusCode + statusCode;
        }
    }

}
