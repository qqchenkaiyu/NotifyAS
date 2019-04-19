package cn.witsky.smb.pojo.communication;

import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */
public class AsToSlfRecordInfo {
    // 录音模式
   private int mode;

    // 录音名称字冠
    private List<String> namePrefixes;

    public AsToSlfRecordInfo() {
    }

    public AsToSlfRecordInfo(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public List<String> getNamePrefixes() {
        return namePrefixes;
    }

    public void setNamePrefixes(List<String> namePrefixes) {
        this.namePrefixes = namePrefixes;
    }

    @Override
    public String toString() {
        return "{mode=" + mode +
                ", namePrefixes=" + namePrefixes +
                '}';
    }
}
