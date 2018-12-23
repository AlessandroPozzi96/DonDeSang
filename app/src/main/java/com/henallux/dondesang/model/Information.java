package com.henallux.dondesang.model;

public class Information {
    private Integer id;
    private String question;
    private String reponse;
    private String fkUtilisateur;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getFkUtilisateur() {
        return fkUtilisateur;
    }

    public void setFkUtilisateur(String fkUtilisateur) {
        this.fkUtilisateur = fkUtilisateur;
    }
}
