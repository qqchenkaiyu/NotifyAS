package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * @author HuangYX
 * @date 2019/1/8 15:42
 */
@Data
public class ApkExtCollectDgt {

  /**
   * 可选	BIT0：  1：中断语音通知播放 0：不中断语音通知播放 BIT1： 1：循环连续收号 0：不循环收号 BIT2： 1：首位数字上报 0：首位数字不上报
   */
  private Integer control;

  /** 可选	最小收集数字个数 */
  private Integer mincollect;
  /**
   * 可选	最大收集数字个数   >= 1   >=最小收集数字个数 <=128
   */
  private Integer maxcollect;

  /** 可选	等待收集数字完成的总时长   0：总时长没有限制 */
  private Integer maxinteracttime;
  /** 可选	等待首位数字超时时间 */
  private Integer initinterdgttime;
  /** 可选	两个数字输入之间的间隔时间  0：缺省值为5 SEC */
  private Integer norminterdgttime;
  /** 可选	收号结束按键1：*号键,2：#号键,3：*号或#号键 */
  private Integer enterdgtmask;
}
