package com.reusemi;

import com.reusemi.desktop.DesktopMain;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ReusemiApplication {

    private static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        // Inicia o Spring Boot
        springContext = new SpringApplicationBuilder(ReusemiApplication.class)
                .headless(false)
                .run(args);

        // Inicia a aplicação Desktop JavaFX
        Application.launch(DesktopMain.class, args);
    }

    public static ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }
}