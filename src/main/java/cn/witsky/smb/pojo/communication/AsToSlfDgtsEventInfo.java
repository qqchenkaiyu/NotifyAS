package cn.witsky.smb.pojo.communication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Administrator on 2017/7/21.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AsToSlfDgtsEventInfo {

    /** 控制指示*/
    private int control;

    /** 最小收集数字个数*/
    private int minCollect;

    /** 最大收集数字个数*/
    private int maxCollect;

    /** 等待收集完成总时长*/
    private int maxInteractTime;

    /** 等待首位数字超时时间*/
    private int initInterDgtTime;

    /** 两个数字输入之间的间隔时间*/
    private int normInterDgtTime;

    /** 应答结束数字*/
    private int enterDgtMask;

    /** 收号方式，可选*/
    private Integer digitCollectionType;


}
