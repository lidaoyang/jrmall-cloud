package com.jrmall.cloud.common.base;

import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author huawei
 * @desc VO 基类
 * @email huawei_code@163.com
 * @since 2021/1/11
 */
@Data
@ToString
public class BaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
