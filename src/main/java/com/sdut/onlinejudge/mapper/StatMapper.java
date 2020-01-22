package com.sdut.onlinejudge.mapper;

import com.sdut.onlinejudge.model.FeedBack;
import com.sdut.onlinejudge.model.StatKit;
import com.sdut.onlinejudge.model.SubmitUserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Devhui
 * @Date: 2019/12/23 19:59
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */
@Repository
public interface StatMapper {
    StatKit getStat();

    List<SubmitUserInfo> getall();

    // 获取反馈列表
    List<FeedBack> getFeedBacks();
}
