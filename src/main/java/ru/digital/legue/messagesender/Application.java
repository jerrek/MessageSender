package ru.digital.legue.messagesender;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.digital.legue.messagesender.service.FilePathService;
import ru.digital.legue.messagesender.ui.MainController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
public class Application extends AbstractJavaFxApplicationSupport {

    @Value("${ui.title:JavaFX приложение}")//
    private String windowTitle;

    @Resource(name = "mainView")
    private ControllersConfiguration.ViewHolder view;

    @Resource(name = "mainController")
    private MainController controller;

    @Resource(name = "filePathService")
    private FilePathService filePathService;

    public Application() {
    }

    public static void main(String[] args) {
        launchApp(Application.class, args);
    }

    @Override
    public void start(Stage stage) {
        File propertiesFile;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open messagesender.properties file");
        if (filePathService.getPathToProps() == null || filePathService.getPathToProps().isEmpty()) {
            propertiesFile = fileChooser.showOpenDialog(stage);
        } else {
            propertiesFile = new File(filePathService.getPathToProps());
        }
        Map<String, String> queuesWithWorkflow = new HashMap<>();
        while (true) {
            if (!propertiesFile.getName().contains("messagesender.properties")) {
                propertiesFile = fileChooser.showOpenDialog(stage);
                continue;
            }
            if (!filePathService.getPathToProps().equals(propertiesFile.getPath())) {
                filePathService.savePathToProps(propertiesFile.getPath());
            }
            Properties property = new Properties();
            try (FileInputStream fis = new FileInputStream(propertiesFile.getPath())) {
                property.load(fis);
                for (final String name : property.stringPropertyNames()) {
                    queuesWithWorkflow.put(name, property.getProperty(name));
                }
            } catch (IOException e) {
                System.err.println("ОШИБКА: Файл свойств отсуствует!");
            }
            controller.getQueues().setItems(FXCollections.observableArrayList(new ArrayList<>(queuesWithWorkflow.keySet())));
            controller.setQueuesWithWorkflow(queuesWithWorkflow);
            controller.setPropertiesFile(propertiesFile);
            break;
        }
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(view.getView()));
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }

}
