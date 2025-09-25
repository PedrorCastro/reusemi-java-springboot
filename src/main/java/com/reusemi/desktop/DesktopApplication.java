package com.reusemi.desktop;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DesktopApplication {

    private static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        // Inicia o Spring Boot primeiro
        springContext = new SpringApplicationBuilder(DesktopApplication.class)
                .headless(false) // CRUCIAL: desativa modo headless
                .run(args);

        // Inicia o JavaFX após o Spring estar pronto
        Application.launch(DesktopMain.class, args);
    }

    public static ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }
}