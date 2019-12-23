package com.sdut.onlinejudge.controller;

import com.sdut.onlinejudge.model.ResultKit;
import com.sdut.onlinejudge.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
        statService.getStat();
        return null;
    }
}
