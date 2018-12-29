package com.henallux.dondesang.exception;

import com.henallux.dondesang.R;

public class ModelException extends Exception {
    private String model, variable;

    public ModelException(String model, String variable) {
        this.model = model;
        this.variable = variable;
    }

    @Override
    public String getMessage() {
        return "" + R.string.exception_model + model + R.string.exception_model_variable + variable;
    }
}
