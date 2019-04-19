package cn.witsky.smb.pojo.communication;

import lombok.Data;

/**
 * @author wangyuyang@witsky.cn
 * @date 2018/11/12 14:32
 */
@Data
public class BssTriggerDetection {

    /**
     * 返回码
     */
    private String code;
    /**
     * 返回码描述
     */
    private String message;
    /**
     * "release"：释放
     "continue"：继续
     "connect"：接续
     */
    private String action;
    /**
     * 提示音信息
     */
    private BssAnnToneInfo annToneInfo;
    /**
     * 目的号码
     */
    private String destinationNumber;
    /**
     * 通用号码部分
     */
    private BssGeneralNumberParty generalNumberParty;
    /**
     * 短信中心地址
     */
    private String smcaddress;

}
