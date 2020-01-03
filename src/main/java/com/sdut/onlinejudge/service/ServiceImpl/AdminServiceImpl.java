package com.sdut.onlinejudge.service.ServiceImpl;

import com.sdut.onlinejudge.mapper.AdminMapper;
import com.sdut.onlinejudge.model.*;
import com.sdut.onlinejudge.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Devhui
 * @Date: 2019/12/23 18:30
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin loginCheck(String username, String password) {
        return adminMapper.loginCheck(username, password);
    }

    @Override
    public int deleteUser(String username) {
        return adminMapper.deleteUser(username);
    }

    @Override
    public int updateUserInfo(UserInfo userInfo) {
        return 0;
    }

    @Override
    public int addJudgeProblem(JudgeProblem judgeProblem) {
        return 0;
    }

    @Override
    public int addSingleSelect(SingleSelect select) {
        return 0;
    }

    @Override
    public int addMultiSelect(MultiSelect select) {
        return 0;
    }

    @Override
    public List<Submit> getContestInfo(String cid) {
        return null;
    }

}
