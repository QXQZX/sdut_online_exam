package com.sdut.onlinejudge.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.sdut.onlinejudge.mapper.ContestMapper;
import com.sdut.onlinejudge.model.*;
import com.sdut.onlinejudge.service.ContestService;
import com.sdut.onlinejudge.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Devhui
 * @Date: 2019-11-28 17:05
 * @Version 1.0
 */
@Service
public class ContestServiceImpl implements ContestService {

    @Autowired
    private ContestMapper contestMapper;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<Contest> findAll(String keyWords) {
//        ValueOperations<String, String> ops = redisTemplate.opsForValue();
//        int hour = DateUtil.getHour(new Date());
//        int minute = DateUtil.getHour(new Date());
//        int seconds = DateUtil.getHour(new Date());
//
//        if (hour == 23 && minute == 59 && seconds == 59) {
//
//        } else {
//            if (redisTemplate.hasKey("pv")) {
//                ops.increment("pv");
//            } else {
//                ops.setIfAbsent("pv", "1");
//            }
//        }
        return contestMapper.findAll(keyWords);
    }

    @Override
    public int deployContest() {
        Map<String, Object> map = problemService.fetchProblems(); // 题目
        Answer answer = new Answer(); // 答案
        List<SingleSelect> singleSelects = (List<SingleSelect>) map.get("singleSelects");
        List<JudgeProblem> judgeProblems = (List<JudgeProblem>) map.get("judgeProblems");
        List<MultiSelect> multiSelects = (List<MultiSelect>) map.get("multiSelects");

        ArrayList<String> sans = new ArrayList<>();

        for (SingleSelect s : singleSelects) {
            sans.add(s.getAnswer());
            s.setAnswer(null);
        }
        ArrayList<String> mans = new ArrayList<>();
        for (MultiSelect s : multiSelects) {
            mans.add(s.getAnswer());
            s.setAnswer(null);
        }
        ArrayList<String> jans = new ArrayList<>();
        for (JudgeProblem s : judgeProblems) {
            jans.add(s.getAnswer());
            s.setAnswer(null);
        }
        map.put("singleSelects", singleSelects);
        map.put("judgeProblems", judgeProblems);
        map.put("multiSelects", multiSelects);

        answer.setSingleSelectsAns(sans);
        answer.setJudgeAns(jans);
        answer.setMultiSelectsAns(mans);

        Contest contest = new Contest();
        contest.setProblems(JSON.toJSONString(map));
        contest.setAnswers(JSON.toJSONString(answer));
        return contestMapper.deployContest(contest);
    }

    @Override
    public Map<String, Object> getContestByCid(int cid) {
        String ccid = "contest:" + cid;
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(ccid);
        if (hasKey) {
            String jsonString = ops.get(ccid);
            System.out.println("========从缓存中获取数据========");
            System.out.println("=============================");
            Map map = JSON.parseObject(jsonString, Map.class);
            return map;
        } else {
            String contest = contestMapper.getContestByCid(cid);
            System.out.println("========从数据库中获取数据========");
            System.out.println("=============================");
            ops.set(ccid, contest, 60, TimeUnit.MINUTES);
            Map map = JSON.parseObject(contest, Map.class);
            return map;
        }
    }

    @Override
    public Map<String, Object> getAnswerByCid(int cid) {
//        String ccid = "answer:" + cid;
//        ValueOperations<String, String> ops = redisTemplate.opsForValue();
//        boolean hasKey = redisTemplate.hasKey(ccid);
//        if (hasKey) {
//            String jsonString = ops.get(ccid);
//            System.out.println("========从缓存中获取数据========");
//            System.out.println("=============================");
//            Map map = JSON.parseObject(jsonString, Map.class);
//            return map;
//        } else {
        String answer = contestMapper.getAnswerByCid(cid);
        System.out.println("========从数据库中获取数据========");
        System.out.println("=============================");
//            ops.set(ccid, answer, 60, TimeUnit.MINUTES);
        Map map = JSON.parseObject(answer, Map.class);
        return map;
//        }
    }


}
