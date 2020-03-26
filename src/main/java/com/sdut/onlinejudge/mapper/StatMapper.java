package com.sdut.onlinejudge.mapper;

import com.sdut.onlinejudge.model.FeedBack;
import com.sdut.onlinejudge.model.StatKit;
import com.sdut.onlinejudge.model.SubmitStat;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Devhui
 * @Date: 2019/12/23 19:59
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */
@Repository
public interface StatMapper {
    StatKit getStat();

    // 根据cid获取单次测试统计信息
    List<SubmitStat> getStatByCid(int cid);

    // 获取反馈列表
    List<FeedBack> getFeedBacks();

    // 获取单选题来源统计
    List<Map> getSingleHint();

    // 获取多选题来源统计
    List<Map> getMultiHint();
}
