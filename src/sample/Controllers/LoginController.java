package sample.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.HelperFunctions.AlertHelper;
import sample.HelperFunctions.ImageStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.sql.SQLException;

import sample.BackendThreads.*;
import sample.HelperFunctions.utils;
import sample.HelperFunctions.DBConnection;

public class LoginController {
    private MulticastSocket mainSocket;
//    private GroupThread gt;

    static DBConnection db = new DBConnection();

    clientInfo myClient;
    MainController mainController;
    Parent root;
    client tempThread;

//    public void setGroupThread(GroupThread g){
//        this.gt = g;
//    }

    @FXML
    private Button loginBtn;

    @FXML
    private StackPane defocusHelper;

    @FXML
    private ImageView loginBackdrop;

    @FXML
    private ImageView appLogo;

    @FXML
    private TextField usernameField;

    @FXML
    private void initialize(){

        /**
         * Defocus username TextField on launch
         * Grab GIF from external source, pass into image stream and output on backdrop
        */

        Platform.runLater(()->defocusHelper.requestFocus());

        appLogo.setImage(new Image(getClass().getResource("../Assets/logo.png").toExternalForm()));
        String imageSource = "https://assets.new.siemens.com/siemens/assets/api/uuid:90731460a469311769af27b63a74ac213e7db8e2/width:1125/quality:high/version:1555015281/digital-twin-how-are-you.gif";

        try {

            myClient = new clientInfo(utils.uniqueId);
            mainSocket = new MulticastSocket(utils.port);
            FXMLLoader loader = new FXMLLoader((getClass().getResource("../Scenes/MainApplication.fxml")));
            root = loader.load();
            mainController = loader.getController();
            System.out.println(utils.uniqueId);
//            tempThread = new client(myClient);
//            tempThread.start();

            String msg = utils.createPacketMessage(
                    utils.getUserListCommand,
                    utils.uniqueId,
                    usernameField.getText()
            );
            DatagramPacket packet = utils.createDatagramPacket(
                    msg,
                    InetAddress.getByName(utils.dedicatedIP),
                    utils.port
            );

            mainSocket.send(packet);
            //I have to go grab all the currently online users
            loginBackdrop.setImage(new ImageStream().createImage(imageSource));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    protected void loginHandler(ActionEvent event) {
//        Window owner = loginBtn.getScene().getWindow();
        Stage owner = (Stage) ((Node)event.getSource()).getScene().getWindow();

        if(usernameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, owner, "Username Field Empty",
                    "Please enter your username!");
            return;
        } else {
            try {
//                Parent root = FXMLLoader.load(getClass().getResource("../Scenes/MainApplication.fxml"));


                mainController.transferMessage(usernameField.getText());

                //Validation and checks
                //Call db test
                String tempVar = usernameField.getText();
                if(!utils.regexMatcherForId(tempVar,utils.idPatternString)){
                    AlertHelper.showAlert(Alert.AlertType.WARNING,
                            owner,
                            "Username dont conform",
                            "Username must: \nContain 8 digits\nContain no spaces\nNot start with a number");
                    return;
                } else {
                    mainController.popup("test","ez");
                    if(mainController.getCi().searchUsers(usernameField.getText())==null){
                        DBConnection.createAccount(tempVar);
                        myClient.setUsername(usernameField.getText());
                        System.out.println("Can add");
                        String msg = utils.createPacketMessage(
                                utils.newUserJoinedCommand,
                                myClient.getClientId(),
                                usernameField.getText()
                        );
                        DatagramPacket p = utils.createDatagramPacket(
                                msg,
                                InetAddress.getByName(utils.dedicatedIP),
                                utils.port);
                        mainSocket.send(p);
                        //Run the codes to start thread and all

                    } else {
                        AlertHelper.showAlert(Alert.AlertType.WARNING,
                                owner,
                                "Username already exist",
                                "Please choose another username!");
                        return;
                    }

                }



                Scene mainScene = new Scene(root, 1200, 675);
                owner.setScene(mainScene);
                owner.setResizable(false);
                owner.show();
//                tempThread.stop();
//                mainController.setUp(myClient);
            } catch (InstantiationException | SQLException | IllegalAccessException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}

