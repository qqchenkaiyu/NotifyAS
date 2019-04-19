package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * 系统管控规则
 *
 * @author HuangYX
 * @date 2018/6/6 17:26
 */
@Data
public class ApkSysCtrlCriteria {
    /**
     * 系统呼叫管控规则
     */
    private ApkCriteria callCriteria;
    /**
     * 系统短信管控规则
     */
    private ApkCriteria smsCriteria;
}
