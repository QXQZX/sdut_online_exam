package com.sdut.onlinejudge.service.ServiceImpl;

import com.sdut.onlinejudge.mapper.AdminMapper;
import com.sdut.onlinejudge.mapper.ProblemMapper;
import com.sdut.onlinejudge.model.*;
import com.sdut.onlinejudge.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Devhui
 * @Date: 2019/12/23 18:30
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Autowired
    ProblemMapper problemMapper;

    @Override
    public Admin loginCheck(String username, String password) {
        return adminMapper.loginCheck(username, password);
    }

    @Override
    public int deleteUser(String username) {
        return adminMapper.deleteUser(username);
    }

    @Override
    public int resetPwd(String username) {
        return adminMapper.resetPwd(username);
    }

    @Override
    public int updateUserInfo(UserInfo userInfo) {
        return adminMapper.updateUserInfo(userInfo);
    }

    @Override
    public int addUser(UserInfo userInfo) {
        adminMapper.addUser(userInfo.getUsername());
        return adminMapper.addUserInfo(userInfo);
    }

    @Override
    public List<Admin> adminList() {
        return adminMapper.adminList();
    }

    @Override
    public List<Submit> getContestInfo(String cid) {
        return null;
    }

}
