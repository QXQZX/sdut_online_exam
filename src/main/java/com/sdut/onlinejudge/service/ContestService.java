package com.sdut.onlinejudge.service;

import com.sdut.onlinejudge.model.Contest;

import java.util.List;
import java.util.Map;

/**
 * @Author: Devhui
 * @Date: 2019-11-28 17:05
 * @Version 1.0
 */
public interface ContestService {
    // 获取所有测试信息
    List<Contest> findAll(String keyWords);

    // 根据cid获取测试题目信息
    Map<String, Object> getContestByCid(int cid);

    // 发布新的测试
    int deployContest(Map<String, String> contestInfo);

    int deployContestSelf(Map<String, Object> contestInfo);

    // 根据cid获取答案信息
    Map<String, Object> getAnswerByCid(int cid);

    // 根据cid获取题目分数信息
    Map<String, Float> getProblemScore(int cid);
}
