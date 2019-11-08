package application;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// Initializing the FXML loader
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Admin_Key.fxml"));

			// Creating an instance of the controller class
			Controller controller = new Controller();

			// Setting the controller for the login scene
			loader.setController(controller);

			// Loading the FXML document
			Parent root = loader.load();

			// Giving the controller this instance of the primary
			// stage
			controller.setPrimaryStage(primaryStage);
			
			// Initializing the scene
			Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
			Scene scene = new Scene(root, resolution.getWidth(), resolution.getHeight());

			// Adding the style sheet
			scene.getStylesheets()
					.add(getClass()
							.getResource("/View/application.css")
							.toExternalForm());

			//setting scaling
			double width = resolution.getWidth();
			double height = resolution.getHeight();
			double w = width/scene.getWidth();
			double h = height/scene.getHeight();
			Scale scale = new Scale(w,h,0,0);
			root.getTransforms().add(scale);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("SP Analytics");
			primaryStage.setMaximized(true);
			primaryStage.setFullScreen(true);
			primaryStage.setFullScreenExitHint("");
			primaryStage.show();
			
		} catch (Exception e) {
			String msg = "Error";
			System.out.println(e);
			Alert err = new Alert(AlertType.CONFIRMATION, e.toString());
			err.show();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}