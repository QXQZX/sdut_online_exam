package com.sdut.onlinejudge.service;

import com.sdut.onlinejudge.model.FeedBack;
import com.sdut.onlinejudge.model.StatKit;
import com.sdut.onlinejudge.model.SubmitStat;

import java.util.List;

/**
 * @Author: Devhui
 * @Date: 2019/12/23 20:01
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */
public interface StatService {
    // 获取统计信息
    StatKit getStat();

    // 根据cid获取单次测试统计信息
    List<SubmitStat> getStatByCid(String cid);

    // 获取反馈列表
    List<FeedBack> getFeedBacks();
}
