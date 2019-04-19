package cn.witsky.smb.controller;

import cn.witsky.smb.StateMachine.StateMachineForCollectAndConnect;
import cn.witsky.smb.StateMachine.StateMachineStore;
import cn.witsky.smb.config.Config;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Administrator
 */
@RestController()
@RequestMapping("${witsky.astoslf-mapping-url}")
public class StateMachineController {

  private static final Logger logger = LoggerFactory.getLogger(StateMachineController.class);
  private StateMachineStore stateMachineStore;
  private Config config;

  @Autowired
  public StateMachineController(Config config,StateMachineStore stateMachineStore) {
    this.config = config;
    this.stateMachineStore = stateMachineStore;
  }

  @RequestMapping(value = "/concurrencyQuery")
  public Object concurrencyQuery() {
    ArrayBlockingQueue<String> blockingQueue = stateMachineStore.getCallBlockingQueue();
    return  getResultFromQueue(blockingQueue);
  }
  @RequestMapping(value = "/concurrencyQuery/{taskID}")
  public Object concurrencyQuery(@PathVariable("taskID") String taskID) {
      HashMap<String, ArrayBlockingQueue<String>> callBlockingMap = stateMachineStore.getCallBlockingMap();
    ArrayBlockingQueue<String> blockingQueue = callBlockingMap.get(taskID);
return  getResultFromQueue(blockingQueue);
  }
  public JSONObject getResultFromQueue(ArrayBlockingQueue<String> blockingQueue) {
    JSONObject result = new JSONObject();
    result.put("currentCount",blockingQueue.size());
    JSONArray list = new JSONArray();

    for (String id :blockingQueue) {
      StateMachineForCollectAndConnect stateMachine = (StateMachineForCollectAndConnect) stateMachineStore.get(id);
      list.add(stateMachine.getCurrentState());
    }
    result.put("States",list);
    return result;
  }
  @RequestMapping("/sm/list")
  public List<String> getStateMachineList() {
    return stateMachineStore.getList();
  }

  @RequestMapping(value = "/sm/status/{id}", method = RequestMethod.GET)
  public Map<String, Object> getStateMachineStatus(@PathVariable String id) {

    return stateMachineStore.getStatus(id);
  }
}