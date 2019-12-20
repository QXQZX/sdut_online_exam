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
}
