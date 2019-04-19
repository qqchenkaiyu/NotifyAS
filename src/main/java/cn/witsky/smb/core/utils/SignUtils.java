package cn.witsky.smb.core.utils;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class SignUtils {

  private static Logger logger = LoggerFactory.getLogger(SignUtils.class);

  public static String signature(Map<String, String> params, String secret,
      String signName) {
    String result = null;
    if (params == null) {
      return result;
    }

    // 1. 把字典按Key的字母顺序排序
    TreeMap<String, String> treeMap = new TreeMap<String, String>(params);

    // 2. remove sign parameter
    treeMap.remove(signName);

    // 3 把参数串起来
    Iterator<String> iter = treeMap.keySet().iterator();
    StringBuffer orgin = new StringBuffer(secret);
    while (iter.hasNext()) {
      String name = iter.next();
      orgin.append(name).append(params.get(name));
    }
    // 4. 加密：MD5 + 大写&十六进制
    try {
      String preparedSignString = orgin.toString();
      logger.debug("sign.preparedSignString={}", preparedSignString);
      MessageDigest md = MessageDigest.getInstance("MD5");
      result = byte2hex(md.digest(preparedSignString.getBytes("utf-8")));
      logger.debug("sign.result={}", result);
    } catch (Exception e) {
      throw new RuntimeException("sign error with " + e.getMessage());
    }

    return result;
  }



  public static String byte2hex(byte[] b) {
    StringBuffer hs = new StringBuffer();
    String stmp = "";
    for (int n = 0; n < b.length; n++) {
      stmp = (Integer.toHexString(b[n] & 0XFF));
      if (stmp.length() == 1) {
        hs.append("0").append(stmp);
      } else {
        hs.append(stmp);
      }
    }
    return hs.toString().toUpperCase();
//        return hs.toString();
  }


  public static TreeMap<String, Object> parseFieldsToMap(Object o, Field[] declaredFields) {
    TreeMap<String, Object> newTreeMap = new TreeMap<String, Object>();
    for (Field field : declaredFields) {
      field.setAccessible(true);
      int mod = field.getModifiers();
      String declaredKeyWords = (mod == 0) ? "" : (Modifier.toString(mod) + " ");
      if (declaredKeyWords.contains("static")) {
        continue;
      }
      String name = field.getName();
      try {
        Object value = field.get(o);
        if (value != null) {
          newTreeMap.put(name, value);
        }
      } catch (IllegalAccessException e) {
        logger.error("Utilities.parseObjectToUrlPair().EXCEPTION,cause of={}", e.getMessage(), e);
      }
    }
    return newTreeMap;
  }

  public static TreeMap<String, Object> parseObjectToMap(Object o, String... removes) {
    if (o == null) {
      return Maps.newTreeMap();
    }
    Class<?> aClass = o.getClass();
//        String classPackageName = aClass.getPackage().getName();
    Field[] declaredFields = aClass.getDeclaredFields();
    TreeMap<String, Object> fieldsMap = parseFieldsToMap(o, declaredFields);
    Class<?> superclass = aClass.getSuperclass();
    declaredFields = superclass.getDeclaredFields();
    fieldsMap.putAll(parseFieldsToMap(o, declaredFields));
    if (removes != null) {
      for (String remove : removes) {
        fieldsMap.remove(remove);
      }
    }
    return fieldsMap;
  }

  public static String signatureObject(Object o, Map<String, String> signMap,
      String secret, String signName) {
    TreeMap<String, Object> object = parseObjectToMap(o, signName);
    final TreeMap<String, String> comparableObjectTreeMap = Maps.newTreeMap();
    if (signMap != null) {
      comparableObjectTreeMap.putAll(signMap);
    }
    if (object != null) {
      object.forEach((key, value) -> {
        if (value != null) {
          comparableObjectTreeMap.put(key, value.toString());
        }

      });
    }

    return signature(comparableObjectTreeMap, secret, signName);

  }
}
