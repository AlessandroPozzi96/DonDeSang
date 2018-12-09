package com.henallux.dondesang.exception;

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
            return "Erreur du client, Status code : "+statusCode;
        }
        else
        {
            return "Erreur du serveur, Status code : "+statusCode;
        }
    }

}
