package com.example.socialnetworkguiapplication;

import controller.Controller;
import domain.*;
import domain.validators.exceptions.ExistenceException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FriendsController implements Initializable {

    public TableView<UserModel> friendsTable;
    public TableColumn<UserModel,String> friendEmailColumn;
    public TableColumn<UserModel,String> friendFirstNameColumn;
    public TableColumn<UserModel,String> friendLastNameColumn;
    public TableColumn<UserModel,String> friendshipDateColumn;
    public Button friendRequestsButton;
    private ObservableList<UserModel> friendsModels;

    Controller controller;
    private Stage primaryStage;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        friendEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        friendFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        friendLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        friendshipDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        friendsTable.setItems(getTableData());
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

    @FXML
    public void onFriendRequestsButtonClick() throws IOException {
        FXMLLoader friendRequestsWindowLoader = new FXMLLoader(SocialNetworkApplication.class.getResource("friend-requests-view.fxml"));
        Stage friendRequestsStage=new Stage();
        Scene friendRequestsScene = new Scene(friendRequestsWindowLoader.load(), 700, 400);
        friendRequestsStage.setTitle("FriendRequests");
        friendRequestsStage.setScene(friendRequestsScene);
        friendRequestsStage.initModality(Modality.APPLICATION_MODAL);
        friendRequestsStage.show();
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
        if(selected != null){
            controller.removeFriend(selected.getEmail());
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Remove Friend","Succesfully removed!");
        }
        else{
            MessageAlert.showErrorMessage(null,"Please select an user!");
        }
    }
}



