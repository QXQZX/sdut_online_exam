package com.sdut.onlinejudge.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Devhui
 * @Date: 2020/2/3 19:39
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */
@Data
public class Notice {
    private Integer nid;
    private String content;
    private Date dateTime;
    private String type;
    private String status;
}
