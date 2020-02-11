//package sample.BackendThreads;
//
//
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.InetAddress;
//import java.net.MulticastSocket;
//
//public class GroupThread extends Thread {
//    Group mygroup;
//    clientInfo ci;
//    groupchatGUI context;
//    volatile private boolean leftGroupThread;
//    MulticastSocket mcSocket;
//
//    public GroupThread(clientInfo ci, groupchatGUI context) {
//        this.mygroup = ci.getCurrentGroup();
//        this.ci = ci;
//        this.context = context;
//        this.leftGroupThread = false;
//    }
//
//    public void leaveGroup() {
//        this.leftGroupThread = true;
//        context.notInRoom();
//        System.out.println("Left group through function. Value: " + this.leftGroupThread);
//    }
//
//    @Override
//    public void run(){
//        try {
////            System.out.println("Joining Room " + ci.getCurrentGroup().getGrpName() +" on "
////            + ci.getCurrentGroup().getIp());
//            context.appendMsg("Entering "+ci.getCurrentGroup().getGrpName());
//            context.inRoom();
//
//            MulticastSocket groupSocket = new MulticastSocket(utils.port);
//            InetAddress multiCastGroup = InetAddress.getByName(ci.getCurrentGroup().getIp());
//            groupSocket.joinGroup(multiCastGroup);
//            byte[] buf1 = new byte[1000];
//            DatagramPacket dgpReceived = new DatagramPacket(buf1,buf1.length);
//            while(!leftGroupThread){
//
//                groupSocket.receive(dgpReceived);
//                byte[] receivedData = dgpReceived.getData();
//                int length = dgpReceived.getLength();
//                //Assumed we received string
//                String msg = new String(receivedData,0,length);
//                context.appendMsg(msg);
//            }
////            groupSocket.leaveGroup(multiCastGroup);
////            groupSocket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//}