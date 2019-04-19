package cn.witsky.smb.pojo.apk;

import lombok.Data;

import java.util.List;

/**
 * @author HuangYX
 * @date 2018/6/6 17:23
 */
@Data
public class ApkExtCriteriaTable {
    /**
     * 系统管控规则
     */
    private ApkSysCtrlCriteria tp1;
    /**
     * 业务规则
     */
    private List<ApkSvcCriteria> tp2;
    /**
     * 呼叫来显规则
     */
    private ApkCidCriteria tp3;
}
