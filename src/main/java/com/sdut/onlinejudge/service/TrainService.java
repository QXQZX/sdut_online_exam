package com.sdut.onlinejudge.service;

import com.sdut.onlinejudge.model.Train;

import java.util.List;
import java.util.Map;

/**
 * @Author: Devhui
 * @Date: 2020/4/8 17:36
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */
public interface TrainService {
    // 获取所有测试信息
    List<Train> findAll(String uid);

    // 根据tid获取测试题目信息
    Map<String, Object> getTrain(Integer tid);

    // 根据cid获取答案信息
    Map<String, Object> getAnswer(Integer tid);

    // 发布新的测试
    int deployTrain(String uid);

    // 提交
    int trainSubmit(Train train);

    // 获取提交信息
    Map<String, String> checkSubmit(Integer tid);

    // 获取专题名称 和统计数量
    List<Map> getTopics();

    List getTopicProblem(String type, String label);
}
