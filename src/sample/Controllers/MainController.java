package sample.Controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.BackendThreads.client;
import sample.BackendThreads.clientInfo;
import sample.HelperFunctions.AlertHelper;
import sample.HelperFunctions.DBConnection;
import sample.HelperFunctions.ImageStream;
import sample.HelperFunctions.utils;

import java.io.IOException;
import java.net.MulticastSocket;

public class MainController {
    static DBConnection db = new DBConnection();
    clientInfo ci;
    client dedicatedThread;
    private MulticastSocket mainSocket;

    @FXML
    private Text currUser;

    @FXML
    private TextArea messages;

    @FXML
    private VBox onlineUsers;

    @FXML
    private Button createGroupBtn;

    @FXML
    private void initialize(){
        try {
            mainSocket = new MulticastSocket(utils.port);
            ci = new clientInfo(utils.uniqueId);
            System.out.println("main controller initialize "+utils.uniqueId);
            dedicatedThread = new client(ci,this);
            dedicatedThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public clientInfo getCi(){
        return this.ci;
    }

    @FXML
    private void createGroupBtnHandler(ActionEvent actionEvent) {
        System.out.println("Testing button create group clicked");
        Stage owner = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        AlertHelper.showAlert(Alert.AlertType.WARNING, owner, "Username Field Empty",
                "Please enter your username!");
    }

    public void setUp(clientInfo ci){
        this.ci = ci;
        System.out.println(this.ci.getUserListInString() + "\n Is in the house");
        dedicatedThread = new client(this.ci,this);
        dedicatedThread.start();
    }

    //Receive message from scene 1
    public void transferMessage(String message) {
        //Display the message
        currUser.setText(message);
    }



    @FXML
    protected void createGroupHandler(ActionEvent event){
        Stage owner = (Stage) ((Node)event.getSource()).getScene().getWindow();
        AlertHelper.showAlert(Alert.AlertType.WARNING, owner, "Username Field Empty",
                "adsad");
//        db.createGroup();
//        db.joinGroup();

    }

    public void popup(String title, String msg){
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(title);
        a.setContentText(msg);
        a.show();

    }

    public void addOnlineUserToList(String userId){

        Text userToAdd = new Text();
        userToAdd.setText(userId);
        userToAdd.setStyle("-fx-font: 16 system");

        HBox newOnlineUser = new HBox();
        newOnlineUser.setMaxSize(240,40);
        newOnlineUser.setMaxHeight(40);
        newOnlineUser.setMaxWidth(240);
        newOnlineUser.setMinHeight(40);
        newOnlineUser.setMaxWidth(240);
        newOnlineUser.getChildren().add(userToAdd);

        onlineUsers.getChildren().add(newOnlineUser);
    }

}
