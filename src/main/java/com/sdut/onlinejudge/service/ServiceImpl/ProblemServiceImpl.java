package com.sdut.onlinejudge.service.ServiceImpl;

import com.sdut.onlinejudge.mapper.ProblemMapper;
import com.sdut.onlinejudge.model.JudgeProblem;
import com.sdut.onlinejudge.model.MultiSelect;
import com.sdut.onlinejudge.model.SingleSelect;
import com.sdut.onlinejudge.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Devhui
 * @Date: 2019-12-04 19:35
 * @Version 1.0
 */
@Service
public class ProblemServiceImpl implements ProblemService {
    @Autowired
    private ProblemMapper problemMapper;


    @Override
    public Map<String, Object> fetchProblems() {
        HashMap<String, Object> map = new HashMap<>();
        List<SingleSelect> singleSelects = problemMapper.fetchSingleSelects();
        List<JudgeProblem> judgeProblems = problemMapper.fetchJudgeProblem();
        List<MultiSelect> multiSelects = problemMapper.fetchMultiSelects();

        map.put("singleSelects", singleSelects);
        map.put("judgeProblems", judgeProblems);
        map.put("multiSelects", multiSelects);
        return map;
    }
}
