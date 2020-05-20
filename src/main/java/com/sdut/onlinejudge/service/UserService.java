package com.sdut.onlinejudge.service;

import com.sdut.onlinejudge.model.*;

import java.util.List;

/**
 * @Author: Devhui
 * @Date: 2019-12-02 19:15
 * @Version 1.0
 */
public interface UserService {
    // 用户登录检查
    User loginCheck(String username, String password);

    // 获取所有用户信息
    List<UserInfo> findAllUsers(String Uname, String CollegeName);

    // 获取练习统计
    List<TrainStat> getTrainStat(String uid);

    // 注册
    int register(User user);

    // 通过用户名获取单个用户信息
    UserInfo getUserInfo(String username);

    // 获取用户所有提交记录的cid
    List<Integer> getCidsByUsername(String username);

    // 修改密码
    int updatePwd(String username, String oldPwd, String newPwd);

    // 反馈bug 和 建议
    int addFeedBack(FeedBack feedBack);

    // 获取所有通知
    List<Notice> fetchNotices();
}
