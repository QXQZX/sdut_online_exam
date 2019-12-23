package com.sdut.onlinejudge.mapper;

import com.sdut.onlinejudge.model.StatKit;
import org.springframework.stereotype.Repository;

/**
 * @Author: Devhui
 * @Date: 2019/12/23 19:59
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */
@Repository
public interface StatMapper {
    StatKit getStat();
}
