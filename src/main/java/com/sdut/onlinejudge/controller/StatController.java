package com.sdut.onlinejudge.controller;

import com.sdut.onlinejudge.model.*;
import com.sdut.onlinejudge.service.StatService;
import com.sdut.onlinejudge.utils.ResultCode;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Devhui
 * @Date: 2019/12/23 19:06
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */

@RestController
@RequestMapping("admin/stat")
@Api("数据统计接口")
public class StatController {

    @Autowired
    StatService statService;

    @GetMapping("main")
    @ResponseBody
    public ResultKit statMain() {
        ResultKit resultKit = new ResultKit<>();
        StatKit stat = statService.getStat();
        List<TrainStat> submitStat = statService.getSubmitStat(null);
        List<Object> resp = Arrays.asList(stat, submitStat);
        resultKit.setData(resp);
        if (stat != null) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("获取统计数据成功");
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("获取统计数据失败");
        }
        return resultKit;
    }

    @GetMapping("contestStat/{cid}")
    @ResponseBody
    public ResultKit getContestStat(@PathVariable("cid") Integer cid) {
        ResultKit<List> resultKit = new ResultKit<>();
        List<SubmitStat> contestStat = statService.getStatByCid(cid);
        resultKit.setData(contestStat);
        if (contestStat != null) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("获取测试统计数据成功");
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("获取测试统计数据失败");
        }
        return resultKit;
    }

    @GetMapping("noSubmitStat/{cid}")
    @ResponseBody
    public ResultKit noSubmitStat(@PathVariable("cid") Integer cid) {
        ResultKit<List> resultKit = new ResultKit<>();
        List<SubmitStat> contestStat = statService.noSubmitStat(cid);
        resultKit.setData(contestStat);
        if (contestStat != null) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("获取测试统计数据成功");
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("获取测试统计数据失败");
        }
        return resultKit;
    }


    @GetMapping("uSubmit")
    @ResponseBody
    public ResultKit uSubmitStat(@RequestParam("uid") String uid) {
        ResultKit resultKit = new ResultKit<>();
        List<TrainStat> submitStat = statService.getSubmitStat(uid);
        resultKit.setData(submitStat);
        if (submitStat != null) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("获取统计数据成功");
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("获取统计数据失败");
        }
        return resultKit;
    }


    @GetMapping("hintStat")
    public ResultKit problemHintStat() {
        ResultKit<Map> resultKit = new ResultKit<>();

        List<Map> singleHint = statService.getSingleHint();
        List<Map> multiHint = statService.getMultiHint();
        List<Map> judgeHint = statService.getJudgeHint();

        HashMap<String, List> map = new HashMap<>();
        map.put("singleHint", singleHint);
        map.put("multiHint", multiHint);
        map.put("judgeHint", judgeHint);

        if (singleHint != null && multiHint != null) {
            resultKit.setData(map);
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("获取题目统计数据成功");
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("获取题目统计数据失败");
        }
        return resultKit;
    }

    @GetMapping("fetchFeedbacks")
    @ResponseBody
    public ResultKit getFeedbacks() {
        ResultKit<List> resultKit = new ResultKit<>();
        List<FeedBack> feedBacks = statService.getFeedBacks();

        resultKit.setData(feedBacks);
        if (feedBacks != null) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("获取反馈数据成功");
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("获取反馈数据失败");
        }
        return resultKit;
    }
}
