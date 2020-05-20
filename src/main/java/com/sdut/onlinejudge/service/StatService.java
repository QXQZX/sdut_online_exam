package com.sdut.onlinejudge.service;

import com.sdut.onlinejudge.model.FeedBack;
import com.sdut.onlinejudge.model.StatKit;
import com.sdut.onlinejudge.model.SubmitStat;
import com.sdut.onlinejudge.model.TrainStat;

import java.util.List;
import java.util.Map;

/**
 * @Author: Devhui
 * @Date: 2019/12/23 20:01
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */
public interface StatService {
    // 获取统计信息
    StatKit getStat();

    // 提交量统计
    List<TrainStat> getSubmitStat(String uid);

    // 根据cid获取单次测试统计信息
    List<SubmitStat> noSubmitStat(Integer cid);

    // 根据cid获取单次测试统计信息
    List<SubmitStat> getStatByCid(int cid);

    // 获取反馈列表
    List<FeedBack> getFeedBacks();

    // 获取单选题来源统计
    List<Map> getSingleHint();

    // 获取多选题来源统计
    List<Map> getMultiHint();

    // 获取判断题来源统计
    List<Map> getJudgeHint();
}
