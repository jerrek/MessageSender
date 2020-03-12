package ru.digital.legue.messagesender.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.digital.legue.messagesender.services.MessageSender;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;


public class MainController {
    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);
    private Map<String, String> queuesWithWorkflow;
    private File propertiesFile;
    private MessageSender messageSender;
    private static final String SUCCESS = "This is SUCCESS!";
    private static final String FAIL = "Mission failed!";
    private Random random = new Random();
    @FXML
    public TextArea inputData;
    @FXML
    public TextField statusData;
    @FXML
    public TextField wfName;
    @FXML
    public TextField queueName;
    @FXML
    private ComboBox<String> queues;

    @FXML
    public void sendMessage() {
        if (queues.getValue() == null || queues.getValue().isEmpty()) {
            statusData.setText(FAIL + " Choose queue!");
        } else if (inputData.getText() == null || inputData.getText().isEmpty()) {
            statusData.setText(FAIL + " Input text!");
        } else {
            if (messageSender.sendMessage(inputData.getText(), queuesWithWorkflow.get(queues.getValue()))) {
                statusData.setText(SUCCESS);
            } else {
                statusData.setText(FAIL);
            }
            someSecretMethod();
        }

    }

    @FXML
    public void addQueue() {
        if (wfName.getText() != null && !wfName.getText().isEmpty() &&
                queueName.getText() != null && !queueName.getText().isEmpty()) {
            queuesWithWorkflow.put(queueName.getText(), wfName.getText());
            queues.setItems(FXCollections.observableArrayList(new ArrayList<>(queuesWithWorkflow.keySet())));
            rewriteFile();
        }
    }

    @FXML
    public void deleteQueue() {
        if (queuesWithWorkflow.containsKey(queueName.getText())) {
            queuesWithWorkflow.remove(queueName.getText());
            queues.setItems(FXCollections.observableArrayList(new ArrayList<>(queuesWithWorkflow.keySet())));
        } else {
            statusData.setText("Queue is not exist");
        }
    }

    private void rewriteFile() {
        try (FileOutputStream fos = new FileOutputStream(propertiesFile.getPath(), false)) {
            queuesWithWorkflow.forEach((k, v) -> {
                String stringToWrite = k + "=" + v + "\n";
                try {
                    fos.write(stringToWrite.getBytes());
                } catch (IOException e) {
                    LOG.debug(e.getMessage());
                    LOG.debug(e.getCause().toString());
                }
            });
        } catch (Exception e) {
            LOG.debug(e.getMessage());
            LOG.debug(e.getCause().toString());
        }
    }

    public ComboBox<String> getQueues() {
        return queues;
    }

    public void setQueuesWithWorkflow(Map<String, String> queuesWithWorkflow) {
        this.queuesWithWorkflow = queuesWithWorkflow;
    }

    public void setPropertiesFile(File propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    private void someSecretMethod() {

        int randomInt = random.nextInt(342);
        if (randomInt == 42) {
            statusData.setText("Мир иллюзия! Вселенная голограмма! Скупай золото!");
        }
        if (randomInt == 13) {
            statusData.setText("IDDQD! Внедряй! Поддерживай! Не ссы!");
        }
        if (randomInt == 128) {
            statusData.setText("WoobbaLoobbaDubDub!!!");
        }
    }

    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }
}
