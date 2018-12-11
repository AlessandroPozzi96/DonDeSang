package com.henallux.dondesang.exception;

public class DeserialisationException extends Exception {
    private String typeDonnees, message;

    public DeserialisationException(String typeDonnees, String message) {
        this.typeDonnees = typeDonnees;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "Exception levée en voulant désérialisé un fichier de type : " + this.typeDonnees + "\n Source de l'erreur : " + this.message;
    }
}
