//package cn.witsky.smb.aspect;
//
//import cn.witsky.smb.application.constant.Cons.*;
//import cn.witsky.smb.as.annotation.*;
//import cn.witsky.smb.as.util.*;
//import cn.witsky.smb.core.constant.DateFormaters;
//import cn.witsky.smb.core.domain.Log;
//import cn.witsky.smb.core.domain.LogData;
//import cn.witsky.smb.core.domain.LogDataBody;
//import cn.witsky.smb.core.domain.LogDataDigest;
//import cn.witsky.smb.core.log.LogOutPut;
//import cn.witsky.smb.core.utils.Utilities;
//import com.google.common.collect.Maps;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * Created by HuangYX on 2018/4/4 15:39.
// *
// * @author HuangYX
// */
//@Aspect
//@Component
//public class ReceivedServiceAspect {
//    private static final Logger logger = LoggerFactory.getLogger(ReceivedServiceAspect.class);
//
//    private final LogOutPut logOutPut;
//
//    @Autowired
//    public ReceivedServiceAspect(LogOutPut logOutPut) {
//        this.logOutPut = logOutPut;
//    }
//
//    @Pointcut(value = "@annotation(cn.witsky.smb.as.annotation.RequestInLog)")
//    public void requestIn() {
//    }
//
//    private static HttpServletRequest getHttpServletRequest() {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (attributes != null) {
//            return attributes.getRequest();
//        }
//        return null;
//    }
//
//    @Around("requestIn()")
//    public Object doAround(ProceedingJoinPoint joinPoint) {
//        Object o = null;
//        //start-time-millis
//        long startMillis = System.currentTimeMillis();
//        //args
//        String remoteAddress = null;
//        String requestUrl = null;
//        String requestMethodType = null;
//        HttpServletRequest request = getHttpServletRequest();
//        if (request != null) {
//            remoteAddress = request.getRemoteAddr();
////            requestUrl = request.getRequestURL().toString();
//            requestUrl = request.getRequestURI();
//            requestMethodType = request.getMethod();
//        }
//        Object[] args = joinPoint.getArgs();
//        HashMap<Object, Object> argsMap = Maps.newHashMap();
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        Annotation[][] annotations = method.getParameterAnnotations();
//        for (int i = 0; i < annotations.length; i++) {
//            Annotation[] pas = annotations[i];
//            for (Annotation annotation : pas) {
//                if (annotation instanceof ParamDesc) {
//                    String description = ((ParamDesc) annotation).d();
//                    argsMap.put(description, args[i]);
//                }
//            }
//        }
//
//        //log
//        Log log = logOutPut.getImportLog();
//        //log_data
//        LogData logData = log.getData();
//        LogDataBody logDataBody = logData.getBody();
//
//        HashMap<Object, Object> requestMap = Maps.newHashMap();
//
//        requestMap.put("httpMethod", requestMethodType);
//        requestMap.put("sourceIP", remoteAddress);
//
//        requestMap.put("params", argsMap);
//
//        logger.info("AS接收服务[{}], Data = {}",requestUrl, requestMap);
//
//        requestMap.put("requestUrl", requestUrl);
//        logDataBody.setRequestDetails(requestMap);
//        logDataBody.setRequestTimestamp(DateFormaters.YYYYMMDDHHMMSSSSS.print(startMillis));
//
//
//
//        try {
//            o = joinPoint.proceed();
//
//            if (o instanceof ResponseEntity) {
//                ResponseEntity entity = (ResponseEntity) o;
//                HttpHeaders headers = entity.getHeaders();
//
//                List<String> global = headers.get(HeaderFiled.GLOBAL_ID);
//                if (global != null && global.size() > 0) {
//                    log.setGlobalId(global.get(0));
//                }
//                List<String> from = headers.get(HeaderFiled.DIALOG_FROM);
//                if (from != null && from.size() > 0 && from.get(0) != null) {
//                    log.setFrom(from.get(0));
//                } else {
//                    log.setFrom(AsSlfUtil.getRemoteAddress());
//
//                }
//
//                List<String> digests = headers.get(HeaderFiled.LOG_GIGEST);
//
//                if (digests != null && digests.size() > 0) {
//                    LogDataDigest logDataDigest = Utilities.parseObject(digests.get(0), LogDataDigest.class);
//                    logData.setDigest(logDataDigest);
//                }
//
//                Object respBody = entity.getBody();
//
//
//                logDataBody.setResponseDetails(respBody);
//                logDataBody.setResponseTimestamp(DateFormaters.YYYYMMDDHHMMSSSSS.print(System.currentTimeMillis()));
//
//                o = new ResponseEntity<Object>(respBody, entity.getStatusCode());
//            }
//
//        } catch (Throwable e) {
//            logger.error("RequestInLogAspect.doAround().EXCEPTION,cause of={}", e.getMessage(), e);
//        }
//
//        long cost = System.currentTimeMillis() - startMillis;
//        logDataBody.setProcessCost(cost);
//        logOutPut.exportLogs(log);
//        logger.info("AS响应服务[{}]={}, 耗时={}毫秒",requestUrl, o, cost);
//        return o;
//    }
//
//}
