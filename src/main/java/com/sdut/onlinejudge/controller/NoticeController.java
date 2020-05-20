package com.sdut.onlinejudge.controller;

import com.sdut.onlinejudge.model.Notice;
import com.sdut.onlinejudge.model.ResultKit;
import com.sdut.onlinejudge.service.AdminService;
import com.sdut.onlinejudge.utils.ResultCode;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Devhui
 * @Date: 2020/2/6 19:31
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */

@RestController
@RequestMapping("admin/notice")
@Api("通知信息接口")
public class NoticeController {

    @Autowired
    AdminService adminService;

    @GetMapping("all")
    @ResponseBody
    public ResultKit getAllNoice() {
        ResultKit<List> resultKit = new ResultKit();
        List<Notice> notices = adminService.fetchNotices();
        resultKit.setCode(ResultCode.WRONG_UP.code());
        resultKit.setMessage("获取全部通知成功");
        if (notices != null) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("获取全部通知失败");
            resultKit.setData(notices);
        }
        return resultKit;
    }


    @PostMapping("addNotice")
    @ResponseBody
    public ResultKit addNoice(@RequestBody Notice notice) {
        ResultKit resultKit = new ResultKit();

        int i = adminService.addNotice(notice);

        resultKit.setCode(ResultCode.WRONG_UP.code());
        resultKit.setMessage("添加通知失败");
        if (i == 1) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("添加通知成功");
        }
        return resultKit;
    }

    @GetMapping("deleteNotice/{nid}")
    @ResponseBody
    public ResultKit deleteNoice(@PathVariable("nid") int nid) {
        ResultKit resultKit = new ResultKit();
        int i = adminService.deleteNotice(nid);
        System.out.println("删除标志" + i);
        resultKit.setCode(ResultCode.WRONG_UP.code());
        resultKit.setMessage("删除通知失败！");
        if (i == 1) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("删除通知成功！");
        }
        return resultKit;
    }

    @GetMapping("updateNotice")
    @ResponseBody
    public ResultKit updateNoice(@RequestParam(value = "nid") Integer nid,
                                 @RequestParam(value = "status") Integer status) {
        ResultKit resultKit = new ResultKit();
        int i = adminService.updateNotice(nid, status);
        resultKit.setCode(ResultCode.WRONG_UP.code());
        if (i == 1) {
            resultKit.setCode(ResultCode.SUCCESS.code());
        }
        return resultKit;
    }

}
