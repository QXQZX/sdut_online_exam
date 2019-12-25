package com.sdut.onlinejudge.service.ServiceImpl;

import com.sdut.onlinejudge.mapper.UserMapper;
import com.sdut.onlinejudge.model.FeedBack;
import com.sdut.onlinejudge.model.User;
import com.sdut.onlinejudge.model.UserInfo;
import com.sdut.onlinejudge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Devhui
 * @Date: 2019-12-02 19:16
 * @Version 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User loginCheck(String username, String password) {
        return userMapper.loginCheck(username, password);
    }

    @Override
    public List<UserInfo> findAllUsers(String Uname, String CollegeName) {
        return userMapper.findAllUsers(Uname, CollegeName);
    }

    @Override
    public int register(User user) {
        return userMapper.register(user);
    }

    @Override
    public UserInfo getUserInfo(String username) {
        return userMapper.getUserInfo(username);
    }

    @Override
    public List<Integer> getCidsByUsername(String username) {
        return userMapper.getCidsByUsername(username);
    }

    @Override
    public int updatePwd(String username, String oldPwd, String newPwd) {
        return userMapper.updatePwd(username, oldPwd, newPwd);
    }

    @Override
    public int addFeedBack(FeedBack feedBack) {
        return userMapper.addFeedBack(feedBack);
    }


}
