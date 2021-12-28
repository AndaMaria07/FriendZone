package com.example.socialnetworkguiapplication;

import controller.Controller;
import domain.*;
import domain.validators.*;
import domain.validators.exceptions.EntityNullException;
import domain.validators.exceptions.LogInException;
import domain.validators.exceptions.NotExistenceException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import repository.Repository;
import repository.db.FriendRequestDbRepository;
import repository.db.FriendshipDbRepository;
import repository.db.MessageDbRepository;
import repository.db.UserDbRepository;
import service.*;
import utils.UtilMethods;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    Controller controller;

    @FXML
    private TextField emailTextField,passwordTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Validator<User> userValidator = new UserValidator();
        Validator<String> emailValidator = new EmailValidator();
        Validator<Friendship> friendshipValidator = new FriendshipValidator();
        Repository<String, User> userDbRepository=new UserDbRepository("jdbc:postgresql://localhost:5432/SocialNetwork", "postgres","diana", userValidator,emailValidator);
        Repository<Tuple<String, String>, Friendship> friendshipDbRepository =new FriendshipDbRepository("jdbc:postgresql://localhost:5432/SocialNetwork", "postgres","diana",friendshipValidator);
        Repository<Long, Message> messageDbRepository = new MessageDbRepository("jdbc:postgresql://localhost:5432/SocialNetwork", "postgres","diana");
        Repository<Tuple<String, String>, FriendRequest> friendRequestDbRepository=new FriendRequestDbRepository("jdbc:postgresql://localhost:5432/SocialNetwork", "postgres","diana");
        Service<String, User> userService = new UserService(userDbRepository);
        Service<Tuple<String, String>, Friendship> friendshipService = new FriendshipService(friendshipDbRepository);
        Service<Long,Message> messageService = new MessageService(messageDbRepository);
        Service<Tuple<String, String>, FriendRequest> friendRequestService=new FriendRequestService(friendRequestDbRepository);
        controller = new Controller((UserService) userService,(FriendshipService) friendshipService,(MessageService) messageService,(FriendRequestService) friendRequestService);
    }

    @FXML
    public void onLogInButtonClick(){
        try {
            controller.setLoggedEmail(emailTextField.getText());
            controller.setLoggedPassword(passwordTextField.getText());
        }catch (ValidationException | EntityNullException | LogInException | NotExistenceException exc){
            UtilMethods.showErrorDialog(exc.getMessage());
        }
    }

    @FXML
    public void onRegisterButtonClick() throws IOException {
        FXMLLoader registerWindowLoader = new FXMLLoader(SocialNetworkApplication.class.getResource("register-view.fxml"));
        Stage registerStage=new Stage();
        Scene registerScene = new Scene(registerWindowLoader.load(), 500, 500);
        registerStage.setTitle("Register");
        registerStage.setScene(registerScene);
        registerStage.initModality(Modality.APPLICATION_MODAL);
        registerStage.initOwner(emailTextField.getScene().getWindow());
        registerStage.show();
    }


}
