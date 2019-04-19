//package cn.witsky.smb.aspect;
//
//import cn.witsky.smb.application.constant.*;
//import cn.witsky.smb.application.constant.Cons.*;
//import cn.witsky.smb.as.annotation.*;
//import cn.witsky.smb.as.constant.*;
//import cn.witsky.smb.core.constant.DateFormaters;
//import cn.witsky.smb.core.domain.Log;
//import cn.witsky.smb.core.domain.LogData;
//import cn.witsky.smb.core.domain.LogDataBody;
//import cn.witsky.smb.core.domain.LogDataDigest;
//import cn.witsky.smb.core.log.LogOutPut;
//import cn.witsky.smb.core.utils.CommonUtil;
//import cn.witsky.smb.core.utils.Utilities;
//import com.alibaba.fastjson.JSON;
//import com.google.common.cache.Cache;
//import com.google.common.collect.Maps;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author HuangYX
// * @date 2018/5/16 16:12
// */
//@Aspect
//@Component
//public class CallServiceAspect {
//    private static final Logger logger = LoggerFactory.getLogger(CallServiceAspect.class);
//    private final Cache<String, Map<String, String>> cache;
//    private final LogOutPut logOutPut;
//
//    @Value("${witsky.format-json:false}")
//    private boolean isFormat;
//
//    @Autowired
//    public CallServiceAspect(@Qualifier("cacheForGlobal") Cache<String, Map<String, String>> cache,
//                             LogOutPut logOutPut) {
//        this.cache = cache;
//        this.logOutPut = logOutPut;
//    }
//
//    @Pointcut("@annotation(cn.witsky.smb.as.annotation.RequestOutLog)")
//    public void requestOut() {
//    }
//
//    /**
//     * @param joinPoint
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    @Around("requestOut()")
//    public Object requestOutLog(ProceedingJoinPoint joinPoint) {
//        Object o = null;
//        long startMillis = System.currentTimeMillis();
//        HashMap<String, Object> argsMap = Maps.newHashMap();
//        //args
//        Log log = logOutPut.getRestOutLog();
//        LogData logData = log.getData();
//        LogDataBody logDataBody = logData.getBody();
//
//        Object[] args = joinPoint.getArgs();
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        Annotation[][] annotations = method.getParameterAnnotations();
//        String logType = null;
//        String url = null;
//        Object body = null;
//        Object h = null;
//        for (int i = 0; i < annotations.length; i++) {
//            Annotation[] pas = annotations[i];
//            for (Annotation annotation : pas) {
//                if (annotation instanceof ParamDesc) {
//                    String description = ((ParamDesc) annotation).d();
//
//                    argsMap.put(description, args[i]);
//
//                   /* if (AsCons.GLOBALID.equals(description)) {
//                        log.setGlobalId(String.valueOf(args[i]));
//                    } else */
//                    if (Annotations.URL.equals(description)) {
//                        url = String.valueOf(args[i]);
//                        log.setToAddress(url);
//                    } else if (Annotations.HEADER.equals(description)) {
//                        Map<String, String> headers = (Map<String, String>) args[i];
//                        h = args[i];
//                        if (headers != null) {
//
//                            log.setGlobalId(headers.get(HeaderFiled.GLOBAL_ID));
//
//                            String localSessionId = headers.get(HeaderFiled.XDR_ID);
//                            if (localSessionId != null) {
//                                Map<String, String> map = cache.getIfPresent(localSessionId);
//                                if (map != null && map.get(HeaderFiled.LOG_GIGEST) != null) {
//                                    logData.setDigest(Utilities.parseObject(map.get(HeaderFiled.LOG_GIGEST), LogDataDigest.class));
//                                }
//                            }
//
//                            logType = headers.remove(Cons.HeaderFiled.TEMP_HEADER_TYPE);
//                        }
//                    }  else if (Annotations.BODY.equals(description)) {
//                        body = args[i];
//                    }
//                }
//            }
//        }
//        logDataBody.setRequestDetails(argsMap);
//        logDataBody.setRequestTimestamp(DateFormaters.YYYYMMDDHHMMSSSSS.print(startMillis));
//
//        try {
//            o = joinPoint.proceed();
//        } catch (Throwable e) {
//            logger.error("RequestOutAspect.doAround().EXCEPTION,cause of={}", e.getMessage(), e);
//            o = CommonUtil.commonResponse(50001, "Exception! Please retry a moment later!");
//        }
//
//        logDataBody.setResponseDetails(o);
//
//        long endMillis = System.currentTimeMillis();
//        logDataBody.setRequestTimestamp(DateFormaters.YYYYMMDDHHMMSSSSS.print(endMillis));
//        long cost = endMillis - startMillis;
//        logDataBody.setProcessCost(cost);
//
//        logOutPut.exportLogs(log);
//        if (o instanceof ResponseEntity) {
//            ResponseEntity r = (ResponseEntity) o;
//            String err = r.getHeaders().getFirst(Cons.EXCEPTION_RECEIVED);
//            if (isFormat && body instanceof String) {
//                Object logs;
//                try {
//                    String formatBody = (String) body;
//                    logs = JSON.parseObject(formatBody);
//                } catch (Exception e) {
//                    logs= body;
//                }
//                logger.info("[{}] {},url[{}],body[{}],headers[{}],response={},cost={}", logType, err == null ? "Success" : "Failed" ,
//                    url, logs, h, r,cost);
//            }else {
//                logger.info("[{}] {},url[{}],body[{}],headers[{}],response={},cost={}", logType, err == null ? "Success" : "Failed" ,
//                    url, body, h, r,cost);
//            }
//        } else {
//            logger.info("request.time={},request.args = {}, response = {}, cost = {}", logDataBody.getRequestTimestamp(), argsMap, o, cost);
//        }
//
//        return o;
//    }
//
//    @AfterThrowing(pointcut = "requestOut()", throwing = "throwable")
//    public void doException(JoinPoint joinPoint, Throwable throwable) {
//        logger.error("OutRequestAspect.doException().EXCEPTION,cause of={},thr = {}", "exception", throwable.getMessage(), throwable);
//    }
//}
