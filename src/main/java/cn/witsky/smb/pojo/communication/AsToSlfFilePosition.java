package cn.witsky.smb.pojo.communication;

/**
 * Created by Administrator on 2017/7/21.
 */
public class AsToSlfFilePosition {
    // 文件类型
    private int fileType;

    // 服务器IP地址
    private long ipAddressOfServer;

    // 服务器端口号
    private int portOfServer;

    // 强制下载标识
    private int forceLoading;

    // 文件名
    private String fileName;


    public AsToSlfFilePosition() {
    }

    public AsToSlfFilePosition(int fileType, long ipAddressOfServer, int portOfServer, int forceLoading, String fileName) {
        this.fileType = fileType;
        this.ipAddressOfServer = ipAddressOfServer;
        this.portOfServer = portOfServer;
        this.forceLoading = forceLoading;
        this.fileName = fileName;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public long getIpAddressOfServer() {
        return ipAddressOfServer;
    }

    public void setIpAddressOfServer(long ipAddressOfServer) {
        this.ipAddressOfServer = ipAddressOfServer;
    }

    public int getPortOfServer() {
        return portOfServer;
    }

    public void setPortOfServer(int portOfServer) {
        this.portOfServer = portOfServer;
    }

    public int getForceLoading() {
        return forceLoading;
    }

    public void setForceLoading(int forceLoading) {
        this.forceLoading = forceLoading;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public String toString() {
        return "AsToSlfFilePosition{" +
                "fileType=" + fileType +
                ", ipAddressOfServer=" + ipAddressOfServer +
                ", portOfServer=" + portOfServer +
                ", forceLoading=" + forceLoading +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
