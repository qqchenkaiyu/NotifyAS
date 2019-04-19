package cn.witsky.smb.pojo.apk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HuangYX
 * @date 2018/6/6 17:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApkServiceRouteTable {

    /**
     * as路由地址
     */
    private ApkRoute asRoute;
    /**
     * 容灾as路由地址
     */
    private ApkRoute recoveryRoute;
}
