package com.henallux.dondesang.exception;

public class ModelException extends Exception {
    private String model, variable;

    public ModelException(String model, String variable) {
        this.model = model;
        this.variable = variable;
    }

    @Override
    public String getMessage() {
        return "Exception levée ! Model : " + model + " variable : " + variable;
    }
}
