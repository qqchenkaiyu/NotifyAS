package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * @author HuangYX
 * @date 2018/6/6 19:08
 */
@Data
public class ApkRestrict {
    /**
     * 取值为：
     * 1：允许接入
     * 0：限制接入
     */
    private String ctrl;
    /**
     * 条件	如果ctrl值为“限制”时的放音编号
     */
    private String annc;
    /**
     * 条件, 如果ctrl为“限制”时的推送策略：
     * 0，不推送；1，推送
     */
    private String push;

}
