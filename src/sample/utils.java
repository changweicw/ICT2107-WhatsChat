package GroupChatApp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Random;

public class utils {

    final public static String joinSearchIpByNameCommand = "10";
    final public static String createSearchIpByNameCommand = "01";
    final public static String foundIPCommand = "1";
    final public static String noIPFoundCommand = "2";
    final public static String createCommand = "3";
    final public static String leaveCommand = "4";
    final public static String grpExistCommand = "5";
    final public static String dedicatedIP = "228.1.1.1";
    final public static int port = 6789;

    public static String getRandomSubnet(){
        Random ran = new Random();
        String randomSubnet = "228.1.";
        int first255 = ran.nextInt(255)+1;
        int second255 = ran.nextInt(255) +1;
        randomSubnet = randomSubnet + first255 + "." + second255;
        return randomSubnet;
    }

    public static DatagramPacket createDatagramPacket(String s, InetAddress multicastGroup, int port){
        byte[] buf = s.getBytes();
        DatagramPacket dgpacket = new DatagramPacket(buf,buf.length,multicastGroup,port);
        return dgpacket;
    }

    public static String createPacketMessage(String... s){
        String retString = "";
        int counter =0;
        for (String tempStr:s){
            if (counter==0){
                retString = retString.concat(tempStr);
            } else {
                retString = retString.concat(";"+tempStr);
            }
            counter++;
        }
        return retString;
    }
}
