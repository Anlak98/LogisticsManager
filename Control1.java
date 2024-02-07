package logisticManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.server.LoaderHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.jar.Attributes.Name;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * @author Anas Laknitri
 * @version 10/12/2023
 * <br>
 * This class is used to manage the login scene of the s1.fxml file and realize it's processes.
 * The relative css style is managed by the s1style.css file.
 * <br>
 * @param stage Element used by JavaFX to define the window.
 * @param scene Graphic container of the visual elements of the application.
 * @param root The container of the nodes in the scene.
 * @param usertext Collects the user's username input.
 * @param passwordtext Collects the user's password input.
 * @param errorlabel Shows an eventual user error description regarding the correctness of the password or of the username.
 */
public class Control1{
	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private TextField usertext;
	@FXML
	private PasswordField passwordtext;
	@FXML
    private Label errorlabel;
	
	
	/**
	 * This method manages the outputs of the login scene in result of the users inputs.
	 * If the username is incorrect a message of incorrect username will be shown.
	 * If the password is incorrect a message of incorrect password will be shown.
	 * Blank inputs are interpreted as errors.
	 * @param event Used by Javafx protocol to manage the intercation between user and login button.
	 */
	public void login(ActionEvent event) {
		try {
			
			List<User> usersList = new ArrayList<User>();
			usersList = (new TextReader("txtfiles/users.txt")).ReadUsers();
			
			
			String username=usertext.getText();
			String password=passwordtext.getText();
			
			for (int i = 0; i < usersList.size(); i++) {						
				if(usersList.get(i).getName().equals(username)) {
				    if (usersList.get(i).getPassword().equals(password)) {
				    	
				    	FXMLLoader loader= new FXMLLoader(getClass().getResource("s2.fxml")); 
						root =loader.load();
						
						Control2 control2 =loader.getController();
						control2.pulluser(usersList.get(i));
						
						stage = (Stage)((Node)event.getSource()).getScene().getWindow();
						scene = new Scene(root);
						stage.setScene(scene);
						stage.centerOnScreen();
			    		stage.setResizable(false);
						stage.show();
				        return;
						}
				    	errorlabel.setText("Incorrect password");
						return;
				}
				if (i==usersList.size()-1) {
					errorlabel.setText("Incorrect username");
					return;
				}
			}
			
		}catch(FileNotFoundException e) {
			System.err.println("Error in controller1 class, login method: The requested file was not found.");
		    e.printStackTrace();
		}catch(Exception f) {
			System.err.println("Error in controller1 class, login method: Unexpected error."+f);
			f.printStackTrace();
		}

	}
	



}

