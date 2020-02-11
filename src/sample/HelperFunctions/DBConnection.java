package sample.HelperFunctions;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;

public class DBConnection {

    static java.sql.Connection connection = null;
    static String databaseName = "1801016TZA";
    static String url = "jdbc:mysql://rm-gs5c889f8g6s7c80vso.mysql.singapore.rds.aliyuncs.com/" + databaseName;

    static String username = "1801016TZA";
    static String password = "Password123";

    public void storeMessage(String accountID, String groupName, String message)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url, username, password);

        String sql = "Select * from chatGroup where groupName = '" + groupName + "'";
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery(sql);
        int groupID = 0;

        while (result.next()) {
            groupID = result.getInt(1);

        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO Message (MessageText, msgTimeStamp, Account_idAccount, Group_idGroup) VALUE ('" + message
                        + "','" + timestamp + "','" + accountID + "','" + groupID + "')");

        int status = ps.executeUpdate();

        if (status != 0) {
            System.out.print("Success");
        }
    }

    public ArrayList<String> retrieveMessage(String groupName)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url, username, password);

        String getGroupID = "SELECT * FROM chatGroup WHERE groupName = '" + groupName + "'";
        Statement st1 = connection.createStatement();
        ResultSet result1 = st1.executeQuery(getGroupID);
        int groupID = 0;

        while (result1.next()) {
            groupID = result1.getInt(1);
        }

        ArrayList<String> listOfOldMsg = new ArrayList<String>();

        String sql = "SELECT * FROM Message WHERE Group_idGroup = '" + groupID + "'";
        Statement st2 = connection.createStatement();
        ResultSet rs2 = st2.executeQuery(sql);
        System.out.println(sql);

        while (rs2.next()) {
            String result2 = rs2.getString(4) + ": " + rs2.getString(2);
            System.out.println(result2);
            listOfOldMsg.add(result2);
        }
        return listOfOldMsg;
    }

    public void updateAccountProfile(FileInputStream picture, String description, String userID)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url, username, password);

        PreparedStatement ps = connection
                .prepareStatement("INSERT INTO account (profilePicture, profileDesc) VALUE (?,?) WHERE idAccount = ?");
        ps.setBinaryStream(1, picture);
        ps.setString(2, description);
        ps.setString(3, userID);

        int status = ps.executeUpdate();

        if (status != 0) {
            System.out.print("Success");
        }
    }

    public ImageIcon getAccountPicture(String userID)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url, username, password);

        String selectAccount = "SELECT profilePiture FROM Account WHERE idAccount = '" + userID + "'";
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery(selectAccount);
        Blob blob = null;

        while (result.next()) {
            blob = result.getBlob(0);
        }
        ImageIcon imageIcon = new ImageIcon(blob.getBytes(1, (int) blob.length()));
        return imageIcon;

    }

    public String getAccountDescription(String userID)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        String selectAccount = "SELECT profileDesc FROM Account WHERE idAccount = '" + userID + "'";
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery(selectAccount);
        String desc ="";
        while (result.next()) {
            desc = result.getString(0);
        }
        return desc;

    }

    public static void createAccount(String userID)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url, username, password);
        PreparedStatement ps = connection.prepareStatement("INSERT INTO account (idAccount) VALUE (?)");
        ps.setString(1, userID);

        int status = ps.executeUpdate();

        if (status != 0) {
            System.out.print("Success");
        }
    }

    public boolean checkAccount(String userID)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url, username, password);

        String checkAccountSql = "SELECT * FROM Account WHERE idAccount = '" + userID + "'";
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery(checkAccountSql);

        // If there is result, return true
        if (result.next() == false) {
            return false;
        } else {
            return true;
        }
    }

    public void createGroup(String groupName)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url, username, password);
        PreparedStatement ps = connection.prepareStatement("INSERT INTO chatGroup (groupName) VALUE (?)");

        ps.setString(1, groupName);

        int status = ps.executeUpdate();

        if (status != 0) {
            System.out.print("Success");
        }
    }
    public ArrayList<String> getJoinedGroup(String userID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url, username, password);
        ArrayList<String> listOfGroup = new ArrayList<String>();

        String getGroup = "SELECT * FROM Account_has_Group WHERE idAccount = '" + userID + "'";
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery(getGroup);

        while(result.next()) {
            listOfGroup.add(result.getString(1));
        }
        return listOfGroup;
    }

    public void joinGroup(String groupName, String userID)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url, username, password);

        String getGroupID = "SELECT * FROM chatGroup WHERE groupName = '" + groupName + "'";
        Statement st1 = connection.createStatement();
        ResultSet result1 = st1.executeQuery(getGroupID);
        int groupID = 0;

        while (result1.next()) {
            groupID = result1.getInt(1);

        }

        PreparedStatement ps = connection
                .prepareStatement("INSERT INTO Account_has_Group (Account_idAccount,Group_idGroup) VALUE (?,?)");
        ps.setString(1, userID);
        ps.setString(2, groupName);
        int status = ps.executeUpdate();

        if (status != 0) {
            System.out.print("Success");
        }

    }

    public void leaveGroup(String groupName, String userID)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url, username, password);

        String getGroupID = "SELECT * FROM chatGroup WHERE groupName = '" + groupName + "'";
        Statement st1 = connection.createStatement();
        ResultSet result1 = st1.executeQuery(getGroupID);
        int groupID = 0;

        while (result1.next()) {
            groupID = result1.getInt(1);
        }

        PreparedStatement ps = connection.prepareStatement("DELETE FROM account_has_group WHERE Group_idGroup = '"
                + groupName + "' AND Account_idAccount = '" + userID + "'");

        int status = ps.executeUpdate();

        if (status != 0) {
            System.out.print("Success");
        }
    }

}