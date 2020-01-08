package com.sdut.onlinejudge.controller;

/**
 * @Author: Devhui
 * @Date: 2019-11-28 17:05
 * @Version 1.0
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdut.onlinejudge.model.*;
import com.sdut.onlinejudge.service.AdminService;
import com.sdut.onlinejudge.service.ContestService;
import com.sdut.onlinejudge.service.ProblemService;
import com.sdut.onlinejudge.service.UserService;
import com.sdut.onlinejudge.utils.JwtUtils;
import com.sdut.onlinejudge.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    ContestService contestService;

    @Autowired
    UserService userService;

    @Autowired
    ProblemService problemService;

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

    @GetMapping("getAdmins")
    @ResponseBody
    public ResultKit getAdmin() {
        ResultKit<List> resultKit = new ResultKit();
        List<Admin> admins = adminService.adminList();
        resultKit.setCode(ResultCode.WRONG_UP.code());
        resultKit.setData(admins);
        resultKit.setMessage("获取信息失败！");
        if (admins != null) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("获取信息成功！");
        }
        return resultKit;
    }

    @GetMapping("fetchProblems")
    @ResponseBody
    public ResultKit getProblems(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(value = "type", defaultValue = "single") String type,
                                 @RequestParam(value = "keyWords", required = false) String keyWords) {
        ResultKit<Map> resultKit = new ResultKit<>();
//        String orderBy = "score" + " desc";//按照（数据库）排序字段 倒序 排序
        PageHelper.startPage(pageNum, 10);
        System.out.println("type=" + type + " keyWords=" + keyWords);
        List list = null;
        long total = 0;
        Map<String, Object> map = new HashMap<>();
        if (type.equals("single")) {
            list = problemService.getSingleSelects(keyWords);
            PageInfo<SingleSelect> pageInfo = new PageInfo(list, 10);
            total = pageInfo.getTotal();

        } else if (type.equals("judge")) {
            list = problemService.getJudgeProblem(keyWords);
            PageInfo<JudgeProblem> pageInfo = new PageInfo(list, 10);
            total = pageInfo.getTotal();

        } else if (type.equals("multi")) {
            list = problemService.getMultiSelects(keyWords);
            PageInfo<MultiSelect> pageInfo = new PageInfo(list, 10);
            total = pageInfo.getTotal();
        }
        map.put("total", total);
        map.put("pageInfo", list);
        resultKit.setCode(ResultCode.SUCCESS.code());
        resultKit.setMessage("获取成功");
        resultKit.setData(map);
        return resultKit;
    }

    @PostMapping("deploy")
    @ResponseBody
    public ResultKit deployNewContest(@RequestBody String param) {
        ResultKit<Integer> resultKit = new ResultKit<>();
        int i = contestService.deployContest();
        resultKit.setData(i);
        resultKit.setCode(ResultCode.SUCCESS.code());
        resultKit.setMessage("新测试发布成功");
        return resultKit;
    }


    @PostMapping("updateUserInfo")
    @ResponseBody
    public ResultKit updateUserInfo(@RequestBody UserInfo userInfo) {
        ResultKit resultKit = new ResultKit();
        int i = adminService.updateUserInfo(userInfo);
        resultKit.setCode(ResultCode.WRONG_UP.code());
        System.out.println("信息修改" + i);
        resultKit.setMessage("信息修改失败！");
        if (i == 1) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("信息修改成功！");
        }
        return resultKit;
    }

    @GetMapping("resetPwd/{uid}")
    @ResponseBody
    public ResultKit resetPwd(@PathVariable("uid") String uid) {
        ResultKit resultKit = new ResultKit();
        int i = adminService.resetPwd(uid);
        resultKit.setCode(ResultCode.WRONG_UP.code());
        resultKit.setMessage("重置密码失败！");
        if (i == 1) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("重置密码成功！");
        }
        return resultKit;
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

    @PostMapping("addUser")
    @ResponseBody
    public ResultKit addUser(@RequestBody UserInfo userInfo) {
        ResultKit resultKit = new ResultKit();
        int i = adminService.addUser(userInfo);
        System.out.println("插入标志" + i);
        resultKit.setCode(ResultCode.WRONG_UP.code());
        resultKit.setMessage("添加用户失败！");
        if (i == 1) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("添加用户成功！");
        }
        return resultKit;
    }

    @GetMapping("getUsers")
    public ResultKit getAllUser() {
        ResultKit<List> resultKit = new ResultKit<>();
        List<UserInfo> allUsers = userService.findAllUsers(null, null);
        resultKit.setCode(ResultCode.SUCCESS.code());
        resultKit.setMessage("获取成功");
        resultKit.setData(allUsers);
        return resultKit;
    }


    @PostMapping("addProblem")
    @ResponseBody
    public ResultKit addJudgeProblem(@RequestParam(value = "type", defaultValue = "single") String type,
                                     @RequestBody String param) {
        if (type.equals("single")) {
            SingleSelect singleSelect = JSONObject.parseObject(param, SingleSelect.class);
            System.out.println(singleSelect);
        } else if (type.equals("judge")) {


        } else if (type.equals("multi")) {

        }
        return null;
    }

}
