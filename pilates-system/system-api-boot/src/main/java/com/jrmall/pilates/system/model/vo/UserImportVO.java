package com.jrmall.pilates.system.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户导入对象
 *
 * @author haoxr
 * @since 2022/4/10
 */
@Data
public class UserImportVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "用户名")
    private String username;

    @ExcelProperty(value = "昵称")
    private String nickname;

    @ExcelProperty(value = "性别")
    private String gender;

    @ExcelProperty(value = "手机号码")
    private String mobile;

    @ExcelProperty(value = "邮箱")
    private String email;

    @ExcelProperty("角色")
    private String roleCodes;

}
