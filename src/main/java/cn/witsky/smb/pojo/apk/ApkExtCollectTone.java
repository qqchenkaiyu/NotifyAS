package cn.witsky.smb.pojo.apk;

import lombok.*;

/**
 * @author wangyuyang@witsky.cn
 * @date 2018/11/12 9:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApkExtCollectTone {

    /**
     * 初始放音编号，仅放一次；如果是0或不出现，则跳过这个放音
     */
    private String inittone;
    /**
     * 初始收号等待时间
     */
    private Integer initwaittime;
    /**
     * 收号时的放音编号
     */
    private String collecttone;
    /**
     * 收号的次数
     */
    private Integer collectcount;
    /**
     * 收号的等地时间
     */
    private Integer collectwaittime;

    /**
     * 收号无效提示音
     */
    private String illegaltone;

}
