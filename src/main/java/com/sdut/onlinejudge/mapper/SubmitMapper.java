package com.sdut.onlinejudge.mapper;

import com.sdut.onlinejudge.model.Submit;
import org.springframework.stereotype.Repository;

/**
 * @Author: Devhui
 * @Date: 2019-12-07 16:34
 * @Version 1.0
 */
@Repository
public interface SubmitMapper {
    // 获取提交
    Submit getSubmit(String uid, int cid);

    // 判断是否已经提交
    Submit hasSubmit(String uid, int cid);

    // 提交
    int addSubmit(Submit submit);

    // 加分操作
    int addScore(int score, String username);
}
