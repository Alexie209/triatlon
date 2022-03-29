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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class RaportController implements Initializable {

    @FXML
    private Button raportBackButton;

    @FXML
    private TableColumn<ParticipantProba, Long> raportIdJucatorColumn;

    @FXML
    private TableColumn<ParticipantProba, Integer> raportPuncteColumn;

    @FXML
    private TableView<ParticipantProba> raportTableView;

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
    private final ObservableList<ParticipantProba> participantProbas = FXCollections.observableArrayList();
    Arbitru arbitru = LoggedUser.arbitru;


    @FXML
    void raportBackButtonOnAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accountController.fxml"));
        Scene scene;
        try {
            Stage stage = (Stage) raportBackButton.getScene().getWindow();
            stage.close();
            scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        participantProbas.removeAll(participantProbas);
        participantProbas.addAll(jucatoriProbe());

        raportIdJucatorColumn.setCellValueFactory(new PropertyValueFactory<ParticipantProba,Long>("idJucator"));
        raportPuncteColumn.setCellValueFactory(new PropertyValueFactory<ParticipantProba,Integer>("puncteStranse"));

        raportTableView.getItems().addAll(participantProbas);

    }
    private List<ParticipantProba> jucatoriProbe() {
        ArrayList<ParticipantProba> participantProbas1 = new ArrayList<>();
        Long idProba = null;
        for(Proba proba: probaRepository.findAll()) {
            if(proba.getIdArbitru() == arbitru.getId()) {
                idProba = proba.getId();
                break;
            }
        }
        for(ParticipantProba participantProba: participantProbaRepository.findAll()) {
            if(idProba == participantProba.getIdProba()) {
                participantProbas1.add(participantProba);
            }
        }
        service.sortedParticipantProba(participantProbas1);

        return participantProbas1;
    }

}
