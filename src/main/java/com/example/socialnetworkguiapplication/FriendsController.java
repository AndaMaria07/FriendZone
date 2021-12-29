package com.example.socialnetworkguiapplication;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    public Button addFriendsButton;
    public Button friendRequestsButton;
    private ObservableList<UserModel> friendsModels;


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
}


