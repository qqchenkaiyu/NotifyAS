package cn.witsky.smb.Constant;

/**
 * @author wangyuyang@witsky.cn
 * @date 2018/12/14 16:36
 */
public interface ServiceKeyCons {

    /**
     * 业务主键
     */
    String SERVICE_KEY = "serviceKey";

    /**
     * 号码管控，NAS功能
     */
    String SERVICE_KEY_CALL_CONTROL = "10";

    /**
     * 订阅类业务，隐私绑定，BSS功能
     */
    String SERVICE_KEY_HIDE_BIND = "11";

    /**
     * 订阅类业务，数据更新，BSS功能
     */
    String SERVICE_KEY_CALL_BACK = "12";

}
