package com.sdut.onlinejudge.mapper;

import com.sdut.onlinejudge.model.Notice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Devhui
 * @Date: 2020/2/6 19:32
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */

@Repository
public interface NoticeMapper {

    // 获取全部通知
    List<Notice> fetchNotices();

    // 发布通知
    int addNotice(Notice notice);

    // 删除通知
    int deleteNotice(int nid);

    // 更改通知状态
    int updateNotice(int nid, int status);
}
