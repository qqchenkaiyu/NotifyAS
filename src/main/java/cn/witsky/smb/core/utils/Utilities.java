package cn.witsky.smb.core.utils;

import cn.witsky.smb.core.constant.CoreCons;
import cn.witsky.smb.core.constant.TypeReferences;
import cn.witsky.smb.pojo.communication.CodeRes;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhanghaifeng
 * @time 2017年08月29日 下午3:30
 */
public class Utilities {

    private final static Logger logger = LoggerFactory.getLogger(Utilities.class);

    public static Map<String, Object> parseFlatJsonToStringKeyedMap(String string) {

        try {
            return JSON.parseObject(string, TypeReferences.STRING_OBJECT_MAP);
        } catch (Exception e) {
            logger.error("parseFlatJsonToStringKeyedMap_exception", e);
        }
        return Maps.newHashMap();
    }
    public static Map<String, String> parseFlatJsonToStringKeyedStringValueMap(String string) {

        try {
            return JSON.parseObject(string, TypeReferences.STRING_STRING_MAP);
        } catch (Exception e) {
            logger.error("parseFlatJsonToStringKeyedMap_exception", e);
        }
        return Maps.newHashMap();
    }
    public static HttpEntity<String> createHttpEntityForJson(String jsonString) {

        return createHttpEntityForJson(jsonString, null);
    }

    public static HttpEntity<String> createHttpEntityForJson(String jsonString,
                                                             Map<String, String> headers) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpHeaders.set(entry.getKey(), entry.getValue());
            }
        }
        return new HttpEntity<String>(jsonString, httpHeaders);
    }
    public static HttpEntity<String> createHttpEntityForJson(Object jsonString,
                                                             Map<String, String> headers) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpHeaders.set(entry.getKey(), entry.getValue());
            }
        }
        if(jsonString instanceof String){
            return new HttpEntity<String>((String) jsonString, httpHeaders);
        }else {
            return new HttpEntity<String>(JSON.toJSONString(jsonString), httpHeaders);
        }

    }
    /**
     * 判断map形式的结果是否ok（map中如果有一个key为"code"，且值为整数0则表示ok）
     *
     * @param map
     * @return
     */
    public static boolean resultMapMeansOk(Map<String, Object> map) {

        if (map != null) {
            Integer code = (Integer) map.get("code");
            if (code != null && code == 0) {
                return true;
            }
        }
        return false;
    }
    /**
     * 判断map形式的结果是否ok（map中如果有一个key为"code"，且值为整数0则表示ok）
     *
     * @param codeRes
     * @return
     */
    public static boolean resultCodeResOk(CodeRes codeRes) {

        if (codeRes != null) {
            Integer code = codeRes.getCode();
            if (code != null && code == 0) {
                return true;
            }
        }
        return false;
    }
    public static Map<String, Object> createImmutableResultMap(int code,
                                                               String message) {

        ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>().put("code", code);
        if (message != null) {
            builder.put("message", message);
        }
        return builder.build();
    }

    public static JSONObject createJsonObject(int code, String message) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("message", message);
        return jsonObject;
    }

    public static Map<String, Object> createResultMap(int code,
                                                      String message) {

        Map<String, Object> map = Maps.newHashMap();
        map.put("code", code);
        if (message != null) {
            map.put("message", message);
        }
        return map;
    }

    public static String formatTime(DateTimeFormatter dateTimeFormatter,
                                    long millis) {

        return dateTimeFormatter.print(millis);
    }


    public static String convertCommaToVerticalLine(String sessionId) {
        if (sessionId == null) {
            return CoreCons.EMPTY;
        }
        return sessionId.replaceAll(CoreCons.COMMA, CoreCons.SIDSUXIAN);
    }


    public static <T> T parseObject(String text, Class<T> calzz) {
        if (text == null || Objects.equals(CoreCons.EMPTY, text)) {
            return null;
        }
        try {
            return JSON.parseObject(text, calzz);
        } catch (Exception e) {
            logger.error("parse_string_to_" + calzz.getName() + "_ERROR,cause of = " + e.getMessage() + "; text=" + text);
            return null;
        }
    }

    public static <T> T parseObject(String text, TypeReference<T> calzz) {
        if (text == null || Objects.equals(CoreCons.EMPTY, text)) {
            return null;
        }
        try {
            return JSON.parseObject(text, calzz);
        } catch (Exception e) {
            logger.error("parse_string_to_" + calzz.getType() + "_ERROR,cause of = " + e.getMessage() + "; text=" + text);
            return null;
        }
    }
    public static JSONObject parseObject(String text) {
        if (text == null || Objects.equals(CoreCons.EMPTY, text)) {
            return null;
        }
        try {
            return JSON.parseObject(text);
        } catch (Exception e) {
            logger.error("parse_json_ERROR,cause of = " + e.getMessage() + "; text=" + text);
            return null;
        }
    }


    /**
     * 主要实现字符串前缀比较；
     *
     * @param source 被比较字符串
     * @param prefix 前缀
     * @return {@code source}以{@code prefix}开头，返回0；
     * 按顺序比较，当出现同一下标值，{@code source}的值比{@code prefix}的大时，返回正差值；
     * {@code source}的值比{@code prefix}的小时，返回负差值；
     */
    public static int compareWithStart(@NotNull String source, @NotNull String prefix) {
        char[] pChars = prefix.toCharArray();
        char[] sChars = source.toCharArray();

        for (int i = 0; i < Math.min(pChars.length, sChars.length); i++) {
            char sChar = sChars[i];
            char pChar = pChars[i];
            int len = sChar - pChar;
            if (len != 0) {
                return len;
            }
        }
        return 0;
    }


    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest();
        }
        return null;
    }

    private static final Pattern HEADER_PATTERN = Pattern.compile("(?<name>\\w+)([=:])(?<value>[^;,\n]+)");
    public static Map<String, String> splitEqualSignTextToMap(String ccfId) {
        if (Strings.isNullOrEmpty(ccfId)) {
            return Maps.newHashMap();
        }
        HashMap<String, String> map = Maps.newHashMap();
        Matcher matcher = HEADER_PATTERN.matcher(ccfId);
        while (matcher.find()) {
            map.put(matcher.group("name"), matcher.group("value"));
        }
        return map;
    }
}
