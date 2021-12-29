package com.example.socialnetworkguiapplication;

import domain.validators.*;
import domain.validators.exceptions.EntityNullException;
import domain.validators.exceptions.ExistenceException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.UtilMethods;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public TextField firstNameTextField;
    public TextField lastNameTextField;
    public TextField emailTextField;
    public TextField passwordTextField;
    public Button registerButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         }

    public void onRegisterButtonClick() throws IOException {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        try {
            SocialNetworkApplication.getController().addUser(firstName, lastName, email, password);
            FXMLLoader friendsWindowLoader=new FXMLLoader(SocialNetworkApplication.class.getResource("friends-view.fxml"));
            Scene friendsScene=new Scene(friendsWindowLoader.load(),650,500);
            Stage stage= (Stage) emailTextField.getScene().getWindow();
            stage.setScene(friendsScene);
            stage.show();
        } catch (EntityNullException | ValidationException | ExistenceException exc) {
            UtilMethods.showErrorDialog(exc.getMessage());
        }
    }
}
