package com.henallux.dondesang.exception;

import com.henallux.dondesang.R;

public class DeserialisationException extends Exception {
    private String typeDonnees, message;

    public DeserialisationException(String typeDonnees, String message) {
        this.typeDonnees = typeDonnees;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return R.string.exception_deserialisation + this.typeDonnees + R.string.exception_deserialisation_source + this.message;
    }
}
