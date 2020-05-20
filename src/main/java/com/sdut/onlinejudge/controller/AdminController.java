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
import com.sdut.onlinejudge.utils.DateUtil;
import com.sdut.onlinejudge.utils.ExcelUtils;
import com.sdut.onlinejudge.utils.JwtUtils;
import com.sdut.onlinejudge.utils.ResultCode;
import io.swagger.annotations.Api;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("admin")
@Api("管理员端接口")
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
        PageHelper.startPage(pageNum, 10);
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

    @GetMapping("deleteProblem")
    @ResponseBody
    public ResultKit delProblem(@RequestParam(value = "type", defaultValue = "single") String type,
                                @RequestParam(value = "id") String id) {
        ResultKit resultKit = new ResultKit();

        int flag = 0;
        if (type.equals("single")) {
            flag = problemService.delSingleSelect(id);
        } else if (type.equals("judge")) {
            flag = problemService.delJudgeProblem(id);
        } else if (type.equals("multi")) {
            flag = problemService.delMultiSelect(id);
        }
        resultKit.setCode(ResultCode.WRONG_UP.code());
        resultKit.setMessage("删除失败");
        if (flag == 1) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("删除成功");
        }
        return resultKit;
    }


    @GetMapping("deleteContest/{cid}")
    @ResponseBody
    public ResultKit delContest(@PathVariable(value = "cid") int cid) {
        ResultKit resultKit = new ResultKit();

        int flag = contestService.deleteContest(cid);
        resultKit.setCode(ResultCode.WRONG_UP.code());
        resultKit.setMessage("删除失败");
        if (flag == 1) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("删除成功");
        }
        return resultKit;
    }

    @PostMapping("deploy")
    @ResponseBody
    public ResultKit deployNewContest(@RequestBody Map<String, String> contestInfo) {
        ResultKit<Integer> resultKit = new ResultKit<>();

        int i = contestService.deployContest(contestInfo);
        if (i == 1) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("新测试发布成功");
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("新测试发布失败");
        }
        return resultKit;
    }

    @PostMapping("updateContest")
    @ResponseBody
    public ResultKit updateContest(@RequestBody Map<String, String> contestInfo) {
        ResultKit<Integer> resultKit = new ResultKit<>();
        Contest contest = new Contest();
        contest.setCname(contestInfo.get("cname"));
        contest.setCid(contestInfo.get("cid"));
        try {
            contest.setStartTime(DateUtil.dateFormat(contestInfo.get("startTime")));
            contest.setEndTime(DateUtil.dateFormat(contestInfo.get("endTime")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int i = contestService.updateContest(contest);
        if (i == 1) {
            resultKit.setCode(ResultCode.SUCCESS.code());
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
        }
        return resultKit;
    }


    @PostMapping("deploySelf")
    @ResponseBody
    public ResultKit deployNewContest1(@RequestBody Map<String, Object> contestInfo) {
        ResultKit<Integer> resultKit = new ResultKit<>();

        int i = contestService.deployContestSelf(contestInfo);

        if (i == 1) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("新测试发布成功");
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("新测试发布失败");
        }
        return resultKit;
    }


    @PostMapping("updateUserInfo")
    @ResponseBody
    public ResultKit updateUserInfo(@RequestBody UserInfo userInfo) {
        ResultKit resultKit = new ResultKit();
        int i = adminService.updateUserInfo(userInfo);
        resultKit.setCode(ResultCode.WRONG_UP.code());

        resultKit.setMessage("信息修改失败");
        if (i == 1) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("信息修改成功");
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

        resultKit.setCode(ResultCode.WRONG_UP.code());
        resultKit.setMessage("删除用户失败");
        if (i == 1) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("删除用户成功");
        }
        return resultKit;
    }

    @PostMapping("addUser")
    @ResponseBody
    public ResultKit addUser(@RequestBody UserInfo userInfo) {
        ResultKit resultKit = new ResultKit();
        int i = adminService.addUser(userInfo);
        resultKit.setCode(ResultCode.WRONG_UP.code());
        resultKit.setMessage("添加用户失败");
        if (i == 1) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("添加用户成功");
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
        ResultKit resultKit = new ResultKit();
        resultKit.setMessage("添加失败");
        resultKit.setCode(ResultCode.WRONG_UP.code());
        int i = 0;
        if (type.equals("single")) {
            SingleSelect singleSelect = JSONObject.parseObject(param, SingleSelect.class);
            i = problemService.addSingleSelects(singleSelect);
        } else if (type.equals("judge")) {
            JudgeProblem judgeProblem = JSONObject.parseObject(param, JudgeProblem.class);
            i = problemService.addJudgeProblem(judgeProblem);
        } else if (type.equals("multi")) {
            MultiSelect multiSelect = JSONObject.parseObject(param, MultiSelect.class);
            i = problemService.addMultiSelects(multiSelect);
        }

        if (i == 1) {
            resultKit.setMessage("添加成功");
            resultKit.setCode(ResultCode.SUCCESS.code());
        }
        return resultKit;
    }

    @GetMapping("import")
    @ResponseBody
    public ResultKit importProblemData(@RequestParam(value = "type", defaultValue = "single") String type) {
        File file = new File("C:\\Users\\Devhui\\Documents\\Tencent Files\\501966782\\FileRecv\\辅导员题目\\多选题.xlsx");
        try {
            XSSFWorkbook wb = new XSSFWorkbook(file);
            int sheets = wb.getNumberOfSheets();
            //只读取第一个sheet
            XSSFSheet sheetAt = wb.getSheetAt(0);
            //这个表示当前sheet有多少行数据,一行一行读取就行,但是会把没有数据的行读出来,需要加异常处理
            int rows = sheetAt.getPhysicalNumberOfRows();
            System.out.println(rows);
            for (int i = 0; i < rows; i++) {
                if (i == 0) {
                    continue;
                }

                //某一行的数据,是一行一行的读取
                XSSFRow row = sheetAt.getRow(i);
                if (ExcelUtils.isRowEmpty(row) || row == null) {
                    continue;
                }


                for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    cell.setCellType(CellType.STRING);
                }

//                importSs(row);
//                importJp(row);
                importMs(row);
            }
        } catch (
                InvalidFormatException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (
                RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 批量插入判断题
    private void importJp(Row row) {
        // 创建单选题对象
//        JudgeProblem judgeProblem = new JudgeProblem(
//                row.getCell(0).getStringCellValue(),
//                row.getCell(1).getStringCellValue(),
//                row.getCell(2).getStringCellValue(),
//                row.getCell(3).getStringCellValue(),
//                row.getCell(4).getStringCellValue()
//        );
//        int i1 = problemService.addJudgeProblem(judgeProblem);
//        System.out.println(i1);
    }

    // 批量插入单选题
    private void importSs(Row row) {
//        SingleSelect ss = new SingleSelect(
//                row.getCell(0).getStringCellValue(),
//                row.getCell(1).getStringCellValue(),
//                row.getCell(2).getStringCellValue(),
//                row.getCell(3).getStringCellValue(),
//                row.getCell(4).getStringCellValue(),
//                row.getCell(5).getStringCellValue(),
//                row.getCell(6).getStringCellValue(),
//                row.getCell(7).getStringCellValue(),
//                row.getCell(8).getStringCellValue()
//        );
//        int i1 = problemService.addSingleSelects(ss);
    }

    // 批量插入多选题
    private void importMs(Row row) {
//        String answers = row.getCell(6).getStringCellValue();
//
//        if (answers.contains(",")) {
//        } else {
//            char[] chars = answers.toCharArray();
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < chars.length; i++) {
//                sb.append(chars[i]);
//                if (i != chars.length - 1)
//                    sb.append(",");
//            }
//            answers = sb.toString();
//        }
//        MultiSelect ss = new MultiSelect(
//                row.getCell(0).getStringCellValue(),
//                row.getCell(1).getStringCellValue(),
//                row.getCell(2).getStringCellValue(),
//                row.getCell(3).getStringCellValue(),
//                row.getCell(4).getStringCellValue(),
//                row.getCell(5).getStringCellValue(),
//                answers,
//                row.getCell(7).getStringCellValue(),
//                row.getCell(8).getStringCellValue()
//        );
////        int i1 = problemService.addMultiSelects(ss);
//        System.out.println(answers);
    }
}
