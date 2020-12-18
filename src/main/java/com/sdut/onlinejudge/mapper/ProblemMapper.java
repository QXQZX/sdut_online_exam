package com.sdut.onlinejudge.mapper;

import com.sdut.onlinejudge.model.JudgeProblem;
import com.sdut.onlinejudge.model.MultiSelect;
import com.sdut.onlinejudge.model.SingleSelect;
import org.apache.ibatis.annotations.Param;
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
    List<SingleSelect> fetchSingleSelects(int count, String label);

    List<MultiSelect> fetchMultiSelects(int count, String label);

    List<JudgeProblem> fetchJudgeProblem(int count, String label);

    // 目的性抽取题目
    List<SingleSelect> getSingleSelectsSelf(@Param("ids") List ids);

    List<MultiSelect> getMultiSelectsSelf(@Param("ids") List ids);

    List<JudgeProblem> getJudgeProblemSelf(@Param("ids") List ids);

    // 拉取所有题目
    List<SingleSelect> getSingleSelects(@Param("keyWords") String keyWords, @Param("label") String label);

    List<MultiSelect> getMultiSelects(@Param("keyWords") String keyWords, @Param("label") String label);

    List<JudgeProblem> getJudgeProblem(@Param("keyWords") String keyWords, @Param("label") String label);

    // 添加题目
    int addJudgeProblem(JudgeProblem judgeProblem);

    int addSingleSelect(SingleSelect select);

    int addMultiSelect(MultiSelect select);

    // 删除题目
    int delJudgeProblem(String jpid);

    int delSingleSelect(String dpid);

    int delMultiSelect(String mpid);

}
