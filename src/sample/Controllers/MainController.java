package sample.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.HelperFunctions.AlertHelper;
import sample.HelperFunctions.ImageStream;

import java.io.IOException;

public class MainController {
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
        createGroupBtn.setOnAction(this::createGroupBtnHandler);
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
