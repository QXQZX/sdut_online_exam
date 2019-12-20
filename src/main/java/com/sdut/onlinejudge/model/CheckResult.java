package com.sdut.onlinejudge.model;

import io.jsonwebtoken.Claims;
import lombok.Data;

/**
 * jwt验证结果
 */
@Data
public class CheckResult {
    private boolean success;
    private int errorCode;
    private Claims claims;
}
