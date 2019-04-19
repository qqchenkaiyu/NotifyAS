package cn.witsky.smb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author HuangYX
 * @date 2019/1/5 16:26
 */
public class BasicTest {

  private static final Logger logger = LoggerFactory.getLogger(BasicTest.class);

  String bindKey = "AXYB:00";
  String xyb = "{\"aAX\":\"100\",\"aBX\":\"200\",\"aBed\":\"201\",\"aOX\":\"300\",\"apk\":\"SHUC_0001\",\"bId\":\"AX0773X2018100000-1-0-SHUC-GXI\",\"bTs\":\"20180607100920668\",\"control\":\"0\",\"dur\": 300,\"exp\":\"20190107100920668\",\"nA\":\"18607720001\",\"nB\":\"18607740001\",\"nX\":\"18607730001\",\"pCDS\":\"0\",\"pCPU\":\"1\",\"pCUB\":\"0\",\"pRPU\":\"1\",\"r\":\"2\",\"rId\":\"apo2am0607101004015\",\"remark\":\"remark010010\",\"sMC\":\"3\",\"xArea\":\"0773\"}";
  String ax = "{\"aAX\":\"100\",\"aAed\":\"101\",\"aOX\":\"300\",\"apk\":\"SHUC_0001\",\"bId\":\"AX0773X2018100000-1-0-SHUC-GXI\",\"bTs\":\"20180607100920668\",\"control\":\"0\",\"dAX\":\"0\",\"dOX\":\"1\",\"dur\": 300,\"exp\":\"20190107100920668\",\"nA\":\"18607710001\",\"nX\":\"18607720001\",\"pCDS\":\"0\",\"pCPU\":\"0\",\"pCUB\":\"0\",\"pRPU\":\"0\",\"r\":\"1\",\"rId\":\"apo2am0607101004015\",\"remark\":\"remark010010\",\"restrict\":\"0\",\"sMC\":\"1\",\"xArea\":\"0772\",\"requestSK\":[\"111\",\"10\"]}";
  String autoBid = "{\"aAX\":\"100\",\"aBX\":\"200\",\"aBed\":\"201\",\"aOX\":\"300\",\"apk\":\"SHUC_0001\",\"bId\":\"AuBindX0773X2018100000-1-0-SHUC-GXI\",\"bTs\":\"20180607100920668\",\"control\":\"1\",\"dur\": 300,\"exp\":\"20190107100920668\",\"nA\":\"18607720001\",\"nB\":\"18607750001\",\"nX\":\"18607730002\",\"pCDS\":\"1\",\"pCPU\":\"1\",\"pCUB\":\"1\",\"pRPU\":\"0\",\"r\":\"2\",\"rId\":\"apo2am0607101004015111\",\"remark\":\"remark010011110\",\"sMC\":\"1\",\"xArea\":\"0773\"}";

  String onlineKey = "ONLINE:CALL:AX0773X2018100000-1-0-SHUC-GXI";
  String onlineRid = "requestId   onlinecallRequestId201812";
  String calledDn = "calledDn   18607750001";

  String sopenKsy = "SOPEN:SHUC:186:18607720009";
  String sopenApk = "appkey   SHUC_0001";
  String sopenmode = "mode   3";
  String sopenrequestId = "requestId   11121123eRID";
  String sopenqueryInfo = "queryInfo   http://192.168.150.233:7003/soepn/query";
  String sopenreplayAnnInfo = "playAnnInfo   asdaplayAnnInfo";
  String sopenretransfermInfo = "transfermInfo   {\"anncode\":\"annCode119\",\"control\":\"1\",\"display\":\"0\",\"dn\":\"19805710001\",\"r\":\"1\",\"smsService\":\"1\"}";

  String bind_ax_key = "X:BIND:186:18607720001";
  String bind_ax_key18607710001 = "18607710001 AX0773X2018100000-1-0-SHUC-GXI "
      + " axBid AX0773X2018100000-1-0-SHUC-GXI "
      + " xyb_18607740001 CX0773X2018100000-1-0-SHUC-GXI"
      + " xyb_18607750001-TMP AuBindX0773X2018100000-1-0-SHUC-GXI";
  String bind_xyb_key = "X:BIND:186:18607730001";
  String bind_xyb_values = "18607720001 CX0773X2018100000-1-0-SHUC-GXI "
      + " 18607740001 CX0773X2018100000-1-0-SHUC-GXI "
      + " ax_18607710001 AX0773X2018100000-1-0-SHUC-GXI ";
  String bind_autobind_key = "X:BIND:186:18607730002";
  String bind_autobind_values = "18607720001 AuBindX0773X2018100000-1-0-SHUC-GXI "
      + " 18607750001 AuBindX0773X2018100000-1-0-SHUC-GXI "
      + " ax_18607710001 AX0773X2018100000-1-0-SHUC-GXI ";





}
