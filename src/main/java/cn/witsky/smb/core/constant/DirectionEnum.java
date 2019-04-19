package cn.witsky.smb.core.constant;

/**
 * @author HuangYX
 * @date 2018/5/28 10:25
 */
public enum DirectionEnum {
    IN,INTERNAL,OUT;

     DirectionEnum() {
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
