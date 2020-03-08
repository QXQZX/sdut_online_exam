package com.sdut.onlinejudge.service;

import com.sdut.onlinejudge.model.*;

import java.util.List;

/**
 * @Author: Devhui
 * @Date: 2019/12/23 18:30
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */


public interface AdminService {
    // 管理员登陆检查
    Admin loginCheck(String username, String password);

    // 删除用户
    int deleteUser(String username);

    // 删除用户
    int resetPwd(String username);

    // 更新用户信息
    int updateUserInfo(UserInfo userInfo);

    // 添加用户
    int addUser(UserInfo userInfo);

    // 添加用户
    List<Admin> adminList();

    // 获取全部通知
    List<Notice> fetchNotices();

    // 发布通知
    int addNotice(Notice notice);

    // 删除通知
    int deleteNotice(int nid);
}
