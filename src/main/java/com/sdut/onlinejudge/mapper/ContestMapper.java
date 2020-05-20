package com.sdut.onlinejudge.mapper;

import com.sdut.onlinejudge.model.Contest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Devhui
 * @Date: 2019-11-28 17:05
 * @Version 1.0
 */

@Repository
public interface ContestMapper {
    List<Contest> findAll(String keyWords);

    int deployContest(Contest c);

    String getContestByCid(int cid);

    String getAnswerByCid(int cid);

    // 根据cid获取题目分数信息
    Map<String, Float> getProblemScore(int cid);

    // 获取测试时间信息
    Map<String, Object> getContestTime(int cid);

    // 删除测试
    int deleteContest(int cid);

    // 更新测试信息
    int updateContest(Contest contest);
}
