package cn.witsky.smb.pojo.communication;

import lombok.Data;

/**
 * @author wangyuyang@witsky.cn
 * @date 2018/11/12 14:38
 */
@Data
public class BssAnnToneInfo {

    /**
     * 给主叫侧的提示音
     */
    private String origTone;
    /**
     * 给被叫侧接通后的提示音
     */
    private String termTone;

}
