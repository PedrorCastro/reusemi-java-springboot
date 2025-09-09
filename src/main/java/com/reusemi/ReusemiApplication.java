package com.reusemi;

import com.reusemi.desktop.DesktopService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReusemiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        // Força modo gráfico
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(ReusemiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Abre a aplicação desktop apontando para a home do Spring
        new Thread(() -> DesktopService.launchApp("http://localhost:8080/")).start();
    }
}
