package cn.witsky.smb.core.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HuangYX
 * @date 2018/5/9 13:51
 */
public class CommonUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    public static String commonResponse(int code, String message) {
        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("code", code);
        map.put("message", message);
        return JSON.toJSONString(map);
    }

    public static Map<Object, Object> commonResponseObj(int code, String message) {
        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("code", code);
        map.put("message", message);
        return map;
    }
    public static String commonResponse(int code, String message, Object data) {
        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("code", code);
        map.put("message", message);
        map.put("data", data);
        return JSON.toJSONString(map);
    }

    public static Map<Object, Object> commonResponseObj(int code, String message, Object data) {
        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("code", code);
        map.put("message", message);
        map.put("data", data);
        return map;
    }

    public static String commonResponse(int code, Object message, Object data) {
        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("code", code);
        if (message instanceof String) {
            map.put("message", message);
        } else {
            map.put("message", JSON.toJSONString(message));
        }
        map.put("data", data);
        return JSON.toJSONString(map);
    }


    public static Map<Object, Object> commonResponseObj(int code, Object message, Object data) {
        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("code", code);
        if (message instanceof String) {
            map.put("message", message);
        } else {
            map.put("message", JSON.toJSONString(message));
        }
        map.put("data", data);
        return map;
    }
    public static String formatJson(String jsonString){
        if (null == jsonString || "".equals(jsonString))
            return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        boolean isInQuotationMarks = false;
        for (int i = 0; i < jsonString.length(); i++) {
            last = current;
            current = jsonString.charAt(i);
            switch (current) {
                case '"':
                    if (last != '\\'){
                        isInQuotationMarks = !isInQuotationMarks;
                    }
                    sb.append(current);
                    break;
                case '{':
                case '[':
                    sb.append(current);
                    if (!isInQuotationMarks) {
                        sb.append('\n');
                        indent++;
                        addIndentBlank(sb, indent);
                    }
                    break;
                case '}':
                case ']':
                    if (!isInQuotationMarks) {
                        sb.append('\n');
                        indent--;
                        addIndentBlank(sb, indent);
                    }
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\' && !isInQuotationMarks) {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
}
