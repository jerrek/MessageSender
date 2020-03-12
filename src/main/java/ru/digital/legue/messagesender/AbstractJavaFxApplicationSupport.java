package ru.digital.legue.messagesender;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class AbstractJavaFxApplicationSupport extends Application {

    private static String[] savedArgs;

    private ConfigurableApplicationContext context;

    static void launchApp(String[] args) {
        AbstractJavaFxApplicationSupport.savedArgs = args;
        Application.launch(ru.digital.legue.messagesender.Application.class, args);
    }

    @Override
    public void init() {
        System.setProperty("spring.main.web-application-type", "none");
        context = SpringApplication.run(getClass(), savedArgs);
        context.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        context.close();
    }
}
