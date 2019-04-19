package cn.witsky.smb.util;

import cn.witsky.smb.Constant.Cons;
import cn.witsky.smb.config.Config;
import cn.witsky.smb.core.utils.Utilities;
import cn.witsky.smb.pojo.ContextData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2019/4/9.
 */
@Component
public class HttpUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	private  RestTemplate restTemplate;
	private  RetryTemplate retryTemplate;
	private  Config config;
	private final ThreadPoolTaskExecutor taskExecutor;
	private static TypeReference<Map<String, Object>> TYPE_REFERENCE_FOR_STRING_KEYED_MAP = new TypeReference<Map<String, Object>>() {
	};

@Autowired
	public HttpUtil(RestTemplate restTemplate, RetryTemplate retryTemplate, Config config,@Qualifier("internalHttp") ThreadPoolTaskExecutor taskExecutor) {
		this.restTemplate=restTemplate;
		this.retryTemplate=retryTemplate;
		this.config=config;
		this.taskExecutor=taskExecutor;

	}

	public  Map<String, Object> postData(String Url, Object Data, ContextData contextData) {
		return JSON.parseObject(postDataForString(Url, Data, contextData), TYPE_REFERENCE_FOR_STRING_KEYED_MAP);
	}
	public ListenableFuture<Map<String, Object>>  postDataAsync(String Url, Object Data, ContextData contextData) {
		return taskExecutor.submitListenable(() -> {
			//这里写一个返回体
			return postData(Url,Data,contextData);
		});
		//return JSON.parseObject(postDataForString(Url,Data,contextData), TYPE_REFERENCE_FOR_STRING_KEYED_MAP);
	}
	public ListenableFuture<String>  postDataForStringAsync(String Url, Object Data, ContextData contextData) {
		return taskExecutor.submitListenable(() -> {
			//这里写一个返回体
			return postDataForString(Url,Data,contextData);
		});
	}
	public  String postDataForString(String Url, Object Data, ContextData contextData) {
		//initTemplate();
		if (Data == null) {
			logger.error("连接slf时请求体为null");
			return null;
		}
		//logger.info("servicePlayAndCollect,Url={},data={}", Url, Data);
		ResponseEntity<String> responseEntity = retryTemplate.execute(new RetryCallback<ResponseEntity<String>, RuntimeException>() {

			@Override
			public ResponseEntity<String> doWithRetry(RetryContext retryContext) throws RuntimeException {

				HttpEntity<String> httpEntityForJson = Utilities.createHttpEntityForJson(JSON.toJSONString
						(Data),getHeader(contextData,Url));
				//HttpEntity<String> httpEntityForJson = Utilities.createHttpEntityForJson(dired);
				ResponseEntity<String> exchange = restTemplate.exchange(Url, HttpMethod.POST, httpEntityForJson, String.class);
				return exchange;
			}
		}, new RecoveryCallback<ResponseEntity<String>>() {

			@Override
			public ResponseEntity<String> recover(RetryContext retryContext) throws Exception {

				logger.error("多次访问失败,retryContext={},url={}", retryContext,Url);
				return null;
			}
		});
		if (responseEntity != null) {
			HttpStatus httpStatus = responseEntity.getStatusCode();
			if (httpStatus != null && httpStatus == HttpStatus.OK) {
				return responseEntity.getBody();
			}
		}
		return null;
	}
	private  Map<String,String> getHeader(ContextData contextData,String Url) {
		HashMap<String, String> headers = Maps.newHashMap();
		headers.put(Cons.HeaderFiled.XDR_ID, contextData.getLocalSessionId());
		headers.put(Cons.HeaderFiled.GLOBAL_ID, contextData.getGlobalId());
		headers.put(Cons.HeaderFiled.DIALOG_FROM, config.getModuleInstanceId());
		String[] split = Url.split("//");
		headers.put(Cons.HeaderFiled.TEMP_HEADER_TYPE, split[split.length-1]);
		return headers;
	}

}
