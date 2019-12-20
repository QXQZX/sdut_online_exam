package com.sdut.onlinejudge.model;

import lombok.Data;

/**
 * @Author: Devhui
 * @Date: 2019-12-02 20:22
 * @Version 1.0
 */
@Data
public class UserInfo {
    String username;
    String name; // 姓名
    String gender; // 性别
    String college; // 学院
    int score; // 得分
    int times; // 测试次数
}
