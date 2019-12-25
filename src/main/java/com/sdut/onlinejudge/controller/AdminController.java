package com.sdut.onlinejudge.controller;

/**
 * @Author: Devhui
 * @Date: 2019-11-28 17:05
 * @Version 1.0
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdut.onlinejudge.model.Admin;
import com.sdut.onlinejudge.model.ResultKit;
import com.sdut.onlinejudge.service.AdminService;
import com.sdut.onlinejudge.service.ContestService;
import com.sdut.onlinejudge.utils.JwtUtils;
import com.sdut.onlinejudge.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    ContestService contestService;

    @PostMapping("login")
    @ResponseBody
    public ResultKit loginCheck(@RequestBody String param, HttpServletRequest req) {
        JSONObject json = JSON.parseObject(param);
        String username = (String) json.get("username");
        String password = (String) json.get("password");

        ResultKit<Object> resultKit = new ResultKit<>();
        Admin admin = adminService.loginCheck(username, password);
        if (admin != null) {
            // 签发token
            String token = JwtUtils.createJWT(UUID.randomUUID().toString(), admin.getUsername(), 120 * 60 * 1000);
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("登录成功");
            resultKit.setData(token);
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("登录失败，账号或密码错误！");
        }
        return resultKit;
    }

    @GetMapping("deploy")
    @ResponseBody
    public ResultKit deployNewContest(@RequestBody String param) {
        ResultKit<Integer> resultKit = new ResultKit<>();
        int i = contestService.deployContest();
        resultKit.setData(i);
        resultKit.setCode(ResultCode.SUCCESS.code());
        resultKit.setMessage("新测试发布成功");
        return resultKit;
    }


    @PostMapping("updateUserInfo/{uid}")
    @ResponseBody
    public ResultKit updateUserInfo(@PathVariable("uid") String uid, @RequestBody String param) {

        return null;
    }

    @GetMapping("deleteUser/{uid}")
    @ResponseBody
    public ResultKit delUser(@PathVariable("uid") String uid) {
        ResultKit resultKit = new ResultKit();
        int i = adminService.deleteUser(uid);
        System.out.println("删除标志" + i);
        resultKit.setCode(ResultCode.WRONG_UP.code());
        resultKit.setMessage("删除用户失败！");
        if (i == 1) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("删除用户成功！");
        }
        return resultKit;
    }

}
