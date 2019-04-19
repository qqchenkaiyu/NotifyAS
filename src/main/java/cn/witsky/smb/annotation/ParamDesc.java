package cn.witsky.smb.annotation;

import java.lang.annotation.*;

/**
 * 日志记录注解，
 * @author HuangYX
 * @date 2018/4/18 20:25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ParamDesc {

    /**
     * 描述
     *
     * @return 描述内容
     */
    String d() default "";

}
