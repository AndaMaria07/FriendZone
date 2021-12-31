package com.example.socialnetworkguiapplication;
import controller.Controller;
import domain.User;
import domain.UserDto;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddFriendController implements Initializable {


    private Controller controller;
    private Stage primaryStage;
    private ObservableList<User> model = FXCollections.observableArrayList();

    public void setController(Controller controller) {
        this.controller = controller;
        initModel();
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private Button addFriendsButton;

    @FXML
    private TableColumn<User, String> userEmailColumn;

    @FXML
    private TableColumn<User, String> userFirstNameColumn;

    @FXML
    private TableColumn<User, String> userLastNameColumn;

    @FXML
    private TableView<User> usersTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        userFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        userLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        usersTable.setItems(model);
    }

    private void initModel(){
        Iterable<User> users = controller.getAllUsers();
        List<User> allUsers = new ArrayList<>();
        users.forEach(allUsers::add);
        List<User> userList = allUsers.stream().filter(x->!x.getEmail().equals(controller.getLoggedEmail())).collect(Collectors.toList());
        model.setAll(userList);
    }

    @FXML
    void addFriendButtonClick(ActionEvent event) {
        User selected = usersTable.getSelectionModel().getSelectedItem();
        if(selected != null){
            try {
                controller.sendRequest(selected.getEmail());
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Friend Request", "Succesfully sent!");
            }
            catch (ExistenceException ex){
                MessageAlert.showErrorMessage(null,ex.getMessage());
            }
        }
        else{
            MessageAlert.showErrorMessage(null,"Please select an user!");
        }
    }

    @FXML
    void onBackButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SocialNetworkApplication.class.getResource("friends-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setTitle("FriendZone");
        primaryStage.setScene(scene);

        FriendsController friendsController = fxmlLoader.getController();
        friendsController.setController(controller);
        friendsController.setStage(primaryStage);

    }
}
