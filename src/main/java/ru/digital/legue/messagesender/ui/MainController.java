package ru.digital.legue.messagesender.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MainController {
    @FXML
    public TextArea inputData;
    @FXML
    public TextField statusData;
    @FXML
    public TextField wfName;
    @FXML
    public TextField queueName;
    private Map<String, String> queuesWithWorkflow;
    private File propertiesFile;
    @FXML
    private ComboBox<String> queues;

    @FXML
    public void sendMessage() {
        //TODO сюда вписать метод отправки
        System.out.println("send message");
        System.out.println(inputData.getText());
        statusData.setText("SUCCESS!");
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
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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
}
