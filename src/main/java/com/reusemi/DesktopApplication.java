package com.reusemi;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DesktopApplication {

    private static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        // Inicia o Spring Boot em uma thread separada para não bloquear o JavaFX
        Thread springThread = new Thread(() -> {
            springContext = new SpringApplicationBuilder(DesktopApplication.class)
                    .headless(false) // CRUCIAL: desativa modo headless
                    .run(args);
        });
        springThread.setDaemon(true); // Thread morre quando a aplicação principal morrer
        springThread.start();

        // Inicia o JavaFX
        Application.launch(DesktopMain.class, args);
    }

    public static ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }
}