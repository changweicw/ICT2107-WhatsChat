package sample.BackendThreads;

import sample.Controllers.LoginController;
import sample.Controllers.MainController;
import sample.HelperFunctions.utils;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class client extends Thread{

    clientInfo ci;
    MainController context;

    public client(clientInfo ci, MainController context) {
        this.ci = ci;
        this.context = context;
    }
    public client(clientInfo ci) {
        this.ci = ci;
        this.context = null;
    }


    public void run(){
        try{
//            GroupThread gt;
            MulticastSocket mySocket = new MulticastSocket(utils.port);
            InetAddress myGroup = InetAddress.getByName(utils.dedicatedIP);
            mySocket.joinGroup(myGroup);
            System.out.println("Joined Multicast group: "+utils.dedicatedIP+"\n");
//            ci.addUserToList(new User(ci.getUsername()));
            byte[]  buf1 = new byte[1000];
            DatagramPacket dgpReceived = new DatagramPacket(buf1,buf1.length);
            while(true){
                mySocket.receive(dgpReceived);
                byte[] receivedData = dgpReceived.getData();
                int length = dgpReceived.getLength();
                //Assumed we received string
                String msg = new String(receivedData,0,length);
                String[] msgArray = msg.split(";");
                String command = msgArray[0];

                switch(command){
                    case utils.getAllGroupsCommand:

                        break;
                    case utils.newGroupCreatedCommand:

                        break;
                    case utils.joinSearchIpByNameCommand:
//                        System.out.println("join search ip command ran.");

                        String host = ci.searchGroupIpByName(msgArray[2]);
                        if (host!=null){
                            msg = utils.createPacketMessage(
                                    utils.foundIPCommand,
                                    msgArray[1],
                                    msgArray[2],
                                    host
                            );
                            DatagramPacket packet = utils.createDatagramPacket(
                                    msg,
                                    InetAddress.getByName(utils.dedicatedIP),
                                    utils.port
                            );
                            mySocket.send(packet);

                        }
                        break;
                    case utils.createSearchIpByNameCommand:
//                        System.out.println("createsearch ip command ran.");

//                        if(msgArray[1].equals(ci.getUsername())) break;
                        String grp = ci.searchGroupIpByName(msgArray[2]);
                        if (grp==null){
                            msg = utils.createPacketMessage(
                                    utils.createCommand,
                                    msgArray[1],
                                    msgArray[2],
                                    grp
                            );
                            DatagramPacket packet = utils.createDatagramPacket(
                                    msg,
                                    InetAddress.getByName(utils.dedicatedIP),
                                    utils.port
                            );

                            mySocket.send(packet);

                        } else {
                            context.popup("Too slow!","Group already exists");
                        }
                        break;
                    case utils.foundIPCommand:
//                        System.out.println("Found ip command ran.");
                        String username = msgArray[1];
//                        if(!username.equals(ci.getUsername()) || ci.getCurrentGroup()!=null) {
//                            break;
//                        }
                        if(ci.getCurrentGroup()!=null) break;
                        if(!msgArray[1].equals(ci.getUsername())) break;
                        String grpName = msgArray[2];
                        String grpIP = msgArray[3];
                        Group g = new Group(grpName,grpIP); //Create new group object
                        ci.setCurrentGroup(g); //Set the current group user is in
                        ci.addGroupObj(g); //Add into the clientInfo arraylist
//                        gt = new GroupThread(ci,context); //New groupthread, passing in groupchatGUI context and clientInfo object
//                        context.setGroupThread(gt);
//                        gt.start(); //Run the thread

//                        context.inRoom(); //toggle all buttons

                        break;
                    case utils.noIPFoundCommand:
//                        if(!msgArray[1].equals(ci.getUsername())) break;
//                        context.appendMsg("Group "+msgArray[2]+" is not found.");

                        break;
                    case utils.createCommand:
//                        System.out.println("create command ran.\n"+msgArray[1]+","+ci.getUsername());

                        if(!msgArray[1].equals(ci.getUsername())) break;
                        if(ci.getCurrentGroup()!=null) break;
                        Group newGroup = new Group(msgArray[2],utils.getRandomSubnet(),msgArray[1]);
                        ci.setCurrentGroup(newGroup);
                        ci.addGroupObj(newGroup);
//                        gt = new GroupThread(ci,context); //New groupthread, passing in groupchatGUI context and clientInfo object
//                        context.setGroupThread(gt);
//                        gt.start(); //Run the thread
//                        context.inRoom();
                        break;
                    case utils.leaveCommand:
                        break;
                    case utils.getUserListCommand:
                        System.out.println("Inside getUserListCommand");
                        //This command should only be for people that just joined and want to
                        //update their own UI user list with the current user list
                        System.out.println("getUserList command ran");
                        String tempReturnData = "";
                        if (ci.getUserList().size() > 0) {
                            StringBuilder tempReturnDataBuilder = new StringBuilder();
                            for (String user : ci.getUserList()) {
                                tempReturnDataBuilder.append(user).append(",");
                            }
                            tempReturnData = tempReturnDataBuilder.toString();
                            tempReturnData = tempReturnData.substring(0, tempReturnData.length() - 1);
                        }

                        msg = utils.createPacketMessage(
                                utils.addUserToClientListCommand,
                                msgArray[1],
                                tempReturnData
                        );
                        DatagramPacket packet = utils.createDatagramPacket(
                                msg,
                                InetAddress.getByName(utils.dedicatedIP),
                                utils.port);

                        mySocket.send(packet);

                        break;
                    case utils.addUserToClientListCommand:
                        System.out.println("inside addusertoclient command");
                        if(!msgArray[1].equals(ci.getClientId())) break;
                        if(msgArray.length<=2) break;
                        String userList = msgArray[2];
                        for(String user:userList.split(",")){
                            if(!user.isEmpty()){
                                ci.addUserToList(user);
                            }
                        }
//                        context.popup("User List",ci.getUserListInString());
                        System.out.println(ci.getUserListInString());

//                        System.out.println(ci.getUserList().size());
                        break;
                    case utils.updateUserListCommand:
//                        context.appendUser(ci.getUserListInString());
                        break;
                    case utils.newUserJoinedCommand:
                        System.out.println("Inside new user joined command");
                        String newUser = msgArray[2];
                        ci.addUserToList(newUser);
                        System.out.println("These are the new users"+ci.getUserListInString());
//                        context.popup("fuck",ci.getUserListInString());
//                        context.appendUser(ci.getUserListInString());
                        break;
                    case utils.addUsersToGroupCommand:
                        //msgArray[0] = Command
                        //msgArray[1] = Origin user
                        //msgArray[2] = group name to join
                        //msgArray[3] = group ip
                        //msgArray[4] = Users to add, seperated with commas
                        String grpToJoin = msgArray[2];
                        String[] usersToAdd = msgArray[3].split(",");
                        if(Arrays.asList(usersToAdd).contains(ci.getUsername())){
                            //Perform add to group here
                            Group tempGroup = new Group(msgArray[2],msgArray[3],msgArray[1],utils.port);
                            ci.addGroupObj(tempGroup);
                        }

                        break;
                    default:
                        break;

                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
