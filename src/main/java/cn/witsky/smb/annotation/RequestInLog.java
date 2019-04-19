package cn.witsky.smb.annotation;

import java.lang.annotation.*;

/**
 * @author HuangYX
 * @date 2018/5/9 9:56
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestInLog {
    /**
     * 描述
     *
     * @return 描述内容
     */
    String d() default "";
}
