package cn.witsky.smb.core.constant;

import com.alibaba.fastjson.TypeReference;

import java.util.Map;
import java.util.Set;

/**
 * @author HuangYX
 */
public interface TypeReferences {


    TypeReference<Map<String, Object>> STRING_OBJECT_MAP = new TypeReference<Map<String, Object>>() {
    };
    TypeReference<Map<Object, Object>> OBJECT_OBJECT_MAP = new TypeReference<Map<Object, Object>>() {
    };
    TypeReference<Map<Integer, Set<String>>> INTEGER_SET_MAP = new TypeReference<Map<Integer, Set<String>>>() {
    };
    TypeReference<Map<String, String>> STRING_STRING_MAP = new TypeReference<Map<String, String>>() {
    };
    TypeReference<Boolean> BOOLEAN = new TypeReference<Boolean>() {
    };
    TypeReference<Boolean> STRING_LIST = new TypeReference<Boolean>() {
    };
}
