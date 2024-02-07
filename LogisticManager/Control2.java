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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
 * This class is used to manage the products list scene of the s2.fxml file and realize it's processes.
 * The relative css style is managed by the s2style.css file.
 * 
 * <br>
 * @param tableview Shows the list of the products and it is the main element of the view.
 * @param code Manages the code coloumn of the products list. This Column will contain the id codes of the products.
 * @param name Manages the name coloumn of the products list. This Column will contain the names of the products.
 * @param price Manages the price coloumn of the products list. This Column will contain the prices of the products.
 * @param position Manages the position coloumn of the products list. This Column will contain the position of the products in the warehouse.
 * @param quantity Manages the quantity coloumn of the products list. This Column will contain the quantity of the products.
 * @param category Manages the category coloumn of the products list. This Column will contain the category of the products.
 * @param inputCode Collects the product's id code.
 * @param inputName Collects the product's name.
 * @param inputPrice Collects the product's price.
 * @param inputAisle Collects the product's position aisle.
 * @param inputPlace Collects the product's position place.
 * @param inputQuantity Collects the product's quantity.
 * @param inputCategory Collects the product's id code.
 * @param errorlabel Is used to show to the user eventual error messages regarding his activity on the scene.
 * @param username Is used to show the current user's username on the scene.
 * @param userName Is used to store the current user's username.
 * @param user Is used to store the current User object.
 */
public class Control2 implements Initializable{
	@FXML
	private TableView<StockProduct> tableview;
	@FXML
	private TableColumn<StockProduct, Integer> code;
	@FXML
	private TableColumn<StockProduct, String> name;
	@FXML
	private TableColumn<StockProduct, Double> price;
	@FXML
	private TableColumn<StockProduct, String> position;
	@FXML
	private TableColumn<StockProduct, Integer> quantity;
	@FXML
	private TableColumn<StockProduct, String> category;
	@FXML
    private TextField inputCode;
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputPrice;
    @FXML
    private TextField inputAisle;
    @FXML
    private TextField inputPlace;
    @FXML
    private TextField inputQuantity;
    @FXML
    private TextField inputCategory;
    @FXML
    private Label errorlabel;
    @FXML
    private Label username;
    
