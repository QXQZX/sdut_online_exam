package com.sdut.onlinejudge.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdut.onlinejudge.model.Contest;
import com.sdut.onlinejudge.model.ResultKit;
import com.sdut.onlinejudge.model.Submit;
import com.sdut.onlinejudge.service.ContestService;
import com.sdut.onlinejudge.service.SubmitService;
import com.sdut.onlinejudge.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Devhui
 * @Date: 2019-11-28 17:05
 * @Version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("contest")
public class ContestController {
    @Autowired
    private ContestService contestService;

    @Autowired
    private SubmitService submitService;


    @GetMapping("all")
    @ResponseBody
    public ResultKit getAllContest(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(value = "keyWords", required = false) String keyWords) {
        ResultKit<Map> resultKit = new ResultKit<>();
        // pageNum:表示第几页  pageSize:表示一页展示的数据
        String orderBy = "cid" + " desc";//按照（数据库）排序字段 倒序 排序
        PageHelper.startPage(pageNum, 2, orderBy);

        List<Contest> allContest = contestService.findAll(keyWords);
        // 将查询到的数据封装到PageInfo对象
        PageInfo<Contest> pageInfo = new PageInfo(allContest, 2);
        // 分割数据成功

        long total = pageInfo.getTotal();

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("pageInfo", allContest);

        resultKit.setData(map);
        resultKit.setCode(ResultCode.SUCCESS.code());
        resultKit.setMessage("获取全部比赛信息成功");
        return resultKit;
    }

    @GetMapping("cid/{cid}")
    @ResponseBody
    public ResultKit getContest(@PathVariable("cid") int cid, HttpServletRequest req) {
        Map<String, Object> contest = contestService.getContestByCid(cid);
        ResultKit<Map> resultKit = new ResultKit<>();
        resultKit.setData(contest);
        resultKit.setCode(ResultCode.SUCCESS.code());
        resultKit.setMessage("获取试题成功");
        return resultKit;
    }

    @PostMapping("submit/cid/{cid}/{uid}/")
    @ResponseBody
    public ResultKit submitContest(@PathVariable("uid") String uid,
                                   @PathVariable("cid") int cid,
                                   @RequestParam(value = "time", required = false) String date,
                                   @RequestBody String userAns) {
        System.out.println(date);
        Date submitTime = JSON.parseObject(date, Date.class);
        ResultKit<Integer> resultKit = new ResultKit();
        if (submitService.hasSubmit(uid, cid) == null) {
            Map userAnsMap = JSON.parseObject(userAns, Map.class);
            Map<String, Object> answer = contestService.getAnswerByCid(cid);
            int score = judgeCore(userAnsMap, answer);
            System.out.println("======得分====" + score);

            Submit submit = new Submit();
            submit.setUid(uid);
            submit.setCid(cid);
            submit.setAnswers(userAns);
            submit.setScore(score);
            submit.setSubmitTime(submitTime);

            int i = submitService.addSubmit(submit);
            int addScore = submitService.addScore(score, uid);
            System.out.println(i);

            if (i == 1 && addScore == 1) {
                resultKit.setCode(ResultCode.SUCCESS.code());
                resultKit.setMessage("判题成功");
                resultKit.setData(score);
            } else {
                resultKit.setCode(ResultCode.WRONG_UP.code());
                resultKit.setMessage("判题失败, 请勿重复提交");
                resultKit.setData(-1);
            }
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("已经提交, 请勿重复提交");
            resultKit.setData(-1);
        }
        return resultKit;
    }

    @GetMapping("check/cid/{cid}/{uid}")
    @ResponseBody
    public ResultKit checkResult(@PathVariable("uid") String uid, @PathVariable("cid") int cid, HttpServletRequest req) {
        Submit submit = submitService.getSubmit(uid, cid); // 获取用户提交信息
        Map<String, Object> answerByCid = contestService.getAnswerByCid(cid); // 获取答案
        Map<String, Object> contestByCid = contestService.getContestByCid(cid); // 获取测试题目信息
        Map uSubmit = JSON.parseObject(submit.getAnswers(), Map.class); // 获取用户提交的答案

        Map<String, Object> result = new HashMap<>();
        result.put("problems", contestByCid);
        result.put("answer", answerByCid);
        result.put("uSubmit", uSubmit);
        result.put("score", submit.getScore());

        ResultKit<Map> resultKit = new ResultKit<>();
        resultKit.setMessage("获取用户提交和答案成功。");
        resultKit.setCode(ResultCode.SUCCESS.code());
        resultKit.setData(result);
        return resultKit;
    }

    private int judgeCore(Map uAnswer, Map answer) {
        int socre = 0;
        List<String> judgeProblems = (List<String>) uAnswer.get("judgeProblems"); // 用户判断题答案
        List<String> jAns = (List<String>) answer.get("judgeAns"); //数据库判断题答案
        for (int i = 0; i < judgeProblems.size(); i++) {
            String uAns = judgeProblems.get(i);
            String tAns = jAns.get(i);
            if (uAns.equals(tAns)) {
                System.out.println(uAns.equals(tAns));
                socre += 1;
            }
        }
        List<String> multiSelects = (List<String>) uAnswer.get("multiSelects");
        List<String> mAns = (List<String>) answer.get("multiSelectsAns"); //数据库多选题答案
        for (int i = 0; i < multiSelects.size(); i++) {
            String uAns = multiSelects.get(i);
            String tAns = mAns.get(i);
            if (uAns.equals(tAns)) {
                System.out.println(uAns.equals(tAns));
                socre += 1;
            }
        }
        List<String> singleSelects = (List<String>) uAnswer.get("singleSelects");
        List<String> sAns = (List<String>) answer.get("singleSelectsAns"); //数据库单选题答案
        for (int i = 0; i < singleSelects.size(); i++) {
            String uAns = singleSelects.get(i);
            String tAns = sAns.get(i);
            if (uAns.equals(tAns)) {
                System.out.println(uAns.equals(tAns));
                socre += 1;
            }
        }

        return socre;
    }

}
