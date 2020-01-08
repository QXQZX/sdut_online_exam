package com.sdut.onlinejudge.controller;

import com.sdut.onlinejudge.model.ResultKit;
import com.sdut.onlinejudge.model.StatKit;
import com.sdut.onlinejudge.service.StatService;
import com.sdut.onlinejudge.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Devhui
 * @Date: 2019/12/23 19:06
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */

@RestController
@RequestMapping("admin/stat")
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

    @GetMapping("getContestStat/{cid}")
    @ResponseBody
    public ResultKit getContestStat() {
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
}
