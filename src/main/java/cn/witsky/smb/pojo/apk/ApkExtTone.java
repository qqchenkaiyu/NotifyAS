package cn.witsky.smb.pojo.apk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangyuyang@witsky.cn
 * @date 2018/11/12 9:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApkExtTone {

    /**
     * 扩展按键收号的提示音
     */
    private ApkExtCollectTone extCollectTone;

    private ApkExtCollectDgt extCollectDgts;
}
