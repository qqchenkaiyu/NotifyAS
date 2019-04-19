//package cn.witsky.smb.aspect;
//
//import cn.witsky.smb.as.annotation.*;
//import com.google.common.base.Strings;
//import com.google.common.util.concurrent.AtomicLongMap;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//
///**
// * @author HuangYX
// * @date 2018/4/25 15:16
// */
//@Aspect
//@Component
//@EnableScheduling
//public class CountsLogAspect {
//    private static final Logger logger = LoggerFactory.getLogger(CountsLogAspect.class);
//
//    private static AtomicLongMap<String> map = AtomicLongMap.create();
//
//    @Pointcut("@annotation(cn.witsky.smb.as.annotation.Counts)")
//    public void counts() {
//    }
//
//    @Before(value = "counts()")
//    public void beforeRest(JoinPoint joinPoint) {
//
//        Signature signature = joinPoint.getSignature();
//        String description = null;
//        try {
//            MethodSignature methodSignature = (MethodSignature) signature;
//            Method method = methodSignature.getMethod();
//            Counts annotation = method.getAnnotation(Counts.class);
//            if (annotation != null) {
//                description = annotation.d();
//            }
//
//            if (!Strings.isNullOrEmpty(description)) {
//                map.addAndGet(description, 1);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void scheduledFroShowAtomiclog() {
//        logger.info("Service log = {}", map);
//    }
//}
