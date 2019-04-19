package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * 业务单元列表
 * @author HuangYX
 * @date 2018/6/6 18:57
 */
@Data
public class ApkSU {
    /**
     * 业务类型，比如AX、AXB、AXx等
     */
    private String serviceType;
    /**
     * 业务功能描述符
     */
    private ApkSFD sfd;
}
