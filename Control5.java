package logisticManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
/**
 * @author Anas Laknitri
 * @version 10/12/2023
 * <br>
 * This class is used to manage the stack analytics scene of the s5.fxml file and realize it's processes.
 * The relative css style is managed by the s5style.css file.
 * <br>
 * @param username Is used to show the current user's username on the scene.
 * @param stackedbarchart Is used to show the stackedbarchart. 
 * @param userName Is used to store the current user's username.
 * @param user Is used to store the current User object.
 */
public class Control5 implements Initializable{
    @FXML
    private Label username;
    @FXML
    private StackedBarChart stackedbarchart;
    String userName;
    User user;
    
    
    /**
     * The method initialize is called by the javafx protocol and is used to manage the products stack chart creation. 
     */
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
        	CategoryAxis xAxis    = new CategoryAxis();
            xAxis.setLabel("Devices");
            xAxis.getCategories().addAll("Correct", "positive error", "negative error");

            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("Visits");
            
            
            List<StockProduct> productsList = new ArrayList<StockProduct>();
    		try {
    			productsList = (new TextReader("txtfiles/prodotti.txt")).ReadProducts();
    		} catch (IllegalArgumentException e) {
    			e.printStackTrace();
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		XYChart.Series dataSeries1 = new XYChart.Series();
    		XYChart.Series dataSeries2 = new XYChart.Series();
    		XYChart.Series dataSeries3 = new XYChart.Series();
    		XYChart.Series dataSeries4 = new XYChart.Series();
    		dataSeries1.setName("Furniture");
    		dataSeries2.setName("Electronics");
    		dataSeries3.setName("Clothing");
    		dataSeries4.setName("Stationery");
    		int countFur=0, countEl=0, countClo=0, countStat=0;
    		for(StockProduct product:productsList) {
    			
    			if((product.getCategory()).equals("furniture")) {
    				countFur++;
    			}else if((product.getCategory()).equals("clothing")) {
    				countClo++;
    			}else if((product.getCategory()).equals("electronics")){
    				countEl++;
    			}else {
    				countStat++;
    			}
    		}
    		dataSeries1.getData().add(new XYChart.Data("Furniture", countFur));
    		dataSeries2.getData().add(new XYChart.Data("Electronics", countEl));
    		dataSeries3.getData().add(new XYChart.Data("Clothing", countClo));
    		dataSeries4.getData().add(new XYChart.Data("Stationery", countStat));
    		stackedbarchart.getData().add(dataSeries1);
    		stackedbarchart.getData().add(dataSeries2);
    		stackedbarchart.getData().add(dataSeries3);
    		stackedbarchart.getData().add(dataSeries4);
		} catch (Exception e) {
			System.err.println("\nError in the initialize method of the control5 class: " + e.getMessage());
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
			System.err.println("\nError in the logout method of the control5 class: " + e.getMessage());
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
			System.err.println("\nError in the pulluser method of the control5 class: " + e.getMessage());
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
			System.err.println("\nError in the inventory method of the control5 class: " + e.getMessage());
		}
     }
     /**
      * This method is used as action handler when the piechart button is used by the user.
      * The piechart scene will be displayed to the user. 
      * @param event Used by Javafx protocol to manage the interaction between user and stack button.
      * @throws IOException
      */
     @FXML
     void pie(ActionEvent event) throws IOException {
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
			System.err.println("\nError in the piechart method of the control5 class: " + e.getMessage());
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
			System.err.println("\nError in the team method of the control5 class: " + e.getMessage());
		}
     }
     
     
}
