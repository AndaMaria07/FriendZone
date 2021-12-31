package com.example.socialnetworkguiapplication;

import domain.validators.*;
import domain.validators.exceptions.EntityNullException;
import domain.validators.exceptions.LogInException;
import domain.validators.exceptions.NotExistenceException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.UtilMethods;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    public Button logInButton;
    public Button registerButton;
    public TextField emailTextField,passwordTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void onLogInButtonClick() throws IOException {
        try {
            String email=emailTextField.getText();
            SocialNetworkApplication.getController().setLoggedEmail(email);
            SocialNetworkApplication.getController().setLoggedPassword(passwordTextField.getText());
            FXMLLoader friendsWindowLoader=new FXMLLoader(SocialNetworkApplication.class.getResource("friends-view.fxml"));
            Scene friendsScene=new Scene(friendsWindowLoader.load(),650,500);
            Stage stage= (Stage) emailTextField.getScene().getWindow();
            stage.setScene(friendsScene);
            stage.show();
        }catch (ValidationException | EntityNullException | LogInException | NotExistenceException exc){
            UtilMethods.showErrorDialog(exc.getMessage());
        }
    }

    @FXML
    public void onRegisterButtonClick() throws IOException {
        FXMLLoader registerWindowLoader = new FXMLLoader(SocialNetworkApplication.class.getResource("register-view.fxml"));
        Stage registerStage= (Stage) emailTextField.getScene().getWindow();
        Scene registerScene = new Scene(registerWindowLoader.load(), 500, 500);
        registerStage.setTitle("Register");
        registerStage.setScene(registerScene);
        registerStage.initModality(Modality.APPLICATION_MODAL);
        registerStage.initOwner(emailTextField.getScene().getWindow());
        registerStage.show();
    }
}
