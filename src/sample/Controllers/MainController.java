package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class MainController {
    @FXML
    private Text currUser;

    @FXML private TextArea messages;

    //Receive message from scene 1
    public void transferMessage(String message) {
        //Display the message
        currUser.setText(message);
    }

}
