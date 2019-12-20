package com.sdut.onlinejudge.service;

import java.util.Map;

/**
 * @Author: Devhui
 * @Date: 2019-12-04 19:34
 * @Version 1.0
 */
public interface ProblemService {
    // 拉取整套题
    Map<String, Object> fetchProblems();
}
