package com.jrmall.pilates.common.dubbo.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * 登录用户信息
 *
 * @author: Dao-yang.
 * @date: Created in 2025/6/9 13:42
 */
@Data
public class LoginUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * JWT令牌ID
     */
    private String jti;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户角色数据权限集合
     */
    private Integer dataScope;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户角色编码集合 ["ROOT","ADMIN"]
     */
    private Set<String> roles = new HashSet<>();

    /**
     * 过期时间
     */
    private Instant exp;
}
