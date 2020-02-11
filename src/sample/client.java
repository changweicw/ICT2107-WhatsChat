package GroupChatApp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class client extends Thread{

    clientInfo ci;
    groupchatGUI context;

    public client(clientInfo ci, groupchatGUI context) {
        this.ci = ci;
        this.context = context;
    }

    public void run(){
        try{
            GroupThread gt;
            MulticastSocket mySocket = new MulticastSocket(utils.port);
            InetAddress myGroup = InetAddress.getByName(utils.dedicatedIP);
            mySocket.joinGroup(myGroup);
            System.out.println("Joined Multicast group: "+utils.dedicatedIP+"\n");

            byte buf1[] = new byte[1000];
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
                    case utils.joinSearchIpByNameCommand:
                        System.out.println("join search ip command ran.");

//                        if(msgArray[1].equals(ci.getUsername())) break;
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
                        System.out.println("createsearch ip command ran.");

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
                            context.appendMsg("Group already exists");
                        }
                        break;
                    case utils.foundIPCommand:
                        System.out.println("Found ip command ran.");
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
                        gt = new GroupThread(ci,context); //New groupthread, passing in groupchatGUI context and clientInfo object
                        context.setGroupThread(gt);
                        gt.start(); //Run the thread

                        context.inRoom(); //toggle all buttons

                        break;
                    case utils.noIPFoundCommand:
//                        if(!msgArray[1].equals(ci.getUsername())) break;
//                        context.appendMsg("Group "+msgArray[2]+" is not found.");

                        break;
                    case utils.createCommand:
                        System.out.println("create command ran.\n"+msgArray[1]+","+ci.getUsername());

                        if(!msgArray[1].equals(ci.getUsername())) break;
                        if(ci.getCurrentGroup()!=null) break;
                        Group newGroup = new Group(msgArray[2],utils.getRandomSubnet());
                        ci.setCurrentGroup(newGroup);
                        ci.addGroupObj(newGroup);
                        gt = new GroupThread(ci,context); //New groupthread, passing in groupchatGUI context and clientInfo object
                        context.setGroupThread(gt);
                        gt.start(); //Run the thread
                        context.inRoom();
                        break;
                    case utils.leaveCommand:
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
