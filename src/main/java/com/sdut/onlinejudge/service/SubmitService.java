package com.sdut.onlinejudge.service;

import com.sdut.onlinejudge.model.Submit;

/**
 * @Author: Devhui
 * @Date: 2019-12-07 16:42
 * @Version 1.0
 */
public interface SubmitService {
    // 获取提交记录
    Submit getSubmit(String uid, int cid);

    // 判断是否已经提交
    Submit hasSubmit(String uid, int cid);

    // 添加提交记录
    int addSubmit(Submit submit);

    // 加分操作
    int addScore(int score, String username);
}
