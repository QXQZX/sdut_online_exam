package com.sdut.onlinejudge.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdut.onlinejudge.model.Contest;
import com.sdut.onlinejudge.model.ResultKit;
import com.sdut.onlinejudge.model.Submit;
import com.sdut.onlinejudge.service.ContestService;
import com.sdut.onlinejudge.service.SubmitService;
import com.sdut.onlinejudge.utils.MainUtils;
import com.sdut.onlinejudge.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: Devhui
 * @Date: 2019-11-28 17:05
 * @Version 1.0
 */

@RestController
@RequestMapping("contest")
//@Api("测试信息接口")
public class ContestController {

    private Logger logger = LoggerFactory.getLogger(ContestController.class);

    @Autowired
    private ContestService contestService;

    @Autowired
    private SubmitService submitService;


    @GetMapping("all")
    public ResultKit getAllContest(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(value = "keyWords", required = false) String keyWords) {
        ResultKit<Map> resultKit = new ResultKit<>();

        String orderBy = "cid" + " desc";//按照（数据库）排序字段 倒序 排序
        PageHelper.startPage(pageNum, 15, orderBy);

        List<Contest> allContest = contestService.findAll(keyWords);
        // 将查询到的数据封装到PageInfo对象
        PageInfo<Contest> pageInfo = new PageInfo(allContest);
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
        Map<String, Float> problemScore = contestService.getProblemScore(cid);
        Map<String, Object> contestTime = contestService.getContestTime(cid);
        ResultKit<Object> resultKit = new ResultKit<>();
        if (contest != null && problemScore != null) {
            List<Map<String, ?>> maps = Arrays.asList(contest, problemScore, contestTime);
            resultKit.setData(maps);
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("获取试题成功");
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("获取试题失败");
        }
        return resultKit;
    }

    @PostMapping("submit/cid/{cid}/{uid}/")
    @ResponseBody
    public ResultKit submitContest(@PathVariable("uid") String uid,
                                   @PathVariable("cid") int cid,
                                   @RequestParam(value = "time", required = false) String date,
                                   @RequestBody String userAns) {
        Date submitTime = JSON.parseObject(date, Date.class);
        ResultKit<Float> resultKit = new ResultKit();
        if (submitService.hasSubmit(uid, cid) == null) {
            Map userAnsMap = JSON.parseObject(userAns, Map.class);

            Map<String, Object> answer = contestService.getAnswerByCid(cid); //答案信息
            Map<String, Float> problemScore = contestService.getProblemScore(cid); // 分数信息

            float score = MainUtils.judgeCore(userAnsMap, answer, problemScore);
            logger.info("用户UID={} 在测试 CID={} 的得分是 {}", uid, cid, score);

            Submit submit = new Submit();
            submit.setUid(uid);
            submit.setCid(cid);
            submit.setAnswers(userAns);
            submit.setScore(score);
            submit.setSubmitTime(submitTime);

            int i = submitService.addSubmit(submit);
            int addScore = submitService.addScore(score, uid);

            if (i == 1 && addScore == 1) {
                resultKit.setCode(ResultCode.SUCCESS.code());
                resultKit.setMessage("判题成功");
                resultKit.setData(score);
            } else {
                resultKit.setCode(ResultCode.WRONG_UP.code());
                resultKit.setMessage("判题失败, 请勿重复提交");
                resultKit.setData(-1f);
            }
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("已经提交, 请勿重复提交");
            resultKit.setData(-1f);
        }
        return resultKit;
    }

    @GetMapping("check/cid/{cid}/{uid}")
    @ResponseBody
    public ResultKit checkResult(@PathVariable("uid") String uid, @PathVariable("cid") int cid, HttpServletRequest req) {
        ResultKit<Map> resultKit = new ResultKit<>();
        Submit submit = submitService.getSubmit(uid, cid); // 获取用户提交信息
        if (submit != null) {
            Map<String, Object> answerByCid = contestService.getAnswerByCid(cid); // 获取答案
            Map<String, Object> contestByCid = contestService.getContestByCid(cid); // 获取测试题目信息
            Map uSubmit = JSON.parseObject(submit.getAnswers(), Map.class); // 获取用户提交的答案
            Map<String, Float> problemScore = contestService.getProblemScore(cid);

            System.out.println("submit" + submit);
            Map<String, Object> result = new HashMap<>();
            result.put("problems", contestByCid);
            result.put("answer", answerByCid);
            result.put("uSubmit", uSubmit);
            result.put("score", submit.getScore());
            result.put("rank", submit.getRank());
            result.put("problemScore", problemScore);
            resultKit.setMessage("获取用户提交和答案成功");
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setData(result);
        } else {
            resultKit.setMessage("未提交过");
            resultKit.setCode(ResultCode.WRONG_UP.code());
        }
        return resultKit;
    }

}
