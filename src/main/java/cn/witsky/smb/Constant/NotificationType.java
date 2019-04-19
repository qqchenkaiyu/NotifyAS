package cn.witsky.smb.Constant;

/**
 * Created by HuangYX on 2018/1/5 11:32.
 *
 * @author HuangYX
 */
public interface NotificationType {

    /**
     * 呼叫进展通知
     * eventtype：ACM =1，ANM=2，CPG=3
     *
     */
    Integer ACM =1;
    Integer RING =10;
    Integer ANM =2;
    //提示音结束
    Integer SRR=4;
    Integer CPG=3;

}
