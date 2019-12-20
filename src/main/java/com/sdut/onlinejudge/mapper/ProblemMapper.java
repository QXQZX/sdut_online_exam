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
    List<SingleSelect> fetchSingleSelects();

    List<MultiSelect> fetchMultiSelects();

    List<JudgeProblem> fetchJudgeProblem();
}
