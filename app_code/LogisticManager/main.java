package logisticManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
/**
 * @author Anas Laknitri
 * @version 10/12/2023
 * <br>
 * This is the main class that starts the application. 
 * <br>
 */
public class main extends Application {
	@Override
	/**
	 * The `start` method loads the initial scene: `s1.fxml`.
	 * @throws Exception if an unexpected error occurs.
	 */
	public void start(Stage stage) throws Exception{
		try {
			Parent login = FXMLLoader.load(getClass().getResource("s1.fxml")); 
			//login.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			Scene scene=new Scene(login);
			
			scene.setFill(null);
    		stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			System.err.println("Error in Main class, start method: Unexpected error."+"\nerror: "+e);
			e.printStackTrace();
		}
	}
	/**
	 * The main method launches the application.
	 * @param args contains environment parameters and a JavaFX utility used to initialize the application.
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
