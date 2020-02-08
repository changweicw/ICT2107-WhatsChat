package GroupChatApp;

public class Group {
    String grpName;
    String ip;
    int port;

    public Group(String grpName, String ip, int port) {
        this.grpName = grpName;
        this.ip = ip;
        this.port = port;
    }

    public Group(String grpName, String ip){
        this.port = 6789;
        this.grpName = grpName;
        this.ip = ip;
    }



    public String getGrpName() {
        return grpName;
    }

    public void setGrpName(String grpName) {
        this.grpName = grpName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

