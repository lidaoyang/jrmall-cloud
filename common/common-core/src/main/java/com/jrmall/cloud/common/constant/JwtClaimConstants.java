package com.jrmall.cloud.common.constant;

/**
 * JWT常量
 */
public interface JwtClaimConstants {

    /**
     * 用户ID
     */
    String USER_ID = "userId";

    /**
     * 用户名
     */
    String USERNAME = "username";

    /**
     * 部门ID
     */
    String DEPT_ID = "deptId";

    /**
     * 数据权限
     */
    String DATA_SCOPE = "dataScope";

    /**
     * JWT claim中存储授权信息(角色)的字段名称
     */
    String AUTHORITIES = "authorities";

}
