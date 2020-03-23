package com.sdut.onlinejudge.mapper;

import com.sdut.onlinejudge.model.JudgeProblem;
import com.sdut.onlinejudge.model.MultiSelect;
import com.sdut.onlinejudge.model.SingleSelect;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Devhui
 * @Date: 2019-12-04 17:14
 * @Version 1.0
 */
@Repository
public interface ProblemMapper {
    // 随机抽题
    List<SingleSelect> fetchSingleSelects(int count);

    List<MultiSelect> fetchMultiSelects(int count);

    List<JudgeProblem> fetchJudgeProblem(int count);

    // 拉取所有题目
    List<SingleSelect> getSingleSelects(String keyWords);

    List<MultiSelect> getMultiSelects(String keyWords);

    List<JudgeProblem> getJudgeProblem(String keyWords);

    // 添加题目
    int addJudgeProblem(JudgeProblem judgeProblem);

    int addSingleSelect(SingleSelect select);

    int addMultiSelect(MultiSelect select);

    // 删除题目
    int delJudgeProblem(String jpid);

    int delSingleSelect(String dpid);

    int delMultiSelect(String mpid);
}
