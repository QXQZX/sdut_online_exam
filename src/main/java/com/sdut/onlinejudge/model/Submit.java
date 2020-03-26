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
    private Integer cid;
    private String answers;
    private Float score;
    private Date submitTime;
    private Integer rank;
}
