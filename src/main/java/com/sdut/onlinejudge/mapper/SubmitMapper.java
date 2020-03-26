package com.sdut.onlinejudge.mapper;

import com.sdut.onlinejudge.model.Submit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: Devhui
 * @Date: 2019-12-07 16:34
 * @Version 1.0
 */
@Repository
public interface SubmitMapper {
    // 获取提交
    Submit getSubmit(@Param("uid") String uid, @Param("cid") int cid);

    // 判断是否已经提交
    Submit hasSubmit(@Param("uid") String uid, @Param("cid") int cid);

    // 提交
    int addSubmit(Submit submit);

    // 加分操作
    int addScore(@Param("score") float score, @Param("username") String username);
}
