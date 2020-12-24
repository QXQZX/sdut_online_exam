package com.sdut.onlinejudge.service.ServiceImpl;

import com.sdut.onlinejudge.mapper.AdminMapper;
import com.sdut.onlinejudge.mapper.NoticeMapper;
import com.sdut.onlinejudge.mapper.ProblemMapper;
import com.sdut.onlinejudge.model.Admin;
import com.sdut.onlinejudge.model.Notice;
import com.sdut.onlinejudge.model.UserInfo;
import com.sdut.onlinejudge.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Devhui
 * @Date: 2019/12/23 18:30
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */

@Service
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public Admin loginCheck(String username, String password) {
        return adminMapper.loginCheck(username, password);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public int deleteUser(String username) {
        return adminMapper.deleteUser(username);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public int resetPwd(String username) {
        return adminMapper.resetPwd(username);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public int updateUserInfo(UserInfo userInfo) {
        return adminMapper.updateUserInfo(userInfo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public int addUser(UserInfo userInfo) {
        adminMapper.addUser(userInfo.getUsername());
        return adminMapper.addUserInfo(userInfo);
    }

    @Override
    public List<Admin> adminList() {
        return adminMapper.adminList();
    }

    @Override
    public List<Notice> fetchNotices() {
        return noticeMapper.fetchNotices();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public int addNotice(Notice notice) {
        return noticeMapper.addNotice(notice);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public int deleteNotice(int nid) {
        return noticeMapper.deleteNotice(nid);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public int updateNotice(int nid, int status) {
        return noticeMapper.updateNotice(nid, status);
    }


}
