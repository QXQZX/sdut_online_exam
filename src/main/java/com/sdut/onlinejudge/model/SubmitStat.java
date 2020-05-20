package com.sdut.onlinejudge.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Devhui
 * @Date: 2020/1/8 21:56
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */

@Data
public class SubmitStat {
    private Integer cid;
    private String username;
    private String name;
    private String gender;
    private String college;
    private Float score;
    private Date submitTime;
}
