package com.sdut.onlinejudge.model;

import lombok.Data;

import java.util.ArrayList;

/**
 * @Author: Devhui
 * @Date: 2019-12-04 22:00
 * @Version 1.0
 */
@Data
public class Answer {
    private ArrayList<String> SingleSelectsAns;
    private ArrayList<String> MultiSelectsAns;
    private ArrayList<String> JudgeAns;
}
