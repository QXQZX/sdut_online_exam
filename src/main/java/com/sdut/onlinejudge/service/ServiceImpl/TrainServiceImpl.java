package com.sdut.onlinejudge.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.sdut.onlinejudge.mapper.ProblemMapper;
import com.sdut.onlinejudge.mapper.SubmitMapper;
import com.sdut.onlinejudge.mapper.TrainMapper;
import com.sdut.onlinejudge.model.*;
import com.sdut.onlinejudge.service.ProblemService;
import com.sdut.onlinejudge.service.TrainService;
import com.sdut.onlinejudge.utils.DateUtil;
import com.sdut.onlinejudge.utils.ProblemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Devhui
 * @Date: 2020/4/8 17:36
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */

@Service
@Transactional(readOnly = true)
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TrainMapper trainMapper;

    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private SubmitMapper submitMapper;

    @Autowired
    private ProblemService problemService;

    @Override
    public List<Train> findAll(String uid) {
        return trainMapper.findAll(uid);
    }

    @Override
    public Map<String, Object> getTrain(Integer tid) {
        String train = trainMapper.getTrain(tid);
        return JSON.parseObject(train, Map.class);
    }

    @Override
    public Map<String, Object> getAnswer(Integer tid) {
        String answer = trainMapper.getAnswer(tid);
        return JSON.parseObject(answer, Map.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public int deployTrain(String uid) {
        HashMap<String, String> count = new HashMap<>();
        count.put("singleCount", String.valueOf(ProblemConstant.trainSingleCount));
        count.put("judgeCount", String.valueOf(ProblemConstant.trainJudgeCount));
        count.put("multiCount", String.valueOf(ProblemConstant.trainMultiCount));
        Map<String, Object> problems = problemService.fetchProblems(count);

        Train train = separate(problems);
        train.setUid(uid);
        return trainMapper.deployTrain(train);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public int trainSubmit(Train train) {
        int addScore = submitMapper.addScore(train.getScore(), train.getUid());
        int submit = trainMapper.trainSubmit(train);
        return submit + addScore;
    }

    @Override
    public Map<String, String> checkSubmit(Integer tid) {
        Map<String, String> submit = trainMapper.checkSubmit(tid);
        return submit;
    }

    @Override
    public List<Map> getTopics() {
        return trainMapper.getTopics();
    }

    @Override
    public List getTopicProblem(String type, String label) {
        if (type.equals("single")) {
            return problemMapper.fetchSingleSelects(1, label);
        } else if (type.equals("multi")) {
            return problemMapper.fetchMultiSelects(1, label);
        } else if (type.equals("judge")) {
            return problemMapper.fetchJudgeProblem(1, label);
        }
        return null;
    }

    private Train separate(Map map) {
        System.out.println(map);
        Answer answer = new Answer(); // 答案
        answer.setSingleSelectsAns(null);
        answer.setJudgeAns(null);
        answer.setMultiSelectsAns(null);


        List<SingleSelect> singleSelects = (List<SingleSelect>) map.get("singleSelects");
        List<JudgeProblem> judgeProblems = (List<JudgeProblem>) map.get("judgeProblems");
        List<MultiSelect> multiSelects = (List<MultiSelect>) map.get("multiSelects");

        // 题目答案分离
        ArrayList<String> sans = new ArrayList<>();
        if (singleSelects != null) {
            for (SingleSelect s : singleSelects) {
                sans.add(s.getAnswer());
                s.setAnswer(null);
            }
            map.put("singleSelects", singleSelects);
            answer.setSingleSelectsAns(sans);
        }
        ArrayList<String> mans = new ArrayList<>();
        if (multiSelects != null) {
            for (MultiSelect s : multiSelects) {
                mans.add(s.getAnswer());
                s.setAnswer(null);
            }
            map.put("multiSelects", multiSelects);
            answer.setMultiSelectsAns(mans);
        }
        ArrayList<String> jans = new ArrayList<>();
        if (judgeProblems != null) {
            for (JudgeProblem s : judgeProblems) {
                jans.add(s.getAnswer());
                s.setAnswer(null);
            }
            map.put("judgeProblems", judgeProblems);
            answer.setJudgeAns(jans);
        }


        Train train = new Train();
        train.setTname(DateUtil.getTimeString());
        train.setProblems(JSON.toJSONString(map));
        train.setAnswers(JSON.toJSONString(answer));
        return train;
    }
}
