package logisticManager;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
/**
 * @author Anas Laknitri
 * @version 10/12/2023
 * <br>
 * This class is used to manage the inventory scene of the s3.fxml file and realize it's processes.
 * The relative css style is managed by the s3style.css file.
 * 
 * <br>
 * @param tableview Shows shows the list of the products.
 * @param code Manages the code coloumn of the products list. This Column will contain the id codes of the products.
 * @param name Manages the name coloumn of the products list. This Column will contain the names of the products.
 * @param position Manages the position coloumn of the products list. This Column will contain the position of the products in the warehouse.
 * @param quantity Manages the quantity coloumn of the products list. This Column will contain the quantity of the products.
 * @param codelab Collects the product's id code.
 * @param namelab Collects the product's name.
 * @param aislelab Collects the product's position aisle.
 * @param placelab Collects the product's position place.
 * @param inputQuantity Collects the product's quantity countend by the inventory operator (user).
 * @param result Is used to show to the user eventual messages regarding his activity on the scene.
 * @param username Is used to show the current user's username on the scene.
 * @param userName Is used to store the current user's username.
 * @param user Is used to store the current User object.
 */
public class Control3 implements Initializable{
	@FXML
	private TableView<StockProduct> tableview;
	@FXML
	private TableColumn<StockProduct, Integer> code;
	@FXML
	private TableColumn<StockProduct, String> name;
	@FXML
	private TableColumn<StockProduct, String> position;
	@FXML
	private TableColumn<StockProduct, Integer> quantity;
	@FXML
    private Label codelab;
    @FXML
    private Label namelab;
    @FXML
    private Label aislelab;
    @FXML
    private Label placelab;
    @FXML
    private Label result;
    @FXML
    private TextField inputQuantity;
    @FXML
    private Label username;

    String userName;
    User user;

    /**
	 * The method initialize is called by the javafx protocol and is used to manage the products list creation. 
	 */
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			code.setCellValueFactory(new PropertyValueFactory<StockProduct, Integer>("code"));
			name.setCellValueFactory(new PropertyValueFactory<StockProduct, String>("name"));
			position.setCellValueFactory(new PropertyValueFactory<StockProduct, String>("position"));
			quantity.setCellValueFactory(new PropertyValueFactory<StockProduct, Integer>("quantity"));
			pushData();
		} catch (Exception e) {
			System.err.println("\nError in the initialize method of the control3 class: " + e.getMessage());
		}
	}
    /**
	 * The method creates the java list of products and pushes them in the tableview list of the scene.
	 * @throws IllegalArgumentException
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public void pushData() throws IllegalArgumentException, FileNotFoundException, Exception{
		try {
			List<StockProduct> productsList = new ArrayList<StockProduct>();
			productsList = (new TextReader("txtfiles/prodotti.txt")).ReadProducts();
			
			tableview.getItems().addAll(productsList);
		} catch (Exception e) {
			System.err.println("\nError in the pushdata method of the control3 class: " + e.getMessage());
		}
	}
	/**
	 * This method is used as action handler that updates the textfileds of the products properties when a row of the table is clicked.
	 * @param event Used by Javafx protocol to manage the interaction between user and the clicked row.
	 */
	 @FXML
	 void rowClicked(MouseEvent event) {
	     try {
	    	 StockProduct clickedProduct = tableview.getSelectionModel().getSelectedItem();
		     codelab.setText(String.valueOf(clickedProduct.getCode()));
		     namelab.setText(clickedProduct.getName());
		     String[] parts = clickedProduct.getPosition().split(" ");
		     String aisle=parts[1];
		     String place=parts[3];
		     aislelab.setText("Aisle: "+aisle);
		     placelab.setText("Place: "+place);
		     inputQuantity.setText(String.valueOf(clickedProduct.getQuantity()));
		} catch (Exception e) {
			System.err.println("\nError in the rowclicked method of the control3 class: " + e.getMessage());
		}
	 }
	 /**
	  * This method is used as action handler that checks the inventory input of the user.
	  * A message is displayed showing the user the inventory status of the last updated product.
	  * @param event Used by Javafx protocol to manage the interaction between user and check button.
	  * @throws IOException
	  */
	 public void check(ActionEvent event) throws IOException {
     	try {
     		ObservableList<StockProduct> currentTableData = tableview.getItems();
     		if(codelab.getText().equals("code")) {
            	result.setText("Select an item from the list.");
            	return;
            }
     		int currentId = Integer.parseInt(codelab.getText());
            int cycleId=0;
            if(inputQuantity.getText().isEmpty()) {
            	result.setText("No input value.");
            	return;
            }
            for (StockProduct product : currentTableData) {
                if(product.getCode() == currentId) {
                    int inv=Integer.parseInt(inputQuantity.getText())-(product.getQuantity());
                    product.setInv(inv);
                    if(inv==0){
                   	 result.setText("No inventory difference.");
                    }else if(inv<0) {
                   	 result.setText("Negative inventory difference: "+inv);
                    }else {
                   	 result.setText("Positive inventory difference: "+inv);
                    };

                	String workingDirectory = System.getProperty("user.dir");
                    String filePath = workingDirectory + "/txtfiles/prodotti.txt";
                    File file = new File(filePath);
                    if (!file.exists()) {

                         System.err.println("Aggiornamento file prodotti fallito: file non trovato.");
                    }
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.close();
                    ObservableList<StockProduct> currentTableData2 = tableview.getItems();
   				 for (StockProduct product2 : currentTableData2) {
   					String[] parts = product2.getPosition().split(" ");
   					 String aisle=parts[1];
   					 String place=parts[3];
   					 String prod=String.valueOf(product2.getCode())+","+product2.getName()+","+String.valueOf(product2.getPrice())+","+"aisle "+aisle+" place "+place+","+String.valueOf(product2.getQuantity()+","+String.valueOf(product2.getInv())+","+product2.getCategory());
   					writer = new BufferedWriter(new FileWriter(file, true));
   					 writer.write(prod);
   					 writer.newLine();
   					 writer.close();
   				 }
                    
                    return;
                }
                cycleId++;
            }

		} catch (Exception e) {
			System.err.println("\nError in the check method of the control3 class: " + e.getMessage());
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
			System.err.println("\nError in the logout method of the control3 class: " + e.getMessage());
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
			System.err.println("\nError in the pulluser method of the control3 class: " + e.getMessage());
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
			System.err.println("\nError in the products method of the control3 class: " + e.getMessage());
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
      * This method is used as action handler when the team button is used by the user.
      * The team scene will be displayed to the user only if the user's permission level is = 2.    
      * @param event Used by Javafx protocol to manage the interaction between user and team button.
      * @throws IOException
      */  
     @FXML
     void team(ActionEvent event) throws IOException {
     	try {
     		if(user.getPermission()==2) {
         		Parent root;
             	Stage stage;
        		Scene scene;
        		
        		FXMLLoader loader= new FXMLLoader(getClass().getResource("s6.fxml")); 
        		root =loader.load();
        		
        		Control6 control6 =loader.getController();
        		control6.pulluser(user);
        		
        		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        		scene = new Scene(root);
        		stage.setScene(scene);
         		stage.setResizable(false);
        		stage.show();
         	}else{
        		Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Access Denied");
        		alert.setHeaderText("You do not have sufficient privileges to view this content.");
        		alert.setContentText("Please log in with appropriate credentials or contact your it support.");
        		alert.showAndWait();
        	}
		} catch (Exception e) {
			System.err.println("\nError in the team method of the control3 class: " + e.getMessage());
		}
     }
}
    
