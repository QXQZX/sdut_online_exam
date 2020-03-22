package com.sdut.onlinejudge.model;

import lombok.Data;

@Data
public class SingleSelect {
    int spid;
    String title;
    String optionA;
    String optionB;
    String optionC;
    String optionD;
    String optionE;
    String answer;
    String hint;

    public SingleSelect(String title, String optionA, String optionB,
                        String optionC, String optionD, String optionE, String answer, String hint) {
        this.title = title;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.optionE = optionE;
        this.answer = answer;
        this.hint = hint;
    }
}
