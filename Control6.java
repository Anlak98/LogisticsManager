package logisticManager;

import java.awt.MenuItem;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
/**
 * @author Anas Laknitri
 * @version 10/12/2023
 * <br>
 * This class is used to manage the team organization scene of the s6.fxml file and realize it's processes.
 * The relative css style is managed by the s6style.css file.
 * <br>
 * @param tableview Shows the list of the users
 * @param name Manages the name coloumn of the users list. This Column will contain the usernames of the users.
 * @param role Manages the role coloumn of the users list. This Column will contain the roles of the users.
 * @param password Manages the password coloumn of the users list. This Column will contain the passwords of the users.
 * @param permission Manages the permission level coloumn of the users list. This Column will contain the permission level of the users.
 * @param id Manages the unique id coloumn of the users list. This Column will contain the ids of the users.
 * @param inputName Collects the user's name.
 * @param inputRole Collects the users's role.
 * @param inputPassword Collects the user's password.
 * @param inputPermission Collects the user's permission.
 * @param inputId Collects the user's id.
 * @param username Is used to show the current user's username on the scene.
 * @param errorlabel Shows an eventual informational messages about the activity of the user on the application.
 * @param stackedbarchart Is used to show the stackedbarchart. 
 * @param userName Is used to store the current user's username.
 * @param user Is used to store the current User object.
 */
public class Control6 implements Initializable{
	@FXML
	private TableView<User> tableview;
	@FXML
	private TableColumn<User, String> name;
	@FXML
	private TableColumn<User, String> role;
	@FXML
	private TableColumn<User, String> password;
	@FXML
	private TableColumn<User, Integer> permission;
	@FXML
	private TableColumn<User, Integer> id;
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputRole;
    @FXML
    private TextField inputPassword;
    @FXML
    private TextField inputPermission;
    @FXML
    private TextField inputId;
    @FXML
    private Label username;
    @FXML
    private Label errorlabel;
    
    String userName;
    User user;
    
    /**
     * The method initialize is called by the javafx protocol and is used to manage the users list table creation. 
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
			role.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
			password.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
			permission.setCellValueFactory(new PropertyValueFactory<User, Integer>("permission"));
			id.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));

			try {
				pushData();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.err.println("\nError in the initialize method of the control6 class: " + e.getMessage());
		}
	}
	
	
	/**
	 * The method creates the java list of users and pushes them in the tableview list of the scene.
	 * @throws IllegalArgumentException
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public void pushData() throws IllegalArgumentException, FileNotFoundException, Exception{
		try {
			List<User> userList = new ArrayList<User>();
			userList = (new TextReader("txtfiles/users.txt")).ReadUsers();
			tableview.getItems().addAll(userList);
		} catch (Exception e) {
			System.err.println("\nError in the pushdata method of the control6 class: " + e.getMessage());
		}
	}
        

	/**
	 * This method is used as action handler when the update button is used by yhe user.
	 * A new users is updated with the new informations given by the user as inputs.
	 * The list is updated only if the name is reconized.
	 * otherwise an error informative message is shown to the user.
	 * @param event Used by Javafx protocol to manage the interaction between user and update button.
	 * @throws IOException
	 */     
    public void update(ActionEvent event) throws IOException {
    	try {
    		if (inputId.getText().isEmpty()) {
    			errorlabel.setText("No user id input value");
    			return;
			}
    		ObservableList<User> currentTableData = tableview.getItems();
            for (User user : currentTableData) {
                if(user.getId()==Integer.parseInt(inputId.getText())) {
                	
                	user.setName(inputName.getText());
                	user.setPassword(inputPassword.getText());
                	user.setPermission(Integer.parseInt(inputPermission.getText()));
                	user.setRole(inputRole.getText());
                    
                    tableview.setItems(currentTableData);
                    tableview.refresh();
                    
                	String workingDirectory = System.getProperty("user.dir");
                    String filePath = workingDirectory + "/txtfiles/users.txt";
                    File file = new File(filePath);
                    if (!file.exists()) {

                         System.err.println("Error in the update method of the control6 class: file not found.");
                         return;
                    }
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.close();
                    ObservableList<User> currentTableData2 = tableview.getItems();
                    for (User user2 : currentTableData2) {
                    	String user_write=String.valueOf(user2.getName()+","+user2.getPassword()+","+user2.getRole()+","+user2.getPermission()+","+user2.getId());
                    	writer = new BufferedWriter(new FileWriter(file, true));
                        writer.write(user_write);
                        writer.newLine();
                        writer.close();
                        errorlabel.setText("User updated");
                    }
                    return;
                }
            }
            errorlabel.setText("Id not found");
		} catch (Exception e) {
			System.err.println("\nError in the update method of the control6 class: " + e.getMessage());
		}
    }
    
