package com.sdut.onlinejudge.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdut.onlinejudge.model.Contest;
import com.sdut.onlinejudge.model.ResultKit;
import com.sdut.onlinejudge.model.Train;
import com.sdut.onlinejudge.service.TrainService;
import com.sdut.onlinejudge.utils.MainUtils;
import com.sdut.onlinejudge.utils.ProblemConstant;
import com.sdut.onlinejudge.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Devhui
 * @Date: 2020/4/8 17:34
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */

@RestController
@RequestMapping("train")
public class TrainController {

    private Logger logger = LoggerFactory.getLogger(TrainController.class);

    @Autowired
    private TrainService trainService;

    @GetMapping("all")
    public ResultKit getAllTrain(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(value = "uid", required = true) String uid) {
        ResultKit<Map> resultKit = new ResultKit<>();

        String orderBy = "tid" + " desc";//按照（数据库）排序字段 倒序 排序
        PageHelper.startPage(pageNum, 10, orderBy);

        List<Train> allTrain = trainService.findAll(uid);
        // 将查询到的数据封装到PageInfo对象
        PageInfo<Contest> pageInfo = new PageInfo(allTrain);
        // 分割数据成功

        long total = pageInfo.getTotal();

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("pageInfo", allTrain);

        resultKit.setData(map);
        resultKit.setCode(ResultCode.SUCCESS.code());
        resultKit.setMessage("获取成功");
        return resultKit;
    }


    @PostMapping("deploy")
    @ResponseBody
    public ResultKit deployNewTrain(@RequestParam(value = "uid", required = true) String uid) {
        ResultKit<Integer> resultKit = new ResultKit<>();
        int i = trainService.deployTrain(uid);
        if (i == 1) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("新测试发布成功");
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("新测试发布失败");
        }
        return resultKit;
    }


    @GetMapping("tid/{tid}")
    @ResponseBody
    public ResultKit getTrain(@PathVariable("tid") Integer tid) {
        Map<String, Object> train = trainService.getTrain(tid);
        ResultKit<Object> resultKit = new ResultKit<>();
        if (train != null) {
            resultKit.setData(train);
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("获取试题成功");
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("获取试题失败");
        }
        return resultKit;
    }

    @PostMapping("submit/tid/{tid}/uid/{uid}")
    @ResponseBody
    public ResultKit submitTrain(@PathVariable("tid") Integer tid,
                                 @PathVariable("uid") String uid,
                                 @RequestBody String userAns) {
        ResultKit<Float> resultKit = new ResultKit();
        Map userAnsMap = JSON.parseObject(userAns, Map.class);

        Map<String, Object> answer = trainService.getAnswer(tid); //答案信息
        HashMap<String, Float> trainScore = new HashMap<>();
        trainScore.put("singleScore", ProblemConstant.trainSingleScore);
        trainScore.put("judgeScore", ProblemConstant.trainJudgeScore);
        trainScore.put("multiScore", ProblemConstant.trainMultiScore);

        float score = MainUtils.judgeCore(userAnsMap, answer, trainScore);
        logger.info("用户uid={}在训练 tid={} 的得分是 {}", uid, tid, score);

        Train train = new Train();
        train.setUid(uid);
        train.setTid(tid);
        train.setUAnswers(userAns);
        train.setScore(score);
        train.setCostTime((Integer) userAnsMap.get("costTime"));

        int i = trainService.trainSubmit(train);
        if (i == 2) { // 加分+提交 记录两个操作
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("判题成功");
            resultKit.setData(score);
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("判题失败, 请勿重复提交");
            resultKit.setData(-1f);
        }

        return resultKit;
    }

    @GetMapping("check/tid/{tid}")
    @ResponseBody
    public ResultKit check(@PathVariable("tid") Integer tid, HttpServletRequest req) {
        ResultKit<Map> resultKit = new ResultKit<>();

        Map<String, String> submit = trainService.checkSubmit(tid);
        if (submit != null) {
            resultKit.setMessage("获取用户提交和答案成功。");
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setData(submit);
        } else {
            resultKit.setMessage("未提交过。");
            resultKit.setCode(ResultCode.WRONG_UP.code());
        }
        return resultKit;
    }

    @GetMapping("topiclist")
    @ResponseBody
    public ResultKit topic() {
        List<Map> topics = trainService.getTopics();
        ResultKit<Object> resultKit = new ResultKit<>();
        resultKit.setData(topics);
        return resultKit;
    }

    @GetMapping("tp")
    @ResponseBody
    public ResultKit getTopicProblem(@RequestParam(value = "type") String type,
                                     @RequestParam(value = "label") String label) {
        List problem = trainService.getTopicProblem(type, label);
        ResultKit<Object> resultKit = new ResultKit<>();
        resultKit.setData(problem);
        return resultKit;
    }

}
