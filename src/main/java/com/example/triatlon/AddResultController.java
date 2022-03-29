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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class AddResultController implements Initializable {

    @FXML
    private TableColumn<Jucator, String> addResultFirstNameColumn;

    @FXML
    private TableColumn<Jucator, String> addResultLastNameColumn;

    @FXML
    private Button addResultProbaButton;

    @FXML
    private Button addResultBackButton;

    @FXML
    private TextField puncteObtinuteTextField;

    @FXML
    private TableView<Jucator> addResultTableView;

    @FXML
    private Label probaLabel;

    @FXML
    private Label messageLabel;



    private final Properties properties = new Properties();

    {
        try {
            properties.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
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

        probaLabel.setText("Proba: " + selectedProba().getName());

        jucatori.removeAll(jucatori);
        jucatori.addAll(jucatoriProbe());

        addResultFirstNameColumn.setCellValueFactory(new PropertyValueFactory<Jucator, String>("firstName"));
        addResultLastNameColumn.setCellValueFactory(new PropertyValueFactory<Jucator, String>("lastName"));
        addResultTableView.getItems().addAll(jucatori);

    }

    private List<Jucator> jucatoriProbe() {
        ArrayList<Jucator> jucators = new ArrayList<>(service.printAllJucatori());
        service.sortedJucatori(jucators);

        return jucators;
    }

    @FXML
    void addResultProbaButtonOnAction(ActionEvent event) {
        try{
        service.addPuncteProba(tableViewSelectedJucator().getId(), selectedProba().getId(), Integer.parseInt(puncteObtinuteTextField.getText()));
        messageLabel.setText("Result added!");
    } catch (NumberFormatException e) {
            messageLabel.setText("Could not add your result!");
            //e.printStackTrace();
        }
    }

    private Jucator tableViewSelectedJucator() {
        return addResultTableView.getSelectionModel().getSelectedItem();
    }

    private Proba selectedProba() {
        for (Proba proba1 : service.printAllProba()) {
            if (proba1.getIdArbitru() == arbitru.getId()) {
                return proba1;
            }
        }
        return null;
    }
    @FXML
    void addResultBackButtonOnAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accountController.fxml"));
        Scene scene;
        try {
            Stage stage = (Stage) addResultBackButton.getScene().getWindow();
            stage.close();
            scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}