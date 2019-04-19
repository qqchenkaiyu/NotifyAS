package cn.witsky.smb.Constant;

import cn.witsky.smb.core.constant.Str;

/**
 * Created by HuangYX on 2018/4/16 10:57.
 *
 * @author HuangYX
 */
public interface RecordMode {
    /** 0：不录音 */
    String NO_REC= Str.ZERO;
    /** 1：接通后录音 */
    String CONNECTED_REC= Str.ONE;
    /** 2：被叫响铃后录音 */
    String ALERTING_REC = Str.TWO;



    int TO_EASTCOM_CONNECTED_REC_MODE =3;
    int TO_EASTCOM_ALERTING_REC_MODE =131;
}
