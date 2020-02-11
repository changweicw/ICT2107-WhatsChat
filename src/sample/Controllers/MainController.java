package sample.Controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.BackendThreads.client;
import sample.BackendThreads.clientInfo;
import sample.HelperFunctions.AlertHelper;
import sample.HelperFunctions.DBConnection;
import sample.HelperFunctions.ImageStream;
import sample.HelperFunctions.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MainController {
    static DBConnection db = new DBConnection();
    clientInfo ci;

    MulticastSocket mainSocket;

    @FXML
    private Text currUser;

    @FXML
    private TextArea messages;

    @FXML
    private VBox onlineUsers;

    @FXML
    private VBox groupList;

    @FXML
    private ImageView defocusHelper;

    @FXML
    private Button createGroupBtn;

    @FXML
    private Button sendBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private void initialize(){
        Platform.runLater(()->defocusHelper.requestFocus());
        ImageView imageView = new ImageView(getClass().getResource("../Assets/sendarrow.png").toExternalForm());
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        imageView.setPreserveRatio(true);
        sendBtn.setGraphic(imageView);
        sendBtn.setPadding(Insets.EMPTY);
        Thread dedicatedThread;
        addOnlineUserToUI("Felix");

        addGroupToUI("ICT2902");

        try {
            mainSocket = new MulticastSocket(utils.port);
            ci = new clientInfo(utils.uniqueId);
            System.out.println("main controller initialize "+utils.uniqueId);
            dedicatedThread = new client(ci,this);
            dedicatedThread.start();
            String msg = utils.createPacketMessage(
                    utils.getUserListCommand,
                    utils.uniqueId
            );
            DatagramPacket packet = utils.createDatagramPacket(
                    msg,
                    InetAddress.getByName(utils.dedicatedIP),
                    utils.port
            );

            mainSocket.send(packet);
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

    @FXML
    public void addOnlineUserToUI(String userId){

        Text userToAdd = new Text();
        userToAdd.setText(userId);
        userToAdd.setStyle("-fx-font: 16 system;" +
                "-fx-fill: #fafafa;" );

        Circle userProfilePicture = new Circle();
        userProfilePicture.setRadius(20);

        Rectangle userProfileSpace = new Rectangle();
        userProfileSpace.setArcHeight(20);
        userProfileSpace.setArcWidth(20);
        userProfileSpace.setHeight(55);
        userProfileSpace.setWidth(200);
        userProfileSpace.setStyle("-fx-effect: dropshadow(gaussian, #e5d1f5, 4,0,3,2);" +
                "-fx-fill: #497799;");

        StackPane userProfileStack = new StackPane();
        userProfileStack.setMaxHeight(55);
        userProfileStack.setMaxWidth(200);
        userProfileStack.setMinHeight(55);
        userProfileStack.setMinWidth(200);
        userProfileStack.setStyle("-fx-padding: 0 0 0 25;");

        HBox newOnlineUser = new HBox();
        newOnlineUser.setMaxSize(240,40);
        newOnlineUser.setMaxHeight(40);
        newOnlineUser.setMaxWidth(240);
        newOnlineUser.setMinHeight(40);
        newOnlineUser.setMinWidth(240);

        userProfileStack.getChildren().addAll(userProfileSpace, userProfilePicture, userToAdd);
        newOnlineUser.getChildren().add(userProfileStack);
        userProfileStack.setAlignment(userProfilePicture, Pos.CENTER_LEFT);
        userProfileStack.setAlignment(userToAdd, Pos.CENTER);
        onlineUsers.getChildren().add(newOnlineUser);

    }

    @FXML
    public void addGroupToUI(String groupName) {

        Text groupToAdd = new Text();
        groupToAdd.setText(groupName);
        groupToAdd.setStyle("-fx-font: 12 system;" +
                "-fx-fill: BLACK;");

        Rectangle groupSpace = new Rectangle();
        groupSpace.setArcHeight(5);
        groupSpace.setArcWidth(5);
        groupSpace.setHeight(60);
        groupSpace.setWidth(300);
        groupSpace.setStyle("-fx-effect:  dropshadow(gaussian, grey, 3, 0, 4, 2);" +
                "-fx-fill: #fdf5fa;");

        StackPane groupPane = new StackPane();
        groupPane.setMaxHeight(60);
        groupPane.setMaxWidth(300);
        groupPane.setMinHeight(60);
        groupPane.setMinWidth(300);
        groupPane.setStyle("-fx-padding: 0 0 5 25;");

        groupPane.getChildren().addAll(groupSpace, groupToAdd);
        groupPane.setAlignment(groupToAdd, Pos.CENTER_LEFT);
        groupList.getChildren().add(groupPane);
    }

    @FXML
    public void toggleSelectedGroup(){

    }

}
