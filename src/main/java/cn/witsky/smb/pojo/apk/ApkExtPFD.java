package cn.witsky.smb.pojo.apk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HuangYX
 * @date 2018/6/6 19:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApkExtPFD {

    /**
     * 扩展提示音项
     */
    private ApkExtTone  extTone;
    /**
     * 扩展业务参数项，由不同业务自行定义
     */
    private ApkExtSvc  extSvc;
}