    String userName;
    User user;

    
	/**
	 * The method initialize is called by the javafx protocol and is used to manage the products list creation. 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		try {
			code.setCellValueFactory(new PropertyValueFactory<StockProduct, Integer>("code"));
			name.setCellValueFactory(new PropertyValueFactory<StockProduct, String>("name"));
			price.setCellValueFactory(new PropertyValueFactory<StockProduct, Double>("price"));
			position.setCellValueFactory(new PropertyValueFactory<StockProduct, String>("position"));
			quantity.setCellValueFactory(new PropertyValueFactory<StockProduct, Integer>("quantity"));
			category.setCellValueFactory(new PropertyValueFactory<StockProduct, String>("category"));
			pushData();
		} catch (Exception e) {
			System.err.println("\nError in the initialize method of the control2 class: " + e.getMessage());
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
			System.err.println("\nError in the pushdata method of the control2 class: " + e.getMessage());
		}
	}
	/**
	 * This method is used as action handler when the add button is used by yhe user.
	 * A new product is added on the list only if the id is new and unique otherwise an error informative message is shown to the user.
	 * @param event Used by Javafx protocol to manage the interaction between user and add button.
	 * @throws IOException
	 */
	@FXML
    public void add(ActionEvent event) throws IOException {
		try {
			errorlabel.setText("");
			if (inputCode.getText().isEmpty() || inputName.getText().isEmpty() || inputPlace.getText().isEmpty() || inputPrice.getText().isEmpty() || inputQuantity.getText().isEmpty() || inputAisle.getText().isEmpty() || inputCategory.getText().isEmpty()) {
				errorlabel.setText("Incomplete inputs.");
				return;
			}
			
			ObservableList<StockProduct> currentTableData = tableview.getItems();
	        int currentId = Integer.parseInt(inputCode.getText());
	        for (StockProduct product : currentTableData) {
	            if(product.getCode() == currentId) {
	            	errorlabel.setText("The id arleady exists.");
	            	return;
	            }  
	        }
	        String position= "aisle "+inputAisle.getText()+" place "+inputPlace.getText();
	        currentTableData.add(new StockProduct(Integer.parseInt(inputCode.getText()), inputName.getText(), Double.parseDouble(inputPrice.getText()), position, Integer.parseInt(inputQuantity.getText()), 0, inputCategory.getText()));
	    	tableview.setItems(currentTableData);
	        tableview.refresh();
	        errorlabel.setText("Product added.");
	        
	    	String workingDirectory = System.getProperty("user.dir");
	        String filePath = workingDirectory + "/txtfiles/prodotti.txt";
	        File file = new File(filePath);
	        if (!file.exists()) {
	        	System.err.println("Error in the add method of the control2 class: file not found.");
	        }
	        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	        writer.close();
	        for (StockProduct product2 : currentTableData) {
	        	String[] parts = product2.getPosition().split(" ");
	            String aisle=parts[1];
	            String place=parts[3];
	        	String prod=String.valueOf(product2.getCode())+","+product2.getName()+","+String.valueOf(product2.getPrice())+","+"aisle "+aisle+" place "+place+","+String.valueOf(product2.getQuantity()+","+String.valueOf(product2.getInv())+","+product2.getCategory());
	        	writer = new BufferedWriter(new FileWriter(file, true));
	        	writer.write(prod);
	        	writer.newLine();
	            writer.close();
	        }
		} catch (Exception e) {
			System.err.println("\nError in the add method of the control2 class: " + e.getMessage());
		}
	}
	/**
	 * This method is used as action handler when the remove button is used by yhe user.
	 * A new product is removed from the list only if the id is reconized otherwise an error informative message is shown to the user.
	 * @param event Used by Javafx protocol to manage the interaction between user and remove button.
	 * @throws IOException
	 */
    public void remove(ActionEvent event) throws IOException {
        try {
        	errorlabel.setText("");
        	if (inputCode.getText().isEmpty() || inputName.getText().isEmpty() || inputPlace.getText().isEmpty() || inputPrice.getText().isEmpty() || inputQuantity.getText().isEmpty() || inputAisle.getText().isEmpty() || inputCategory.getText().isEmpty()) {
				errorlabel.setText("Incomplete inputs.");
				return;
			}
        	ObservableList<StockProduct> currentTableData = tableview.getItems();
            int currentId = Integer.parseInt(inputCode.getText());
            int cycleId=0;
            for (StockProduct product : currentTableData){
                if(product.getCode() == currentId) {
                	currentTableData.remove(cycleId);
                	errorlabel.setText("Product removed.");
                	tableview.setItems(currentTableData);
                    tableview.refresh();
                    
                	String workingDirectory = System.getProperty("user.dir");
                    String filePath = workingDirectory + "/txtfiles/prodotti.txt";
                    File file = new File(filePath);
                    if (!file.exists()) {

                         System.err.println("Error in the remove method of the control2 class: file not found.");
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
            errorlabel.setText("The product does not exixts.");
		} catch (Exception e) {
			System.err.println("\nError in the remove method of the control2 class: " + e.getMessage());
		}
     }
    /**
	 * This method is used as action handler when the update button is used by yhe user.
	 * A new product is updated with the new informations given by the user as inputs.
	 * The list is updated only if the id is reconized and if the user's permission level is = or > than 1 
	 * otherwise an error informative message is shown to the user.
	 * @param event Used by Javafx protocol to manage the interaction between user and update button.
	 * @throws IOException
	 */      
    public void update(ActionEvent event) throws IOException {
    	try {
    		errorlabel.setText("");
    		if (inputCode.getText().isEmpty() || inputName.getText().isEmpty() || inputPlace.getText().isEmpty() || inputPrice.getText().isEmpty() || inputQuantity.getText().isEmpty() || inputAisle.getText().isEmpty() || inputCategory.getText().isEmpty()) {
				errorlabel.setText("Incomplete inputs.");
				return;
			}
    		if(user.getPermission()>=1) {
        		ObservableList<StockProduct> currentTableData = tableview.getItems();
                int currentId = Integer.parseInt(inputCode.getText());
                int cycleId=0;
                for (StockProduct product : currentTableData) {
                    if(product.getCode() == currentId) {
                    	
                    	String position= "aisle "+inputAisle.getText()+" place "+inputPlace.getText();
                    	product.setCode(Integer.parseInt(inputCode.getText()));
                    	product.setName(inputName.getText());
                    	product.setPosition(position);
                    	product.setPrice(Double.parseDouble(inputPrice.getText()));
                    	product.setQuantity(Integer.parseInt(inputQuantity.getText()));
                        product.setCategory(inputCategory.getText());
                        tableview.setItems(currentTableData);
                        tableview.refresh();
                        errorlabel.setText("Product updated.");
                        //---
                    	String workingDirectory = System.getProperty("user.dir");
                        String filePath = workingDirectory + "/txtfiles/prodotti.txt";
                        File file = new File(filePath);
                        if (!file.exists()) {

                             System.err.println("Error in the update method of the control2 class: file not found.");
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
                errorlabel.setText("The product does not exixts.");
        	}else{
        		Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Access Denied");
        		alert.setHeaderText("You do not have sufficient privileges to view this content.");
        		alert.setContentText("Please log in with appropriate credentials or contact your it support.");
        		alert.showAndWait();
        	}
		} catch (Exception e) {
			System.err.println("\nError in the update method of the control2 class: " + e.getMessage());
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
            inputCode.setText(String.valueOf(clickedProduct.getCode()));
            inputName.setText(clickedProduct.getName());
            inputPrice.setText(String.valueOf(clickedProduct.getPrice()));
            String[] parts = clickedProduct.getPosition().split(" ");
            String aisle=parts[1];
            String place=parts[3];
            inputAisle.setText(aisle);
            inputPlace.setText(place);
            inputQuantity.setText(String.valueOf(clickedProduct.getQuantity()));
            inputCategory.setText(clickedProduct.getCategory());
		} catch (Exception e) {
			System.err.println("\nError in the rowclicked method of the control2 class: " + e.getMessage());
		}
    }
    /**
	 * This method is used as action handler when the serach button is used by yhe user.
	 * The search will be performed on the code id if this is present. Otherwise it will be performed with the name.
	 * @param event Used by Javafx protocol to manage the interaction between user and search button.
	 */ 
    public void search(ActionEvent event) {
    	try {
    		if (inputName.getText().isEmpty() && inputCode.getText().isEmpty()) {
            	errorlabel.setText("No search input.");
            	return;
			}
    		ObservableList<StockProduct> currentTableData = tableview.getItems();
            if(!inputCode.getText().isEmpty()) {
        	int currentId = Integer.parseInt(inputCode.getText());
            int cycleId=0;
            for (StockProduct product : currentTableData) {
                if(product.getCode() == currentId) {
                	inputCode.setText(String.valueOf(product.getCode()));
                    inputName.setText(product.getName());
                    inputPrice.setText(String.valueOf(product.getPrice()));
                    String[] parts = product.getPosition().split(" ");
                    String aisle=parts[1];
                    String place=parts[3];
                    inputAisle.setText(aisle);
                    inputPlace.setText(place);
                    inputQuantity.setText(String.valueOf(product.getQuantity()));
                    inputCategory.setText(product.getCategory());
                    tableview.getSelectionModel().select(product);
                    tableview.scrollTo(product);
                    return;
                }
                cycleId++;
                }
            }
            String currentName = inputName.getText();
            int cycleId=0;
            if (!inputName.getText().isEmpty()) {
            	for (StockProduct product : currentTableData) {
                    if(product.getName().toLowerCase().contains(currentName.toLowerCase())) {
                    	inputCode.setText(String.valueOf(product.getCode()));
                        inputName.setText(product.getName());
                        inputPrice.setText(String.valueOf(product.getPrice()));
                        String[] parts = product.getPosition().split(" ");
                        String aisle=parts[1];
                        String place=parts[3];
                        inputAisle.setText(aisle);
                        inputPlace.setText(place);
                        inputQuantity.setText(String.valueOf(product.getQuantity()));
                        inputCategory.setText(product.getCategory());
                        tableview.getSelectionModel().select(product);
                        tableview.scrollTo(product);
                        return;
                    }
                    cycleId++;
                }
			}
            
            errorlabel.setText("Product not found.");
            return;
		} catch (Exception e) {
			System.err.println("\nError in the search method of the control2 class: " + e.getMessage());
		}
     }
    /**
   	 * This method is used as action handler when the clear button is used by yhe user.
   	 * The textfields are cleared from the eventual values.
   	 * @param event Used by Javafx protocol to manage the interaction between user and clear button.
   	 */
    public void clear(ActionEvent event) {
    	try {
    		errorlabel.setText("");
    		inputCode.clear();
            inputName.clear();
            inputPrice.clear();
            inputAisle.clear();
            inputPlace.clear();
            inputQuantity.clear();
            inputCategory.clear();
		} catch (Exception e) {
			System.err.println("\nError in the clear method of the control2 class: " + e.getMessage());
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
			System.err.println("\nError in the pulluser method of the control2 class: " + e.getMessage());
		}
    }
    /**
	 * This method is used as action handler when the logout button is used by yhe user.
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
			System.err.println("\nError in the logout method of the control2 class: " + e.getMessage());
		}
     }
    
    /**
   	 * This method is used as action handler when the inventory button is used by yhe user.
   	 * The inventory scene will be displayed to the user only if the user's permission level is = or > than 1.
   	 * @param event Used by Javafx protocol to manage the interaction between user and inventory button.
   	 */ 
    @FXML
    void inventory(ActionEvent event) throws IOException {
    	try {
    		if(user.getPermission()>=1) {
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
        	}else{
        		Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Access Denied");
        		alert.setHeaderText("You do not have sufficient privileges to view this content.");
        		alert.setContentText("Please log in with appropriate credentials or contact your it support.");
        		alert.showAndWait();
        	}
		} catch (Exception e) {
			System.err.println("\nError in the inventory method of the control2 class: " + e.getMessage());
		}
    }
    /**
     * This method is used as action handler when the analytics button is used by yhe user.
   	 * The analytics scene will be displayed to the user only if the user's permission level is = or > than 1.    
     * @param event Used by Javafx protocol to manage the interaction between user and analytics button.
     * @throws IOException
     */
    @FXML
    void analytics(ActionEvent event) throws IOException {
		try {
			if(user.getPermission()>=1) {
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
			}else{
        		Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Access Denied");
        		alert.setHeaderText("You do not have sufficient privileges to view this content.");
        		alert.setContentText("Please log in with appropriate credentials or contact your it support.");
        		alert.showAndWait();
        	}
		} catch (Exception e) {
			System.err.println("\nError in the analytics method of the control2 class: " + e.getMessage());
		}
    }
    /**
     * This method is used as action handler when the team button is used by yhe user.
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
			System.err.println("\nError in the team method of the control2 class: " + e.getMessage());
		}
    }

}

