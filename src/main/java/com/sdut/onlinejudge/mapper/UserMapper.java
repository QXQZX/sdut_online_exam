package com.sdut.onlinejudge.mapper;

import com.sdut.onlinejudge.model.FeedBack;
import com.sdut.onlinejudge.model.User;
import com.sdut.onlinejudge.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Devhui
 * @Date: 2019-12-02 19:09
 * @Version 1.0
 */
@Repository
public interface UserMapper {
    // 登录检查
    User loginCheck(String username, String password);

    // 获取所有用户信息
    List<UserInfo> findAllUsers(@Param("Uname") String Uname, @Param("CollegeName") String CollegeName);

    // 注册
    int register(User user);

    // 获取单个用户信息
    UserInfo getUserInfo(String username);

    // 获取用户所有提交记录的cid
    List<Integer> getCidsByUsername(String username);

    // 修改密码
    int updatePwd(String username, String oldPwd, String newPwd);

    // 反馈bug 和 建议
    int addFeedBack(FeedBack feedBack);
}
