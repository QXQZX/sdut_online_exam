package com.sdut.onlinejudge.model;

import lombok.Data;

@Data
public class JudgeProblem {
    int jpid;
    String title;
    String optionT;
    String optionF;
    String answer;
    String hint;
}
