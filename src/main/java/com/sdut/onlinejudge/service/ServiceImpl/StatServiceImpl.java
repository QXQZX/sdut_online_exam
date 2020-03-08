package com.sdut.onlinejudge.service.ServiceImpl;

import com.sdut.onlinejudge.mapper.StatMapper;
import com.sdut.onlinejudge.model.FeedBack;
import com.sdut.onlinejudge.model.StatKit;
import com.sdut.onlinejudge.model.SubmitStat;
import com.sdut.onlinejudge.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Devhui
 * @Date: 2019/12/23 20:02
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */
@Service
public class StatServiceImpl implements StatService {
    @Autowired
    StatMapper statMapper;

    @Override
    public StatKit getStat() {
        return statMapper.getStat();
    }

    @Override
    public List<SubmitStat> getStatByCid(String cid) {
        return statMapper.getStatByCid(cid);
    }

    @Override
    public List<FeedBack> getFeedBacks() {
        return statMapper.getFeedBacks();
    }
}
