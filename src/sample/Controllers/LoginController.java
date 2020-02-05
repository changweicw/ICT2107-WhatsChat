package sample.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Window;
import sample.HelperFunctions.AlertHelper;
import sample.HelperFunctions.ImageStream;
import java.io.IOException;

public class LoginController {

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
            loginBackdrop.setImage(new ImageStream().createImage(imageSource));
        } catch (
                IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    protected void loginHandler(ActionEvent event) {
        Window owner = loginBtn.getScene().getWindow();
        if(usernameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, owner, "Username Field Empty",
                    "Please enter your username!");
            return;
        } else {
            /**
             * TO BE IMPLEMENTED
             */
        }
    }
}
