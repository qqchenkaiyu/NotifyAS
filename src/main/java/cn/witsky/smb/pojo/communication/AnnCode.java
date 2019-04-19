package cn.witsky.smb.pojo.communication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by HuangYX on 2017/9/6 18:28.
 */
public class AnnCode {
    private static final Logger logger = LoggerFactory.getLogger(AnnCode.class);

    // 主叫提示音
    List<AsToSlfFilePosition> callerAnnCode;

    // 被叫提示音
    List<AsToSlfFilePosition> calledAnnCode;

    public AnnCode() {
    }

    public AnnCode(List<AsToSlfFilePosition> callerAnnCode, List<AsToSlfFilePosition> calledAnnCode) {
        this.callerAnnCode = callerAnnCode;
        this.calledAnnCode = calledAnnCode;
    }

    public List<AsToSlfFilePosition> getCallerAnnCode() {
        return callerAnnCode;
    }

    public void setCallerAnnCode(List<AsToSlfFilePosition> callerAnnCode) {
        this.callerAnnCode = callerAnnCode;
    }

    public List<AsToSlfFilePosition> getCalledAnnCode() {
        return calledAnnCode;
    }

    public void setCalledAnnCode(List<AsToSlfFilePosition> calledAnnCode) {
        this.calledAnnCode = calledAnnCode;
    }


    @Override
    public String toString() {
        return "AnnCode{" + "callerAnnCode=" + callerAnnCode + ", calledAnnCode=" + calledAnnCode + '}';
    }
}
