package com.sdut.onlinejudge.model;

import lombok.Data;

/**
 * @Author: Devhui
 * @Date: 2020/4/8 17:57
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */

@Data
public class Train {
    private Integer tid;
    private String tname;
    private String uid;
    private String problems;
    private String answers;
    private String uAnswers;
    private Float score;
    private Integer status;
    private Integer costTime;
}
