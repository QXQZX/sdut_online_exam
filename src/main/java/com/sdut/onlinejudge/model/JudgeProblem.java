package com.sdut.onlinejudge.model;

import lombok.Data;

@Data
public class JudgeProblem {
    int jpid;
    String title;
    String optionT;
    String optionF;
    String answer;
    String label;
    String hint;

    public JudgeProblem(String title, String optionT, String optionF, String answer, String label, String hint) {
        this.title = title;
        this.optionT = optionT;
        this.optionF = optionF;
        this.answer = answer;
        this.label = label;
        this.hint = hint;
    }
}
