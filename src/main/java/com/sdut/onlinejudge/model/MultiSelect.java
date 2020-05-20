package com.sdut.onlinejudge.model;

import lombok.Data;

@Data
public class MultiSelect {
    int mpid;
    String title;
    String optionA;
    String optionB;
    String optionC;
    String optionD;
    String optionE;
    String answer;
    String hint;
    String label;

    public MultiSelect(String title, String optionA, String optionB, String optionC, String optionD, String optionE, String answer, String hint, String label) {
        this.title = title;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.optionE = optionE;
        this.answer = answer;
        this.hint = hint;
        this.label = label;
    }
}
