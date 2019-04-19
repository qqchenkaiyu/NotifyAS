package cn.witsky.smb.pojo.communication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Administrator
 * @date 2017/7/21
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SlfRedirNumberInfo {

    /**
     * 改发号码
     */
    private String number;
    /**
     * 改发原因
     */
    private Integer reason;
    private Integer len ;
    private Integer tag;

    public SlfRedirNumberInfo(String number, Integer reason) {
        this.number = number;
        this.reason = reason;
    }
}
