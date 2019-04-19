package cn.witsky.smb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryData {
	@NotBlank(message="版本不能为空")
	String v;
	@NotBlank(message="method不能为空")
	String method;
		@NotBlank(message="vendor_access_key不能为空")
	String vendor_access_key;
	@NotBlank(message="timestamp不能为空")
	String timestamp;
	@NotBlank(message="request_id不能为空")
	String request_id;
	@NotBlank(message="sign不能为空")
	String sign;
	@NotBlank(message="request_body不能为空")
	String request_body;
	QueryBody query_Body;
}
