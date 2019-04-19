package cn.witsky.smb.pojo.apk;

import lombok.Data;

import java.util.List;

/**
 * 业务规则
 * @author HuangYX
 * @date 2018/6/6 17:26
 */
@Data
public class ApkSvcCriteria {
    /**
     * 业务类型，比如AX、AXB
     */
    private String serviceType;

    /**
     * 是否排序
     */

    private transient boolean hasSorted;

    /**
     * 业务规则表
     */
    private List<ApkSubsequnentCriteria> serviceCriteria;
}
