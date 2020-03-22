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
    public Map<String, Object> fetchProblems(Map<String, String> contestInfo) {
        int singleCount = Integer.parseInt(contestInfo.get("singleCount"));
        System.out.println(singleCount);
        int judgeCount = Integer.parseInt(contestInfo.get("judgeCount"));
        int multiCount = Integer.parseInt(contestInfo.get("multiCount"));

        HashMap<String, Object> map = new HashMap<>();
        List<SingleSelect> singleSelects = problemMapper.fetchSingleSelects(singleCount);
        List<JudgeProblem> judgeProblems = problemMapper.fetchJudgeProblem(judgeCount);
        List<MultiSelect> multiSelects = problemMapper.fetchMultiSelects(multiCount);

        map.put("singleSelects", singleSelects);
        map.put("judgeProblems", judgeProblems);
        map.put("multiSelects", multiSelects);
        return map;
    }

    @Override
    public List<SingleSelect> getSingleSelects(String keyWords) {
        return problemMapper.getSingleSelects(keyWords);
    }

    @Override
    public List<MultiSelect> getMultiSelects(String keyWords) {
        return problemMapper.getMultiSelects(keyWords);
    }

    @Override
    public List<JudgeProblem> getJudgeProblem(String keyWords) {
        return problemMapper.getJudgeProblem(keyWords);
    }

    @Override
    public int addSingleSelects(SingleSelect ss) {
        return problemMapper.addSingleSelect(ss);
    }

    @Override
    public int addMultiSelects(MultiSelect ms) {
        return problemMapper.addMultiSelect(ms);
    }

    @Override
    public int addJudgeProblem(JudgeProblem jp) {
        return problemMapper.addJudgeProblem(jp);
    }
}
