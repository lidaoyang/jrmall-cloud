package com.jrmall.cloud.common.excel.base;

import java.lang.annotation.*;

/**
 * sheet名称
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Documented
@Inherited
public @interface SheetName {
    String value() default "";
}
