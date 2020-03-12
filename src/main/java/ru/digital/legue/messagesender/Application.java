package ru.digital.legue.messagesender;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.digital.legue.messagesender.services.MessageSender;
import ru.digital.legue.messagesender.ui.MainController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@SpringBootApplication
public class Application extends AbstractJavaFxApplicationSupport {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    private static final String PROPS_PATH_FILE_PROPERTIES = "PropsPathFile.properties";
    private static final String MESSAGE_SENDER_QUEUES_PROPS = "messagesender.properties";

    @Value("${ui.title:JavaFX приложение}")//
    private String windowTitle;

    @Resource(name = "mainView")
    private ControllersConfiguration.ViewHolder view;

    @Resource(name = "mainController")
    private MainController controller;

    @Resource(name = "messageSenderService")
    private MessageSender messageSender;

    public Application() {
    }

    public static void main(String[] args) {
        launchApp(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        controller.setMessageSender(messageSender);
        File propertiesFile = new File("DUMMY");
        String path;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open " + MESSAGE_SENDER_QUEUES_PROPS + " file");
        if (!Paths.get(PROPS_PATH_FILE_PROPERTIES).toFile().exists()) {
            propertiesFile = fileChooser.showOpenDialog(stage);
        } else {
            List<String> paths = Files.readAllLines(Paths.get(PROPS_PATH_FILE_PROPERTIES));
            path = paths.get(0);
            if (Paths.get(path).toFile().exists()) {
                propertiesFile = new File(path);
            } else {
                Files.deleteIfExists(Paths.get(PROPS_PATH_FILE_PROPERTIES));
            }
        }
        Map<String, String> queuesWithWorkflow = new HashMap<>();
        while (true) {
            if (!propertiesFile.getName().contains(MESSAGE_SENDER_QUEUES_PROPS)) {
                propertiesFile = fileChooser.showOpenDialog(stage);
                continue;
            }
            Properties property = new Properties();
            try (FileInputStream fis = new FileInputStream(propertiesFile.getPath())) {
                property.load(fis);
                for (final String name : property.stringPropertyNames()) {
                    queuesWithWorkflow.put(name, property.getProperty(name));
                }
                if (!Paths.get(PROPS_PATH_FILE_PROPERTIES).toFile().exists()) {
                    Files.write(Paths.get(PROPS_PATH_FILE_PROPERTIES), propertiesFile.getPath().getBytes());
                }
            } catch (IOException e) {
                LOG.debug("ОШИБКА: Файл свойств отсуствует!");
                LOG.debug(e.getMessage());
                LOG.debug(e.getCause().toString());
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
