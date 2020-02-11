package sample.BackendThreads;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class clientInfo {
    String username;
    String clientId;
    ArrayList<Group> groupKnowledge;
    Group currentGroup;
    HashSet<String> userList;

    public clientInfo(String clientId) {
        this.username = "";
        this.clientId=clientId;
        this.groupKnowledge = new ArrayList<>();
        this.userList = new HashSet<>();
    }

    public String searchUsers(String u){
        Iterator it = userList.iterator();
        while(it.hasNext()){
            String tempString = it.next().toString();
            if(tempString.equals(u)) return tempString;
        }
        return null;
    }


    public String searchGroupIpByName(String nameToSearch){
        for (Group g : this.groupKnowledge){
            if(g.getGrpName().equals(nameToSearch)){
                return g.getIp();
            }
        }
        return null;
    }

    //ADD GROUP TO LIST
    public int addGroupObj(Group g){
        int retFlag=0;

        for(Group g_loop : groupKnowledge){
            if(g_loop.getGrpName().equals(g.grpName)){
                return 1;
            }
        }
        groupKnowledge.add(g);
        return 0;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setUserList(HashSet<String> userList) {
        this.userList = userList;
    }

    public HashSet<String> getUserList() {
        return userList;
    }

    public String getUserListInString() {
        String temp = "";
        Iterator it = this.userList.iterator();
        StringBuilder sb = new StringBuilder();
        while(it.hasNext()){
            sb.append(it.next()).append("\n");
        }
        String retString = sb.toString();
        return retString;
    }

    public void addUserToList(String name) {

        this.userList.add(name);
    }


    public void clearCurrentGroup(){
        this.currentGroup = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Group> getGroupKnowledge() {
        return groupKnowledge;
    }

    public void setGroupKnowledge(ArrayList<Group> groupKnowledge) {
        this.groupKnowledge = groupKnowledge;
    }

    public Group getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(Group currentGroup) {
        this.currentGroup = currentGroup;
    }
}
