package com.sdut.onlinejudge.model;

import lombok.Data;

/**
 * @Author: Devhui
 * @Date: 2019/12/23 20:32
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */

@Data
public class StatKit {
    private int userNum;
    private int contestNum;
    private int singleSelectNum;
    private int judgeProblemNum;
    private int multiSelectNum;
}
