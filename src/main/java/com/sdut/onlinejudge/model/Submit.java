package com.sdut.onlinejudge.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Devhui
 * @Date: 2019-12-07 16:40
 * @Version 1.0
 */
@Data
public class Submit {
    private String uid;
    private int cid;
    private String answers;
    private int score;
    private Date submitTime;
    private Integer rank;
}
