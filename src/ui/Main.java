package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ui.fxml"));
        Parent root = loader.load();
        Controller contr = loader.getController();
        primaryStage.setTitle("Bellagios Pizza Goose Hollow Communications Log");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setResizable(false);
        primaryStage.setOnHidden(event -> contr.saveLogbook());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
