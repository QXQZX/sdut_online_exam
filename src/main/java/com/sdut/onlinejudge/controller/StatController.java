package com.sdut.onlinejudge.controller;

import com.sdut.onlinejudge.model.FeedBack;
import com.sdut.onlinejudge.model.ResultKit;
import com.sdut.onlinejudge.model.StatKit;
import com.sdut.onlinejudge.model.SubmitStat;
import com.sdut.onlinejudge.service.StatService;
import com.sdut.onlinejudge.utils.ResultCode;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        ResultKit<StatKit> resultKit = new ResultKit<>();
        StatKit stat = statService.getStat();
        System.out.println(stat);
        resultKit.setData(stat);
        if (stat != null) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("获取统计数据成功！");
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("获取统计数据失败！");
        }
        return resultKit;
    }

    @GetMapping("contestStat/{cid}")
    @ResponseBody
    public ResultKit getContestStat(@PathVariable("cid") String cid) {
        ResultKit<List> resultKit = new ResultKit<>();
        List<SubmitStat> contestStat = statService.getStatByCid(cid);
        System.out.println(contestStat);
        resultKit.setData(contestStat);
        if (contestStat != null) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("获取测试统计数据成功！");
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("获取测试统计数据失败！");
        }
        return resultKit;
    }


    @GetMapping("hintStat")
    public ResultKit problemHintStat() {
        ResultKit<Map> resultKit = new ResultKit<>();

        List<Map> singleHint = statService.getSingleHint();
        List<Map> multiHint = statService.getMultiHint();

        System.out.println("singleHint = " + singleHint);
        System.out.println("multiHint = " + multiHint);

        HashMap<String, List> map = new HashMap<>();
        map.put("singleHint", singleHint);
        map.put("multiHint", multiHint);

        if (singleHint != null && multiHint != null) {
            resultKit.setData(map);
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("获取题目统计数据成功！");
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("获取题目统计数据失败！");
        }
        return resultKit;
    }

    @GetMapping("fetchFeedbacks")
    @ResponseBody
    public ResultKit getFeedbacks() {
        ResultKit<List> resultKit = new ResultKit<>();
        List<FeedBack> feedBacks = statService.getFeedBacks();
        System.out.println(feedBacks);
        resultKit.setData(feedBacks);
        if (feedBacks != null) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("获取反馈数据成功！");
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("获取反馈数据失败！");
        }
        return resultKit;
    }
}
