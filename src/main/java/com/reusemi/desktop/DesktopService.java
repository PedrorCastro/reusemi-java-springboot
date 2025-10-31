package com.reusemi.desktop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class DesktopService extends Application {

    private static String url = "http://localhost:8080"; // URL inicial

    @Override
    public void start(Stage stage) {
        WebView webView = new WebView();

        // Carrega a página Thymeleaf inicial
        webView.getEngine().load(url);

        Scene scene = new Scene(webView, 1024, 768);
        stage.setTitle("Reusemi Desktop");
        stage.setScene(scene);
        stage.show();
    }

    public static void launchApp(String startUrl) {
        url = startUrl;
        Application.launch();
    }
}
