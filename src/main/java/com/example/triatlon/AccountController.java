package com.example.triatlon;


import com.example.triatlon.domain.*;
import com.example.triatlon.domain.validators.ArbitruValidator;
import com.example.triatlon.domain.validators.JucatorValidator;
import com.example.triatlon.domain.validators.ParticipantProbaValidator;
import com.example.triatlon.domain.validators.ProbaValidator;
import com.example.triatlon.repository.Repository;
import com.example.triatlon.repository.db.ArbitruDbRepository;
import com.example.triatlon.repository.db.JucatorDbRepository;
import com.example.triatlon.repository.db.ParticipantProbaDbRepository;
import com.example.triatlon.repository.db.ProbaDbRepository;
import com.example.triatlon.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AccountController implements Initializable {

    @FXML
    private Label nameLabel;
    @FXML
    private TableView<Jucator> playersTableView;
    @FXML
    private TableColumn<Jucator, String> firstNameColumn;
    @FXML
    private TableColumn<Jucator, String> lastNameColumn;
    @FXML
    private TableColumn<Jucator, Integer> totalPuncteColumn;
    @FXML
    private Button raportButton;
    @FXML
    private Button logoutButton;

    private final Properties properties = new Properties();
    {
        try {
            properties.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }
    }
    private final Repository<Long, Arbitru> arbitruRepository = new ArbitruDbRepository(properties, new ArbitruValidator());
    private final Repository<Long, Jucator> jucatorRepository = new JucatorDbRepository(properties, new JucatorValidator());
    private final Repository<Long, Proba> probaRepository = new ProbaDbRepository(properties, new ProbaValidator());
    private final Repository<Long, ParticipantProba> participantProbaRepository = new ParticipantProbaDbRepository(properties, new ParticipantProbaValidator());
    private final Service service = new Service(arbitruRepository, jucatorRepository, probaRepository, participantProbaRepository);
    private final ObservableList<Jucator> jucatori = FXCollections.observableArrayList();
    Arbitru arbitru = LoggedUser.arbitru;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameLabel.setText(arbitru.getFirstName() + " " + arbitru.getLastName());
        jucatori.removeAll(jucatori);
        jucatori.addAll(jucatoriUser());

//        TableColumn<Jucator, String> firstNameColumn = new TableColumn<>("FirstName");
//        TableColumn<Jucator, String> lastNameColumn = new TableColumn<>("LastName");
//        TableColumn<Jucator, Integer> totalPuncteColumn = new TableColumn<>("TotalPuncte");
//        playersTableView.getColumns().addAll(firstNameColumn, lastNameColumn, totalPuncteColumn);
//
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Jucator, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Jucator, String>("lastName"));
        totalPuncteColumn.setCellValueFactory(new PropertyValueFactory<Jucator, Integer>("totalPuncte"));
        playersTableView.getItems().addAll(jucatori);

//        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
//        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
//        totalPuncteColumn.setCellValueFactory(new PropertyValueFactory<>("TotalPuncte"));
//        playersTableView.setItems(jucatori);
    }
    public List<Jucator> jucatoriUser() {
        ArrayList<Jucator> jucators = new ArrayList<>(service.printAllJucatori());
        service.sortedJucatori(jucators);

        return jucators;
    }
    @FXML
    void addResultButtonOnAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addResult.fxml"));
        Scene scene;
        try {
            Stage stage = (Stage) raportButton.getScene().getWindow();
            stage.close();
            scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void raportButtonOnAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("raportController.fxml"));
        Scene scene;
        try {
            Stage stage = (Stage) raportButton.getScene().getWindow();
            stage.close();
            scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void logoutButtonOnAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene;
        try {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.close();
            scene = new Scene(fxmlLoader.load(), 591, 346);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
