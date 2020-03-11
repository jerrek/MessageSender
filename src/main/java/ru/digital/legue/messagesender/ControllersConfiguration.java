package ru.digital.legue.messagesender;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.digital.legue.messagesender.ui.MainController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Configuration
public class ControllersConfiguration {

    @Bean(name = "mainView")
    public ViewHolder getMainView() throws IOException {
        return loadView();
    }

    @Bean(name = "mainController")
    public MainController getMainController() throws IOException {
        return (MainController) getMainView().getController();
    }

    private ViewHolder loadView() throws IOException {
        try (InputStream fxmlStream = getClass().getClassLoader().getResourceAsStream("fxml/main.fxml")) {
            FXMLLoader loader = new FXMLLoader();
            loader.load(Objects.requireNonNull(fxmlStream));
            return new ViewHolder(loader.getRoot(), loader.getController());
        }
    }

    static class ViewHolder {
        private Parent view;
        private Object controller;

        ViewHolder(Parent view, Object controller) {
            this.view = view;
            this.controller = controller;
        }

        Parent getView() {
            return view;
        }

        Object getController() {
            return controller;
        }

    }

}
