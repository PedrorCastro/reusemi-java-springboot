package com.reusemi;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class DesktopMain extends Application {

    private Stage primaryStage;
    private WebView webView;
    private WebEngine webEngine;
    private BorderPane rootLayout;
    private ProgressBar progressBar;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Sistema Reusemi - Desktop");
        this.primaryStage.setWidth(1200);
        this.primaryStage.setHeight(800);

        initRootLayout();
        initWebView();

        // Aguarda o Spring Boot inicializar antes de carregar a página
        waitForSpringBoot();
    }

    private void initRootLayout() {
        rootLayout = new BorderPane();

        // Barra de ferramentas
        ToolBar toolBar = new ToolBar();

        Button backButton = new Button("←");
        backButton.setOnAction(e -> {
            if (webEngine != null) {
                webEngine.executeScript("history.back()");
            }
        });

        Button forwardButton = new Button("→");
        forwardButton.setOnAction(e -> {
            if (webEngine != null) {
                webEngine.executeScript("history.forward()");
            }
        });

        Button refreshButton = new Button("↻");
        refreshButton.setOnAction(e -> {
            if (webEngine != null) {
                webEngine.reload();
            }
        });

        Button homeButton = new Button("🏠");
        homeButton.setOnAction(e -> loadSpringBootApp());

        // Barra de progresso
        progressBar = new ProgressBar();
        progressBar.setPrefWidth(200);
        progressBar.setVisible(false);

        toolBar.getItems().addAll(backButton, forwardButton, refreshButton, homeButton, progressBar);
        rootLayout.setTop(toolBar);

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initWebView() {
        try {
            webView = new WebView();
            webEngine = webView.getEngine();

            // Habilita JavaScript
            webEngine.setJavaScriptEnabled(true);

            // Configura o listener de progresso
            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                Platform.runLater(() -> {
                    progressBar.setVisible(newState == Worker.State.RUNNING);

                    if (newState == Worker.State.RUNNING) {
                        progressBar.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
                    } else {
                        progressBar.progressProperty().unbind();
                        progressBar.setProgress(0);
                    }

                    if (newState == Worker.State.SUCCEEDED) {
                        // Injeta objeto Java para comunicação com JavaScript
                        JSObject window = (JSObject) webEngine.executeScript("window");
                        window.setMember("desktopApp", new DesktopAppBridge());
                    }
                });
            });

            // Listener para erros de carregamento
            webEngine.getLoadWorker().exceptionProperty().addListener((obs, oldEx, newEx) -> {
                if (newEx != null) {
                    Platform.runLater(() -> {
                        showErrorPage("Erro ao carregar página: " + newEx.getMessage());
                    });
                }
            });

            rootLayout.setCenter(webView);

        } catch (Exception e) {
            showError("Erro ao inicializar WebView: " + e.getMessage());
            showErrorPage("WebView não suportado. Verifique a instalação do JavaFX.");
        }
    }

    private void loadSpringBootApp() {
        try {
            webEngine.load("http://localhost:8080");
        } catch (Exception e) {
            showLoadingPage();
        }
    }

    private void showLoadingPage() {
        String loadingHtml = """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Reusemi - Carregando</title>
                <style>
                    body { 
                        font-family: Arial, sans-serif; 
                        display: flex; 
                        justify-content: center; 
                        align-items: center; 
                        height: 100vh; 
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        color: white;
                        margin: 0;
                    }
                    .loading-container { 
                        text-align: center; 
                        padding: 20px;
                    }
                    .spinner { 
                        border: 4px solid #f3f3f3; 
                        border-top: 4px solid #3498db; 
                        border-radius: 50%; 
                        width: 40px; 
                        height: 40px; 
                        animation: spin 2s linear infinite; 
                        margin: 0 auto 20px;
                    }
                    @keyframes spin { 
                        0% { transform: rotate(0deg); } 
                        100% { transform: rotate(360deg); } 
                    }
                    button {
                        padding: 10px 20px;
                        background: #3498db;
                        color: white;
                        border: none;
                        border-radius: 5px;
                        cursor: pointer;
                        margin-top: 20px;
                    }
                    button:hover {
                        background: #2980b9;
                    }
                </style>
            </head>
            <body>
                <div class="loading-container">
                    <div class="spinner"></div>
                    <h2>Sistema Reusemi</h2>
                    <p>Iniciando servidor Spring Boot... Aguarde alguns instantes.</p>
                    <p>Se esta mensagem persistir, verifique se o servidor está rodando na porta 8080.</p>
                    <button onclick="window.location.reload()">Tentar Novamente</button>
                </div>
                <script>
                    // Tenta recarregar a cada 3 segundos
                    setInterval(function() {
                        console.log('Tentando conectar com o servidor...');
                        fetch('http://localhost:8080')
                            .then(response => {
                                if (response.ok) {
                                    window.location.reload();
                                }
                            })
                            .catch(error => {
                                console.log('Servidor ainda não disponível');
                            });
                    }, 3000);
                </script>
            </body>
            </html>
            """;

        webEngine.loadContent(loadingHtml);
    }

    private void showErrorPage(String message) {
        String errorHtml = """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Erro - Reusemi</title>
                <style>
                    body { 
                        font-family: Arial, sans-serif; 
                        display: flex; 
                        justify-content: center; 
                        align-items: center; 
                        height: 100vh; 
                        background: #f8d7da;
                        color: #721c24;
                        margin: 0;
                    }
                    .error-container { 
                        text-align: center; 
                        padding: 20px;
                        border: 1px solid #f5c6cb;
                        border-radius: 5px;
                        background: #f8d7da;
                    }
                    button {
                        padding: 10px 20px;
                        background: #dc3545;
                        color: white;
                        border: none;
                        border-radius: 5px;
                        cursor: pointer;
                        margin-top: 20px;
                    }
                    button:hover {
                        background: #c82333;
                    }
                </style>
            </head>
            <body>
                <div class="error-container">
                    <h2>⚠️ Erro no Sistema</h2>
                    <p>%s</p>
                    <button onclick="window.location.reload()">Tentar Novamente</button>
                    <button onclick="desktopApp.openInBrowser('http://localhost:8080')" style="background: #28a745; margin-left: 10px;">Abrir no Navegador</button>
                </div>
            </body>
            </html>
            """.formatted(message);

        webEngine.loadContent(errorHtml);
    }

    private void waitForSpringBoot() {
        // Mostra página de loading inicial
        showLoadingPage();

        // Verifica periodicamente se o Spring Boot está pronto
        new Thread(() -> {
            try {
                // Aguarda o Spring Boot inicializar (aumentei para 5 segundos)
                Thread.sleep(5000);

                Platform.runLater(() -> {
                    loadSpringBootApp();
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    // Ponte para comunicação entre Java e JavaScript
    public class DesktopAppBridge {
        public void showMessage(String message) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mensagem do Sistema");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
            });
        }

        public void exitApplication() {
            Platform.exit();
        }

        public void openInBrowser(String url) {
            try {
                getHostServices().showDocument(url);
            } catch (Exception e) {
                showError("Erro ao abrir navegador: " + e.getMessage());
            }
        }

        public String getAppVersion() {
            return "Reusemi Desktop 1.0";
        }
    }

    private void showError(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Ocorreu um erro na aplicação desktop");
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    @Override
    public void stop() {
        // Fecha o Spring Boot quando a aplicação é fechada
        if (DesktopApplication.getSpringContext() != null) {
            DesktopApplication.getSpringContext().close();
        }
    }
}