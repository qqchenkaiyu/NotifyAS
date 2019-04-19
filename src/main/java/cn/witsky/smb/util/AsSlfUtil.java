package cn.witsky.smb.util;

import cn.witsky.smb.Constant.AsCons;
import cn.witsky.smb.core.constant.CoreCons;
import cn.witsky.smb.core.constant.Int;
import cn.witsky.smb.core.utils.JavaUtils;
import cn.witsky.smb.pojo.communication.SessionId;
import cn.witsky.smb.pojo.communication.SlfRes;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author HuangYX
 * @date 2018/5/29 10:21
 */
public class AsSlfUtil {
    private static final Logger logger = LoggerFactory.getLogger(AsSlfUtil.class);
    private static final long INITIAL_VALUE = 1000000L;
    private static final long MAX_VALUE = INITIAL_VALUE * Int.NINE;
    private static AtomicLong INDEX_ATOMIC = new AtomicLong(INITIAL_VALUE);
@Autowired
    Environment environment;
    /**
     * 参数错误响应
     *
     * @return
     */
    public static SlfRes getInvalidRequestParamsRes() {
        return new SlfRes(AsCons.SLFRES_CODE_INVALID_PARAMS, AsCons.SLFRES_MSG_INVALID_PARAMS);
    }

    /**
     * 参数错误响应
     *
     * @return
     */
    public static SlfRes getInvalidRequestParamsRes(String message) {
        return new SlfRes(AsCons.SLFRES_CODE_INVALID_PARAMS, message);
    }

    /**
     * 处理错误响应
     *
     * @param message
     * @return
     */
    public static SlfRes getExceptionHandleRes(String message) {
        return new SlfRes(AsCons.SLFRES_CODE_PROCESS_ERROR, message);
    }

    /**
     * 失败响应
     *
     * @param message
     * @return
     */
    public static SlfRes getFailRes(String message) {
        return new SlfRes(AsCons.SLFRES_CODE_FAIL, message);
    }

    /**
     * 成功响应
     *
     * @return
     */
    public static SlfRes getOKRes() {
        return new SlfRes(AsCons.SLFRES_CODE_OK, AsCons.SLFRES_MSG_OK);
    }

    /**
     * 成功响应
     *
     * @param sessionId
     * @return
     */
    public static SlfRes getOKRes(String sessionId) {
        return new SlfRes(AsCons.SLFRES_CODE_OK, AsCons.SLFRES_MSG_OK, sessionId);
    }

    /**
     * 创建响应体
     *
     * @param o
     * @param headers
     * @return
     */
    public static ResponseEntity<String> createResponseEntityWithJsonBody(Object o, Map<String, String> headers) {
        String body;
        if (o instanceof String) {
            body = String.valueOf(o);
        } else {
            body = JSON.toJSONString(o);
        }
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String,String> key : headers.entrySet()) {
                builder.header(key.getKey(), key.getValue());
            }
        }
        return builder.body(body);
    }
    /**
     * 创建响应体
     *
     * @param o
     * @return
     */
    public static ResponseEntity<String> createResponseEntityWithJsonBody(Object o) {
        return createResponseEntityWithJsonBody(o,null);
    }



    /**
     * 获取远程地址
     *
     * @return
     */
    public static String getRemoteAddress() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            return getRemoteAddress(request);
        }
        return null;
    }

    /**
     * 获取远程地址
     *
     * @return
     */
    public static String getRemoteAddress(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            String unknown = "unknown";
            if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        logger.error("Utilities.getRemoteAddress().EXCEPTION,cause of={}", e.getMessage(), e);
                    }
                    if (inet != null) {
                        ipAddress = inet.getHostAddress();
                    }
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }

    /**
     * 解析sessionId
     *
     * @param sessionId
     * @return localSessionId
     */
    public static String parseLocalSessionId(String sessionId) {

        if (sessionId == null || Objects.equals(CoreCons.EMPTY, sessionId)) {
            return null;
        }
        try {
            SessionId ses = JSON.parseObject(sessionId, SessionId.class);
            if (ses != null) {
                return ses.getS();
            }
            return null;
        } catch (Exception e) {
            logger.error("parse_sessionId_ERROR,cause of = " + e.getMessage() + "; text=" + sessionId);
            return null;
        }
    }

    /**
     * 生成localSessionId
     *
     * @param callId
     * @return
     */
    public static synchronized String generateLocalSessionId(String callId) {
//        return uuid();
        if (callId == null) {
            return JavaUtils.uuid();
        }
        int callIdLength = callId.length();

        if (callIdLength >= Int.THIRTY_TWO) {
            return callId;
        }

        long andIncrement = INDEX_ATOMIC.getAndIncrement();
        if (andIncrement > MAX_VALUE) {
            INDEX_ATOMIC.set(INITIAL_VALUE);
        }

        String index = Long.toString(andIncrement, Int.THIRTY_TWO);

        int indexLength = index.length();

        if (callIdLength + indexLength <= Int.TWENTY_THREE) {
            String tmp = Long.toString(System.currentTimeMillis(), Int.THIRTY_TWO);
            return Joiner.on("").join(new String[]{callId, tmp, index, JavaUtils.getRandomString(Int.THIRTY_TWO - callIdLength - tmp.length() - indexLength)});
        } else if (callIdLength + indexLength <= Int.THIRTY_TWO) {
            return Joiner.on("").join(new String[]{callId, index, JavaUtils.getRandomString(Int.THIRTY_TWO - callIdLength - indexLength)});
        }
        return Joiner.on("").join(new String[]{callId, JavaUtils.getRandomString(Int.THIRTY_TWO - callIdLength)});
    }

}
