package com.sdut.onlinejudge.model;

import lombok.Data;

import java.util.Date;


@Data
public class Contest {
    private String cid;
    private String cname;
    private String problems;
    private String answers;
    //    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8", locale = "zh")
    private Date startTime;
    private Date endTime;
//    private int status; // 状态  -1等待  1 开始  0 已经结束

    private Float singleScore;
    private Float multiScore;
    private Float judgeScore;
}
