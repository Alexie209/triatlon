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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

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
    @FXML
    private ImageView brandingImageView;

    @FXML
    private Button exitButton;

    @FXML
    private Button loginArbitru;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Label loginMessageLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File brandingFile = new File("C:\\MPP\\triatlon\\src\\images/triatlon.jpg");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);
    }

    @FXML
    void exitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void loginArbitruOnAction(ActionEvent event) {
        if (!usernameTextField.getText().isBlank() && !passwordTextField.getText().isBlank()) {
            validateLogin();
        } else {
            loginMessageLabel.setText("Please enter username or password");
        }
    }

    private void validateLogin() {
        boolean ok = false;
        for (Arbitru arbitru : service.printAll()) {
            if (arbitru.getUsername().equals(usernameTextField.getText()) && arbitru.getPassword().equals(passwordTextField.getText())) {
                loginMessageLabel.setText("Congratulations!");
                ok = true;

                LoggedUser.setUser(arbitru);
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accountController.fxml"));
                Scene scene;
                try {
                    Stage stage = (Stage) loginArbitru.getScene().getWindow();
                    stage.close();
                    scene = new Scene(fxmlLoader.load(), 600, 400);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        if (!ok) {
            loginMessageLabel.setText("Invalid login!");
        }
    }


}
