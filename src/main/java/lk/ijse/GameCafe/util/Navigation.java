package lk.ijse.GameCafe.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;

public class Navigation {
    private static Parent rootNode;
    private static Scene scene;
    private static Stage stage;

    public static void switchNavigation(String path, ActionEvent event) throws IOException {
        rootNode= FXMLLoader.load(Navigation.class.getResource("/view/"+path));

        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();

        scene =new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void switchPaging(Pane pane, String path) throws IOException {
        pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(Navigation.class.getResource("/view/" + path));

        Parent root=loader.load();
        pane.getChildren().add(root);

    }

    public static void closePane2(){

    }

    public static void switchPaging2(Pane pane, String path) throws IOException {
        //pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(Navigation.class.getResource("/view/" + path));

        Parent root=loader.load();
        //pane.getChildren().add(root);

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(pane.getScene().getWindow());

        // Set the title if needed
        popupStage.setTitle("Popup Title");

        // Create a new scene with the loaded root and set it to the stage
        Scene scene = new Scene(root);
        popupStage.setScene(scene);

        // Center the stage on the screen
        popupStage.centerOnScreen();

        // Show the stage
        popupStage.showAndWait();

    }

    public static void closePopUpStage(Stage stage) {
        stage.close();
    }
}
