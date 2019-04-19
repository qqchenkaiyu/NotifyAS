package cn.witsky.smb.pojo.communication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by HuangYX on 2017/10/18 16:15.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cause {

    // 0: Q.850; 1: 非Q.850
    private Integer plan;

    // 原因值
    private Integer reason;

}
