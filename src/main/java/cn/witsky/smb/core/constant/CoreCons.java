package cn.witsky.smb.core.constant;

import java.text.*;

/**
 */
public interface CoreCons {

    String OK = "OK";

    String CODEUTF = "UTF-8";
    String EMPTY = "";
    String DENGYU = "=";
    String HE = "&";
    String MAOHAO = ":";
    String COMMA = ",";

    String SUXIAN = "[|]";

    String SIDSUXIAN = "|";
    String X = "X";
    String JIAN = "-";
    String XHX = "_";

    String A = "A";
    String B = "B";
    String RECORD_PCM = "pcm";
    String RECORD_AMR = "amr";
    String SUFFIX = ".";
    String KPI_LOG = "log_";

    DecimalFormat LONG_FORMATTER = new DecimalFormat("0000000000");
    DecimalFormat INT_FORMATTER = new DecimalFormat("0000");


    String SERVER_ADDRESS = "server.address";
    String SERVER_PORT = "server.port";
    String SLFIP = "witsky.slfip";
    String BACKSLASH = "/";

}
