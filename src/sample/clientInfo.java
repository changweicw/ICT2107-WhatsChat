package GroupChatApp;

import java.util.ArrayList;

public class clientInfo {
    String username;
    ArrayList<Group> groupKnowledge;
    Group currentGroup;

    public clientInfo() {
        this.username = "";
        this.groupKnowledge = new ArrayList<>();
    }

    public clientInfo(String username,Group g) {
        this.username = username;
        this.groupKnowledge = new ArrayList<>();
        this.currentGroup = g;
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
