package com.example.socialnetworkguiapplication;

import controller.Controller;
import domain.*;
import domain.validators.*;
import domain.validators.exceptions.EntityNullException;
import domain.validators.exceptions.LogInException;
import domain.validators.exceptions.NotExistenceException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import repository.Repository;
import repository.db.FriendRequestDbRepository;
import repository.db.FriendshipDbRepository;
import repository.db.MessageDbRepository;
import repository.db.UserDbRepository;
import service.*;

import java.io.IOException;

public class LogInController {
    Controller controller;

    @FXML
    private TextField emailTextField,passwordTextField;
    @FXML
    private Button logInButton,registerButton;
    @FXML
    private Scene registerScene;
    @FXML
    private Label errorLabel;

    @FXML
    public void initialize(){
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
            Stage invalidInformationErrorStage=new Stage();
            errorLabel=new Label(exc.getMessage());
            VBox layout = new VBox(errorLabel);
            layout.setAlignment(Pos.CENTER);
            Scene invalidInformationErrorScene = new Scene(layout, 400, 50);
            invalidInformationErrorStage.setTitle("Error");
            invalidInformationErrorStage.setScene(invalidInformationErrorScene);
            invalidInformationErrorStage.show();
        }
    }

    public void onRegisterButtonClick(MouseEvent mouseEvent) {
    }
}
