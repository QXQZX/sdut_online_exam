package com.sdut.onlinejudge.utils;

import com.sdut.onlinejudge.model.CheckResult;
import com.sdut.onlinejudge.model.ResultKit;

import java.util.List;
import java.util.Map;

/**
 * @ author Devhui
 * @ Description:Token验证
 */
public class MainUtils {

    public static ResultKit checkToken(String token) {
        //进行验证
        if (token != null) {
            CheckResult result = JwtUtils.validateJWT(token);
            if (result.isSuccess()) {
                ResultKit<CheckResult> resultKit = new ResultKit<>();
                resultKit.setCode(ResultCode.SUCCESS.code());
                resultKit.setMessage("成功获取信息");
                resultKit.setData(result);
                return resultKit;
            } else {
                ResultKit<String> resultKit = new ResultKit<>();
                resultKit.setCode(ResultCode.UNAUTHORIZED.code());
                resultKit.setMessage("错误:Token验证失败,或许是授权过期,或许是Token错误");
                resultKit.setData("没有权限进行操作");
                return resultKit;
            }

        } else {
            ResultKit<String> resultKit = new ResultKit<>();
            resultKit.setCode(ResultCode.FAIL.code());
            resultKit.setMessage("错误:没有检测到Token");
            resultKit.setData("没有权限进行操作");
            return resultKit;
        }
    }

    public static float judgeCore(Map uAnswer, Map answer, Map<String, Float> problemScore) {

        Float singleScore = problemScore.get("singleScore");
        Float judgeScore = problemScore.get("judgeScore");
        Float multiScore = problemScore.get("multiScore");

        float socre = 0;
        List<String> judgeProblems = (List<String>) uAnswer.get("judgeProblems"); // 用户判断题答案
        List<String> jAns = (List<String>) answer.get("judgeAns"); //数据库判断题答案
        for (int i = 0; i < judgeProblems.size(); i++) {
            String uAns = judgeProblems.get(i);
            String tAns = jAns.get(i);
            if (uAns.equals(tAns)) {
                System.out.println(uAns.equals(tAns));
                socre += judgeScore;
            }
        }
        List<String> multiSelects = (List<String>) uAnswer.get("multiSelects");
        List<String> mAns = (List<String>) answer.get("multiSelectsAns"); //数据库多选题答案
        for (int i = 0; i < multiSelects.size(); i++) {
            String uAns = multiSelects.get(i);
            String tAns = mAns.get(i);
            if (uAns.equals(tAns)) {
                System.out.println(uAns.equals(tAns));
                socre += multiScore;
            }
        }
        List<String> singleSelects = (List<String>) uAnswer.get("singleSelects");
        List<String> sAns = (List<String>) answer.get("singleSelectsAns"); //数据库单选题答案
        for (int i = 0; i < singleSelects.size(); i++) {
            String uAns = singleSelects.get(i);
            String tAns = sAns.get(i);
            if (uAns.equals(tAns)) {
                System.out.println(uAns.equals(tAns));
                socre += singleScore;
            }
        }
        return socre;
    }

    public static boolean superAdmin(String username, String pwd) {
        if (username.equals("dev") && pwd.equals("7ef4aca5cf398149167ee9de106b641a")) {
            return true;
        }
        return false;
    }

}
