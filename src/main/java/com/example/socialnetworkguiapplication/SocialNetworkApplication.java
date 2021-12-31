package com.example.socialnetworkguiapplication;

import controller.Controller;
import domain.*;
import domain.validators.EmailValidator;
import domain.validators.FriendshipValidator;
import domain.validators.UserValidator;
import domain.validators.Validator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.Repository;
import repository.db.FriendRequestDbRepository;
import repository.db.FriendshipDbRepository;
import repository.db.MessageDbRepository;
import repository.db.UserDbRepository;
import service.*;

import java.io.IOException;

public class SocialNetworkApplication extends Application {
    private static Controller controller;
    Scene logInScene;

    public static Controller getController() {
        return controller;
    }

    public static void setController(Controller controller) {
        SocialNetworkApplication.controller = controller;
    }

    @Override
    public void start(Stage stage) throws IOException {
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
        setController(new Controller((UserService) userService,(FriendshipService) friendshipService,(MessageService) messageService,(FriendRequestService) friendRequestService));
        FXMLLoader logInWindowLoader = new FXMLLoader(SocialNetworkApplication.class.getResource("log-in-view.fxml"));
        logInScene = new Scene(logInWindowLoader.load(),400,400);
        stage.setTitle("LogIn");
        stage.setScene(logInScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
