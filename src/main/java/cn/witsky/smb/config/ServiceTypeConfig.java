package cn.witsky.smb.config;

import cn.witsky.smb.Constant.ServiceTypeCons;
import cn.witsky.smb.config.Config;
import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wangyuyang@witsky.cn
 * @date 2018/12/14 15:47
 */
@Component
@Data
public class ServiceTypeConfig {

    private String dnType;

    public ServiceTypeConfig(Config config) {
        Map<String, String> asTypeMap = Maps.newHashMap();

        asTypeMap.put(ServiceTypeCons.SERVICE_TYPE_AX2C, ServiceTypeCons.DN_TAG_AX2C);
        asTypeMap.put(ServiceTypeCons.SERVICE_TYPE_AXN, ServiceTypeCons.DN_TAG_AXN);
        asTypeMap.put(ServiceTypeCons.SERVICE_TYPE_AXX, ServiceTypeCons.DN_TAG_AXX);
        asTypeMap.put(ServiceTypeCons.SERVICE_TYPE_AXB, ServiceTypeCons.DN_TAG_AXB);
        asTypeMap.put(ServiceTypeCons.SERVICE_TYPE_AXG, ServiceTypeCons.DN_TAG_AXG);
        asTypeMap.put(ServiceTypeCons.SERVICE_TYPE_SPX, ServiceTypeCons.DN_TAG_SPX);
        asTypeMap.put(ServiceTypeCons.SERVICE_TYPE_AX2B, ServiceTypeCons.DN_TAG_AX2B);
        asTypeMap.put(ServiceTypeCons.SERVICE_TYPE_AXYB_AX, ServiceTypeCons.DN_TAG_AXYB_AX);
        asTypeMap.put(ServiceTypeCons.SERVICE_TYPE_AXYB, ServiceTypeCons.DN_TAG_AXYB);
        asTypeMap.put(ServiceTypeCons.SERVICE_TYPE_SOP, ServiceTypeCons.DN_TAG_SOP);
        asTypeMap.put(ServiceTypeCons.SERVICE_TYPE_ALIC, ServiceTypeCons.DN_TAG_ALIC);
        this.dnType = asTypeMap.get(config.getAsType());
    }
}
