package com.example.mvc.model;

import java.util.List;

public class Add2Num {
    private String num1;
    private String num2;
    private String result;
    private List<String> steps;

    // Getter và Setter
    public Add2Num() {
    }

    public Add2Num( String num1, String num2, String result, List<String> steps) {
        this.num1 = num1;
        this.num2 = num2;
        this.result = result;
        this.steps = steps;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getNum2() {
        return num2;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
}
