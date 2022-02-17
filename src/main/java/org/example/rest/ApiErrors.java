package org.example.rest;

import java.util.Arrays;
import java.util.List;


public class ApiErrors {

    private List<String> errors;

    public ApiErrors(String errorsMsg) {
        this.errors = Arrays.asList(errorsMsg);
    }

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ApiErrors{" +
                "errors=" + errors +
                '}';
    }
}
