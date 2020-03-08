package com.sdut.onlinejudge.mapper;

/**
 * @Author: Devhui
 * @Date: 2019-11-28 17:05
 * @Version 1.0
 */

import com.sdut.onlinejudge.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper {
    // 登陆检查
    Admin loginCheck(String username, String password);

    // 更新用户信息
    int resetPwd(String username);

    // 更新用户信息
    int updateUserInfo(UserInfo info);

    // 删除用户
    int deleteUser(String username);

    // 添加用户
    int addUser(String username);

    // 添加用户
    int addUserInfo(UserInfo info);

    // 添加用户
    List<Admin> adminList();

}
