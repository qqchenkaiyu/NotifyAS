package cn.witsky.smb.core.utils;


import cn.witsky.smb.core.constant.*;
import java.util.*;

/**
 * Created by Administrator on 2017/7/20.
 */
public class JavaUtils {


    public static String getRandomString(int size) {// 随机字符串
        char[] c = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'a', 'b', 'c', 'd'};
        // 初始化随机数产生器
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size; i++) {
            int i1 = random.nextInt(c.length);
            sb.append(c[i1]);
        }
        return sb.toString();
    }
    public static String getUUID() {// 随机字符串
        String s = UUID.randomUUID().toString();
       return s.replaceAll("-","");
    }


    public static  void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static  boolean isNum(String ms){
        try{
            Long.parseLong(ms);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public static  int parseInt(String playcnt, int maxcnt){
        try{
            if(null != playcnt) {
                return Integer.parseInt(playcnt);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return maxcnt;

    }


    public static  String concatenateStrings(String[] strings, String separator, boolean convertNullToEmptyString) {
        String ret = null;
        if(strings != null && strings.length > 0) {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < strings.length; ++i) {
                if(i > 0) {
                    sb.append(separator);
                }

                if(convertNullToEmptyString) {
                    sb.append(strings[i] != null?strings[i]:"");
                } else {
                    sb.append(strings[i]);
                }
            }

            ret = sb.toString();
        }

        return ret;
    }


    public static  String lasttwoSubid(String subid){
        int len = subid.length();
        if(len > Int.ONE){
            return subid.substring(len - Int.TWO,len );
        }
        return subid;
    }

    public static String uuid(){
       return UUID.randomUUID().toString().replaceAll("-", "");
    }


    public static void main(String aa[]){
        System.out.println(lasttwoSubid("M"));
    }

}
