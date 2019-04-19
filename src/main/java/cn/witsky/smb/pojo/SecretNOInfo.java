package cn.witsky.smb.pojo;

import lombok.Data;

/**
 * @author HuangYX
 * @date 2018/6/1 10:52
 */
@Data
public class SecretNOInfo {

    /**
     * appkey标识
     */
    private String appkey;
    /**
     * 号码类型()
     */
    private String dnTag;

    private String serviceType;
    /**
     * 短信中心地址
     */
    private String smcgt;
    /**
     * imsi
     */
    private String imsi;
    /**
     * 号码状态
     */
    private String status;
    /**
     * 号码
     */
    private String dn;

    private String areaCode;
}
