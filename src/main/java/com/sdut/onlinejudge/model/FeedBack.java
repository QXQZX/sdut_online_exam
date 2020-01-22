package com.sdut.onlinejudge.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Devhui
 * @Date: 2019-12-08 21:52
 * @Version 1.0
 */
@Data
public class FeedBack {
    private int fid;
    private String contact;
    private String content; // 反馈信息
    private Date feedTime;
}
