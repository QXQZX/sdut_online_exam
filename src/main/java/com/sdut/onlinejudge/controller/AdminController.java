package com.sdut.onlinejudge.controller;

/**
 * @Author: Devhui
 * @Date: 2019-11-28 17:05
 * @Version 1.0
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin
@Controller
@RequestMapping("admin")
public class AdminController {

    @RequestMapping("login")
//    @ResponseBody
    public String login() {
        System.out.println("ssss");
        return "api/index";
    }
}
