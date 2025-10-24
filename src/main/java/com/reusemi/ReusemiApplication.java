package com.reusemi;

import com.reusemi.desktop.DesktopApplication;
import com.reusemi.desktop.DesktopMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class ReusemiApplication {

    private static final Logger logger = LoggerFactory.getLogger(ReusemiApplication.class);

    public static void main(String[] args) {
        // Check if we're running in a headless environment (Docker)
        if (GraphicsEnvironment.isHeadless()) {
            logger.info("Running in headless mode - disabling JavaFX components");
            System.setProperty("java.awt.headless", "true");
            // Don't initialize JavaFX in Docker
        } else {
            logger.info("Running in desktop mode - JavaFX will be available");
            System.setProperty("java.awt.headless", "false");
        }

        SpringApplication.run(ReusemiApplication.class, args);
    }
}