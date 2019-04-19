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
public class QueryBody {
	String callNo;
	String secretNo;
	String extension;
	String recordType;
	String callTime;
	String callId;
	String requestId;
	String vendorKey;
	String v;
	String method;
	String timestamp;
	String sign;
}
