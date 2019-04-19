package cn.witsky.smb.util;


import cn.witsky.smb.core.constant.DateFormaters;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Administrator on 2019/4/12.
 */
public class LogTimeUtil {
	static {
		dateTimeFormatter = DateFormaters.YYYYMMDDHHMMSSSSS.withZoneUTC();
	}
	static DateTimeFormatter dateTimeFormatter;
	//获得格林威治时间  UTC  北京时间是UBT+8  东八区
	public static String getTime() {
		return dateTimeFormatter.print(System.currentTimeMillis());
	}
}
