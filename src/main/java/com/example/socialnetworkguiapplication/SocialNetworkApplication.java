package com.example.socialnetworkguiapplication;

import controller.Controller;
import domain.*;
import domain.validators.FriendshipValidator;
import domain.validators.UserValidator;
import domain.validators.Validator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import repository.Repository;
import repository.db.FriendRequestDbRepository;
import repository.db.FriendshipDbRepository;
import repository.db.MessageDbRepository;
import repository.db.UserDbRepository;
import service.*;

import java.io.IOException;

public class SocialNetworkApplication extends Application {
    Scene logInScene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader logInWindowLoader = new FXMLLoader(SocialNetworkApplication.class.getResource("log-in-view.fxml"));
        logInScene = new Scene(logInWindowLoader.load(), 500, 500);
        stage.setTitle("LogIn");
        stage.setScene(logInScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