    /**
	 * This method is used as action handler when the remove button is used by the user.
	 * The user with the specified id is removed from the list only if the id is reconized 
	 * otherwise an error informative message is shown to the user.
	 * @param event Used by Javafx protocol to manage the interaction between user and remove button.
	 * @throws IOException
	 */
    public void remove(ActionEvent event) throws IOException {
    	try {
    		if (inputId.getText().isEmpty()) {
    			errorlabel.setText("No user id input value");
    			return;
			}
    		ObservableList<User> currentTableData = tableview.getItems();
    		int cycleId=0;
            for (User user : currentTableData) {
                if(user.getId()==Integer.parseInt(inputId.getText())) {
                	
                	currentTableData.remove(cycleId);
                	errorlabel.setText("Product removed.");
                	tableview.setItems(currentTableData);
                    tableview.refresh();
                    
                	String workingDirectory = System.getProperty("user.dir");
                    String filePath = workingDirectory + "/txtfiles/users.txt";
                    File file = new File(filePath);
                    if (!file.exists()) {

                         System.err.println("Error in the update method of the control6 class: file not found.");
                         return;
                    }
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.close();
                    ObservableList<User> currentTableData2 = tableview.getItems();
                    for (User user2 : currentTableData2) {
                    	String user_write=String.valueOf(user2.getName()+","+user2.getPassword()+","+user2.getRole()+","+user2.getPermission()+","+user2.getId());
                    	writer = new BufferedWriter(new FileWriter(file, true));
                        writer.write(user_write);
                        writer.newLine();
                        writer.close();
                        errorlabel.setText("User removed");
                    }
                    return;
                }
                cycleId++;
            }
            errorlabel.setText("Id not found");
		} catch (Exception e) {
			System.err.println("\nError in the remove method of the control6 class: " + e.getMessage());
		}
    }
    /**
	 * This method is used as action handler when the add button is used by yhe user.
	 * A new user is added on the list only if the id is new and unique otherwise an error informative message is shown to the user.
	 * @param event Used by Javafx protocol to manage the interaction between user and add button.
	 * @throws IOException
	 */
    public void add(ActionEvent event) throws IOException {
    	try {
    		if (inputId.getText().isEmpty()) {
    			errorlabel.setText("No user id input value");
    			return;
			}
    		ObservableList<User> currentTableData = tableview.getItems();
            for (User user : currentTableData) {
            	if(user.getId()==Integer.parseInt(inputId.getText())) {
            		errorlabel.setText("User id arleady exiting");
            		return;
            	}
            }
            currentTableData.add(new User(inputName.getText(), inputPassword.getText(), inputRole.getText(), Integer.parseInt(inputPermission.getText()), Integer.parseInt(inputId.getText())));
            
            tableview.setItems(currentTableData);
            tableview.refresh();
            
        	String workingDirectory = System.getProperty("user.dir");
            String filePath = workingDirectory + "/txtfiles/users.txt";
            File file = new File(filePath);
            if (!file.exists()) {

                 System.err.println("Error in the add method of the control6 class: file not found.");
                 return;
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.close();
            ObservableList<User> currentTableData2 = tableview.getItems();
            for (User user2 : currentTableData2) {
            	String user_write=String.valueOf(user2.getName()+","+user2.getPassword()+","+user2.getRole()+","+user2.getPermission()+","+user2.getId());
            	writer = new BufferedWriter(new FileWriter(file, true));
                writer.write(user_write);
                writer.newLine();
                writer.close();
                errorlabel.setText("User added");
            }
		} catch (Exception e) {
			System.err.println("\nError in the add method of the control6 class: " + e.getMessage());
		}
    }
    
    
    /**
   	 * This method is used as action handler that updates the textfileds of the users properties when a row of the table is clicked.
   	 * @param event Used by Javafx protocol to manage the interaction between user and the clicked row.
   	 */
    @FXML
    void rowClicked(MouseEvent event) {
        try {
        	User clickedUser = tableview.getSelectionModel().getSelectedItem();
            inputName.setText(clickedUser.getName());
            inputPassword.setText(clickedUser.getPassword());
            inputRole.setText(clickedUser.getRole());
            inputPermission.setText(String.valueOf(clickedUser.getPermission()));
            inputId.setText(String.valueOf(clickedUser.getId()));
		} catch (Exception e) {
			System.err.println("\nError in the rowclicked method of the control6 class: " + e.getMessage());
		}
    }
        
    /**
     * This method is used to update the user object of the class User that will contain the informations of the logged user.
     * The textfields are cleared from the eventual values. The usarname label will be updated with the username.
     * @param user Used to pass the user's informtions between controllers.
     */
    public void pulluser(User user) {
    	try {
    		username.setText("Hi "+user.getName()+"!");
        	this.userName=user.getName();
        	this.user=user;
		} catch (Exception e) {
			System.err.println("\nError in the pulluser method of the control6 class: " + e.getMessage());
		}
    }
    /**
	 * This method is used as action handler when the logout button is used by the user.
	 * @param event Used by Javafx protocol to manage the interaction between user and logout button.
	 */ 
    public void logout(ActionEvent event) throws IOException {
    	try {
    		Parent root;
        	Stage stage;
    		Scene scene;
    		root =FXMLLoader.load(getClass().getResource("s1.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
		} catch (Exception e) {
			System.err.println("\nError in the logout method of the control6 class: " + e.getMessage());
		}
     }
    /**
 	 * This method is used as action handler when the inventory button is used by the user.
 	 * The inventory scene will be displayed to the user only if the user's permission level is = or > than 1.
 	 * @param event Used by Javafx protocol to manage the interaction between user and inventory button.
 	 */ 
     @FXML
     void inventory(ActionEvent event) throws IOException {
     	try {
     		Parent root;
         	Stage stage;
    		Scene scene;
    		
    		FXMLLoader loader= new FXMLLoader(getClass().getResource("s3.fxml")); 
    		root =loader.load();
    		
    		Control3 control3 =loader.getController();
    		control3.pulluser(user);
    		
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
     		stage.setResizable(false);
    		stage.show();
		} catch (Exception e) {
			System.err.println("\nError in the inventory method of the control6 class: " + e.getMessage());
		}
     }
        
     /**
      * This method is used as action handler when the analytics button is used by the user.
    	 * The analytics scene will be displayed to the user only if the user's permission level is = or > than 1.    
      * @param event Used by Javafx protocol to manage the interaction between user and analytics button.
      * @throws IOException
      */
     @FXML
     void analytics(ActionEvent event) throws IOException {
     	try {
     		Parent root;
         	Stage stage;
    		Scene scene;
    		
    		FXMLLoader loader= new FXMLLoader(getClass().getResource("s4.fxml")); 
    		root =loader.load();
    		
    		Control4 control4 =loader.getController();
    		control4.pulluser(user);
    		
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
     		stage.setResizable(false);
    		stage.show();
		} catch (Exception e) {
			System.err.println("\nError in the analytics method of the control3 class: " + e.getMessage());
		}
     }
        
        /**
    	 * This method is used as action handler when the products button is used by the user.
    	 * The products scene will be displayed to the user.
    	 * @param event Used by Javafx protocol to manage the interaction between user and inventory button.
    	 */ 
         @FXML
         void products(ActionEvent event) throws IOException {
         	try {
         		Parent root;
             	Stage stage;
        		Scene scene;
        		
        		FXMLLoader loader= new FXMLLoader(getClass().getResource("s2.fxml")); 
        		root =loader.load();
        		
        		Control2 control2 =loader.getController();
        		control2.pulluser(user);
        		
        		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        		scene = new Scene(root);
        		stage.setScene(scene);
         		stage.setResizable(false);
        		stage.show();
    		} catch (Exception e) {
    			System.err.println("\nError in the products method of the control5 class: " + e.getMessage());
    		}
         }

}

