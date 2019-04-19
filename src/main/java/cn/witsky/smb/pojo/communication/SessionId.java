package cn.witsky.smb.pojo.communication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by HuangYX on 2017/9/18 14:08.
 */
public class SessionId implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(SessionId.class);
    /**能力组件地址（json字段"c"）为能力组件（CSCF）实例在SLF中的内部表示形式，由SLF定义具体格式*/
    private String c;
    /**AS服务地址（json字段"a"）为AS服务接口的上下文根路径*/
    private String a;
    /**事务标识（json字段"submit"）为事务在AS内的唯一标识，由AS定义具体格式*/
    private String s;

    public String toJson(){
        return  "{\"c\":\""+c+"\", \"a\":\""+a+"\", \"s\":\""+s+"\"}";
    }

    public SessionId() {
    }

    public SessionId(String c, String a, String s) {
        this.c = c;
        this.a = a;
        this.s = s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionId sessionId = (SessionId) o;

        if (c != null ? !c.equals(sessionId.c) : sessionId.c != null) return false;
        if (a != null ? !a.equals(sessionId.a) : sessionId.a != null) return false;
        return s != null ? s.equals(sessionId.s) : sessionId.s == null;
    }

    @Override
    public int hashCode() {
        int result = c != null ? c.hashCode() : 0;
        result = 31 * result + (a != null ? a.hashCode() : 0);
        result = 31 * result + (s != null ? s.hashCode() : 0);
        return result;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
