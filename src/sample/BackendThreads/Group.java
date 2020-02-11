package sample.BackendThreads;

import sample.HelperFunctions.utils;

public class Group {
    String grpName;
    String ip;
    String hostName;
    int port;

    public Group(String grpName, String ip,String hostName, int port) {
        this.grpName = grpName;
        this.hostName = hostName;
        this.ip = ip;
        this.port = port;
    }

    public Group(String grpName, String ip,String hostName) {
        this.grpName = grpName;
        this.hostName = hostName;
        this.ip = ip;
        this.port = utils.port;
    }

    public Group(String grpName, String ip){
        this.port = utils.port;
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

