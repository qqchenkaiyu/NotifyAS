package cn.witsky.smb.pojo.apk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangyuyang@witsky.cn
 * @date 2018/11/12 9:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApkExtSvc {

    /**
     * AYB的有效期
     */
    private String extAYBExpiration;
    /**
     * AYB的放音编号
     */
    private ApkExtAYBCallTone extAYBCallTone;
}
