package com.example.socialnetworkguiapplication;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProfileController implements Initializable,FriendRequestListener {
    public TableView<UserModel> friendsTable;
    public TableColumn<UserModel,String> friendEmailColumn;
    public TableColumn<UserModel,String> friendFirstNameColumn;
    public TableColumn<UserModel,String> friendLastNameColumn;
    public TableColumn<UserModel,String> friendshipDateColumn;
    public Button addFriendsButton;
    public Button friendRequestsButton;
    private ObservableList<UserModel> friendsModels;

    @FXML
    private TextField searchBar;

    @FXML
    private Label loggedEmail;

    Controller controller;
    private Stage primaryStage;

    double xOffset = 0;
    double yOffset = 0;

    public void setController(Controller controller) {
        this.controller = controller;
        loggedEmail.setText(controller.getUser(controller.getLoggedEmail()).getFirstName().concat(" ").concat(controller.getUser(controller.getLoggedEmail()).getLastName()));

    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
        friendsTable.setItems(getTableData());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        friendEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        friendFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        friendLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        friendshipDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        friendsTable.setItems(getTableData());
        searchBar.textProperty().addListener(o->handleSearch());
    }

    public ObservableList<UserModel> getTableData(){
        friendsModels=FXCollections.observableArrayList();
        List<UserDto> loggedUsersFriends= SocialNetworkApplication.getController().getFriends(SocialNetworkApplication.getController().getUser(SocialNetworkApplication.getController().getLoggedEmail()));
        for (UserDto friend:loggedUsersFriends) {
            UserModel friendModel=new UserModel(SocialNetworkApplication.getController().getUserEmail(friend.getFirstName(),friend.getLastName()),friend.getFirstName(),friend.getLastName(),friend.getDate());
            friendsModels.add(friendModel);
        }
        return friendsModels;
    }

    public void onFriendRequestsButtonClick() throws IOException {
        FXMLLoader friendRequestsWindowLoader = new FXMLLoader(SocialNetworkApplication.class.getResource("friend-requests-view.fxml"));
        Stage friendRequestsStage=new Stage();
        Scene friendRequestsScene = new Scene(friendRequestsWindowLoader.load(), 700, 400);
        friendRequestsStage.setTitle("FriendRequests");
        friendRequestsStage.setScene(friendRequestsScene);
        friendRequestsStage.initModality(Modality.APPLICATION_MODAL);
        friendRequestsStage.show();
        ((FriendRequestController)friendRequestsWindowLoader.getController()).addListener(this);
    }

    @Override
    public void onFriendRequestAccepted(Friendship friendship) {
        friendsTable.setItems(getTableData());
    }

    @FXML
    public void onAddFriendsButtonClick() throws IOException {
        FXMLLoader addFriendWindowLoader = new FXMLLoader(SocialNetworkApplication.class.getResource("add-friends-view.fxml"));
        Scene addFriendScene = new Scene(addFriendWindowLoader.load(), 700, 400);
        primaryStage.setTitle("Add Friend");
        primaryStage.setScene(addFriendScene);
        AddFriendController addFriendController = addFriendWindowLoader.getController();
        addFriendController.setController(controller);
        addFriendController.setStage(primaryStage);
    }

    @FXML
    void removeFriendOnClicked(ActionEvent event) {
        UserModel selected = friendsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            controller.removeFriend(selected.getEmail());
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Remove Friend", "Succesfully removed!");
            friendsTable.setItems(getTableData());
        } else {
            MessageAlert.showErrorMessage(null, "Please select an user!");
        }
    }

    @FXML
    void logOutOnClicked(ActionEvent event) throws IOException {
        FXMLLoader logOutWindowLoader = new FXMLLoader(SocialNetworkApplication.class.getResource("log-in-view.fxml"));
        Scene logInScene = new Scene(logOutWindowLoader.load(), 612,341);
        logInScene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        logInScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
        primaryStage.setTitle("LogIn");
        primaryStage.setScene(logInScene);
        LogInController logInController = logOutWindowLoader.getController();
        logInController.setController(controller);
        logInController.setStage(primaryStage);
    }

    void handleSearch(){
        Predicate<UserModel> p1 = n->n.getFirstName().contains(searchBar.getText());
        Predicate<UserModel> p2 = n->n.getLastName().contains(searchBar.getText());
        List<UserModel> userModelList = this.getTableData().stream().filter(p1.or(p2)).collect(Collectors.toList());
        friendsModels.setAll(userModelList);
        friendsTable.setItems(friendsModels);
    }
}
