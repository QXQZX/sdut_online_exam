package com.sdut.onlinejudge.mapper;

/**
 * @Author: Devhui
 * @Date: 2019-11-28 17:05
 * @Version 1.0
 */

import com.sdut.onlinejudge.model.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper {
    // 登陆检查
    Admin loginCheck(String username, String password);
}
