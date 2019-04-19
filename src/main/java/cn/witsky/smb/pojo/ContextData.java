package cn.witsky.smb.pojo;

import cn.witsky.smb.core.constant.CoreCons;
import cn.witsky.smb.core.constant.Str;
import cn.witsky.smb.pojo.communication.SessionId;
import com.google.common.base.Joiner;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.core.env.Environment;

import java.io.Serializable;


@Accessors(chain = true)
@Data
public class ContextData implements Serializable,Cloneable{

    private static final long serialVersionUID = 52111995615142171L;
    protected   CallData callData;
    /**
     * localSessionId  32位状态机标识号
     * SessionId 包含：
     a:当前服务地址
     c:slf地址
     s:localsessionID
     */
//    protected CommonContextData data;
    protected String localSessionId;
    protected String SessionId;
    protected String callID;
    //订单ID
    protected String subID;
    //区号
    protected String areaCode;
    /**
     * http://192.168.150.233:5055/slf   实际访问还要加方法名
     */
    protected String slfRootUrl;
    /**
     * http://192.168.110.94:6051
     */
    protected String asRootUrl;
    /**
     * setup上来的时间戳
     */
    protected Long setupTimestamp;
    //是否是放音收号模式
    protected boolean isIVR=false;

    //放音文件名
    protected String annFileName;
    //按键信息
    protected String dtmf;
    //IT检测失败次数
    protected int itProgressiveFailures;
    //IT检测次数
    protected int itSequences;
    //已播放次数
    protected Integer playTimes=0;

    protected boolean isTalked;
    protected boolean timeOutWhenRing;
    protected String status;
//String 型
    //缓存呼出时间
    protected String calltime;
    //缓存接通时间
    //protected String playTimes;
    //缓存响铃时间
    protected String ringtime;
    //缓存摘机时间
    protected String answertime;
    //缓存释放时间
    protected String releasetime;

//Long 型
//缓存接通时间
protected long starttime;
    //缓存呼入时间
    protected long callintime;
    //缓存呼出时间
    protected long callouttime;
    //缓存挂断时间
    protected long endtime;
    //缓存挂断时间
    protected long durationTime;
    /**
     * 释放方向；1表示主叫， 2表示被叫，0表示平台释放
     */
    protected int dir;
    int cause;
   boolean hadReceivedRingingProgress;
    long ringingTime;
    long alertTime;

    //protected String annFileName;
    /**
     * slf带来的 全局ID
     */
    protected String globalId = Str.ZERO;



    //递减剩余播放次数
    public int decreasePlayTime() {
        return --playTimes;
    }
    //递增剩余播放次数
    public int increasePlayTime() {
        return ++playTimes;
    }
    public int increaseITProgressiveFailures() {
        return ++itProgressiveFailures;
    }
    public void resetItProgressiveFailures() {
        itProgressiveFailures = 0;
    }

    public int increaseITSequences() {
        return itSequences++;
    }
    public ContextData(String slfRootUrl, long time, String globalId, String fromHost, String ccfId, CallData callData) {
        this.slfRootUrl = slfRootUrl;
        this.setupTimestamp = time;
        this.globalId=globalId==null?Str.ZERO:globalId;
        this.fromHost = fromHost;
        this.callData = callData;
    }
    public ContextData(String slfRootUrl,long time, CallData callData) {
        this.slfRootUrl = slfRootUrl;
        this.setupTimestamp = time;
        this.callData = callData;
    }
    public ContextData clone() {
        try {
            return (ContextData) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return  null;
    }
    private void setGlobalId(String globalId) {
        if (globalId == null) {
            return;
        }
        this.globalId = globalId;
    }

    protected String fromInstance;

    protected String ccfId;

    /**
     * slf带来的 来源
     */
    protected String fromHost;

    /**
     * 回传给大网的主被叫
     */
    private String caller;
    private String called;


    public ContextData() {
    }

    public void GenerateSessionId(String pid,Environment environment) {
      String asRootUrl = Joiner.on("").join("http://", environment.getProperty(CoreCons.SERVER_ADDRESS), ":",
                environment.getProperty(CoreCons.SERVER_PORT));
      //这里考虑对象回收
        SessionId sessionId =new SessionId();
        sessionId.setA(asRootUrl);
        sessionId.setS(localSessionId);
        sessionId.setC(pid+environment.getProperty(CoreCons.SLFIP));
        this.SessionId=sessionId.toJson();
    }
}
