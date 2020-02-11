package sample.Controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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

        addOnlineUserToUI("Felix");
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
                "Please enter your username!");
        return;
    }

    @FXML
    public void addOnlineUserToUI(String userId){

        Text userToAdd = new Text();
        userToAdd.setText(userId);
        userToAdd.setStyle("-fx-font: 16 system;" +
                "-fx-fill: #fafafa;" );

    //        Circle userProfilePicture = new Circle();
    //        userProfilePicture.setRadius(20);
    //        userProfilePicture.in

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

        userProfileStack.getChildren().addAll(userProfileSpace, userToAdd);
        newOnlineUser.getChildren().add(userProfileStack);
        onlineUsers.getChildren().add(newOnlineUser);

    }

    @FXML
    public void addGroupToUI(String groupName) {

        Text userToAdd = new Text();
        userToAdd.setText(groupName);
        userToAdd.setStyle("-fx-font: 16 system;" +
                "-fx-fill: #fafafa;");

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
        newOnlineUser.setMaxSize(240, 40);
        newOnlineUser.setMaxHeight(40);
        newOnlineUser.setMaxWidth(240);
        newOnlineUser.setMinHeight(40);
        newOnlineUser.setMinWidth(240);

        userProfileStack.getChildren().addAll(userProfileSpace, userToAdd);
        newOnlineUser.getChildren().add(userProfileStack);
        onlineUsers.getChildren().add(newOnlineUser);

    }

}
