package com.example.socialnetworkguiapplication;

import controller.Controller;
import domain.*;
import domain.validators.*;
import domain.validators.exceptions.EntityNullException;
import domain.validators.exceptions.ExistenceException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import repository.Repository;
import repository.db.FriendRequestDbRepository;
import repository.db.FriendshipDbRepository;
import repository.db.MessageDbRepository;
import repository.db.UserDbRepository;
import service.*;
import utils.UtilMethods;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public TextField firstNameTextField;
    public TextField lastNameTextField;
    public TextField emailTextField;
    public TextField passwordTextField;
    public Button registerButton;

    private Controller controller;

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

    public void onRegisterButtonClick() {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        try {
            controller.addUser(firstName, lastName, email, password);
        } catch (EntityNullException | ValidationException | ExistenceException exc) {
            UtilMethods.showErrorDialog(exc.getMessage());
        }
    }
}
