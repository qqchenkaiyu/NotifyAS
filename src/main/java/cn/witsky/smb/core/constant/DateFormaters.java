package cn.witsky.smb.core.constant;

import org.joda.time.format.*;

/**
 *
 */
public interface DateFormaters {

  String STR_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
  String STR_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
  String STR_YYYYMMDDHHMM = "yyyyMMddHHmm";
  String STR_YYYYMMDD = "yyyyMMdd";
  String STR_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
  String STR_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

  DateTimeFormatter DD = DateTimeFormat.forPattern("dd");
  DateTimeFormatter YYYYMMDDHHMMSS = DateTimeFormat.forPattern(STR_YYYYMMDDHHMMSS);
  DateTimeFormatter YYYYMMDDHHMM = DateTimeFormat.forPattern(STR_YYYYMMDDHHMM);
  DateTimeFormatter YYYYMMDDHHMMSSSSS = DateTimeFormat.forPattern(STR_YYYYMMDDHHMMSSSSS);
  DateTimeFormatter YYYY_MM_DD_HH_MM_SS_SSS = DateTimeFormat.forPattern(STR_YYYY_MM_DD_HH_MM_SS_SSS);
  DateTimeFormatter TIME_FORMAT = DateTimeFormat.forPattern(STR_YYYY_MM_DD_HH_MM_SS);
  DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormat.forPattern(STR_YYYY_MM_DD_HH_MM_SS);

}
