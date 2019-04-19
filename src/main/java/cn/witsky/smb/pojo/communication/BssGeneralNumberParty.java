package cn.witsky.smb.pojo.communication;

import lombok.Data;

/**
 * @author wangyuyang@witsky.cn
 * @date 2018/11/12 14:39
 */
@Data
public class BssGeneralNumberParty {

    /**
     * 主叫号码
     */
    private String dn;
    /**
     * 限制指定
     * ordinary:不限制，默认
     * suspend：限制
     */
    private String qualifierIndicator = "ordinary";

}
