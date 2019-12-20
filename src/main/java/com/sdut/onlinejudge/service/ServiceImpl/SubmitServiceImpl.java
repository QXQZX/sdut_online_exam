package com.sdut.onlinejudge.service.ServiceImpl;

import com.sdut.onlinejudge.mapper.SubmitMapper;
import com.sdut.onlinejudge.model.Submit;
import com.sdut.onlinejudge.service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Devhui
 * @Date: 2019-12-07 16:42
 * @Version 1.0
 */
@Service
@Transactional
public class SubmitServiceImpl implements SubmitService {
    @Autowired
    private SubmitMapper submitMapper;

    @Override
    public Submit getSubmit(String uid, int cid) {
        return submitMapper.getSubmit(uid, cid);
    }

    @Override
    public Submit hasSubmit(String uid, int cid) {
        return submitMapper.hasSubmit(uid, cid);
    }

    @Override
    public int addSubmit(Submit submit) {
        return submitMapper.addSubmit(submit);
    }

    @Override
    public int addScore(int score, String username) {
        return submitMapper.addScore(score, username);
    }
}
