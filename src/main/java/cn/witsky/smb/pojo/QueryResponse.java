package cn.witsky.smb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2019/4/12.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryResponse {
	String code;
	String message;
	String success;
	String module;
	String request_id;
	String sign;

	String request_body;
	QueryBody query_Body;
}
