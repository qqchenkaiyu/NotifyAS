package cn.witsky.smb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

/**
 * voiceNotify 接受到的请求体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallData {
	@NotBlank(message="appkey不能为空")
	String appkey;
	@NotBlank(message="ts不能为空")
	String ts;
//	@NotBlank(message="msgdgt不能为空")
	String msgdgt;
	@NotBlank(message="requestid不能为空")
	String requestid;
	@NotBlank(message="taskid不能为空")
	String taskid;
	String notifyurl;
	@NotBlank(message="content不能为空")
	String content;
	@NotBlank(message="voicecode不能为空")
	String voicecode;
	String repeattimes;
	String repeatcondition;
	String repeattone;
	@NotBlank(message="displaynumber不能为空")
	String displaynumber;
	@NotNull(message = "calledpartylist不能为空")
	@Size(min = 1, message = "calledpartylist至少要有一个号码")
	ArrayList<String> calledpartylist;

}
