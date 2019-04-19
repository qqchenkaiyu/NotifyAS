package cn.witsky.smb.controller;

import cn.witsky.smb.config.Config;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyuyang@witsky.cn
 * @date 2018/11/28 15:04
 */
@RestController
public class CommonController {

    @Autowired
    private Config config;
    /**
     * 心跳检测接口
     *
     * @return
     */
    @RequestMapping("/workingTest")
    public String workingTest() {
        int workingStatus = config.getWorkingStatus();
        JSONObject object = new JSONObject();
        object.put("code", workingStatus);
        return object.toJSONString();
    }
}
