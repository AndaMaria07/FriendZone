package com.example.socialnetworkguiapplication;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LogInController {
    @FXML
    private Controller controller;
    @FXML
    private TextField emailTextField;

    @FXML
    protected void onLogInButtonClick(){
        controller.setLoggedEmail(emailTextField.getText());
    }
}
