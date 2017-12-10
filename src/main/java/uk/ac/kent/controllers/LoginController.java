package uk.ac.kent.controllers;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author norbert
 */

@SuppressWarnings("ClassHasNoToStringMethod")
final class LoginController extends BaseController {

    @FXML
    private PasswordField password;
    @FXML
    private TextField username;
    @FXML
    private Button btn;

    LoginController(final Stage stage) {
        super(stage, database);
    }

    // private Sta

    @FXML
    private void handleLoginBtnPressed(final ActionEvent event) {
        if (authenticate()) displayMainView();
    }

    void displayLoginView() throws IOException {
        final Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/login.fxml"));
        final Scene scene = new Scene(root, 300.0, 200.0);
        getStage().setScene(scene);
        getStage().show();
    }

    private boolean authenticate() { return true;}

    public final PasswordField getPasswordField() { return password;}

    public final TextField getUsernameField() { return username;}
}
