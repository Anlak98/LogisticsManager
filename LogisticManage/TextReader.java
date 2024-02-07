package logisticManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



import java.io.FileNotFoundException;
import java.io.FileReader;
/**
 * 
 * 
 * @author Anas Laknitri
 * @version 11/12/2023
 * <br>
 * The class is used to read values from a given list of products or users. 
 * The file.txt of products must have the following format: name price id. 
 * The file.txt of users must have the following format: name password. 
 * Each file path must be instantiated with it's own object and the relative method must be used like described below.
 * The user entity is defined with the class {@see User} and the product entity is defined with the class {@see Product}.
 * <br>
 *  
 */

public class TextReader {
	
	private Scanner readedFile;
	/**
	 * This is the only constructor of the class and is used to define the path to the file.txt of users or products.
	 * @param path Is the path to the file.txt on which the user list or product list is saved. 
	 * @throws FileNotFoundException If the file.txt can not be find.
	 * @throws Exception If an unexpected error occurs.
	 */
	public TextReader(String path) throws FileNotFoundException, Exception{
		try {
			
			readedFile = new Scanner(new FileReader(path));
			
		
		} catch (FileNotFoundException e) {
			System.out.printf("\nError: Class TextReader could not find %s.\n", path);
		} catch (Exception d) {
			System.out.printf("\nError: Unexpected error in the TextReader class constructor.\n", path);
		}
	}
	/**
	 * The method is used to create a list of products given the path of the products text file. The format of the text must be: name price id.
	 * @return The list of products is returned. 
	 * @throws IllegalArgumentException If a different parameter than a string is passed to the method the error is signaled.
	 * @throws Exception If an unexpected error occurs.
	 */
	public List<StockProduct> ReadProducts() throws IllegalArgumentException, Exception{
		 List<StockProduct> productsList = new ArrayList<>();

	        while (readedFile.hasNextLine()) {
	            String line = readedFile.nextLine();
	            String[] parts = line.split(",");
	            if (parts.length == 7) {
	                try {
	                    // Esegui il parsing dei valori
	                    int code = Integer.parseInt(parts[0]);
	                    String name = parts[1];
	                    double price = Double.parseDouble(parts[2]);

	                    String position = parts[3].trim();

	                    int quantity = Integer.parseInt(parts[4]);
	                    
	                    int inventory = Integer.parseInt(parts[5]);
	                    
	                    String category = parts[6];
	                    StockProduct product = new StockProduct(code, name, price, position, quantity, inventory, category);
	                    productsList.add(product);
	                } catch (NumberFormatException e) {
	                    System.out.println("Error parsing product in the textreader class: " + e.getMessage());
	                }
	            } else {
	                System.out.println("Errore textreader: numero insufficiente di elementi nella riga o riga vuota per prodotti."+parts.length);
	            }
	        }
	        readedFile.close();
	        return productsList;
		
	};
	/**
	 * The method is used to create a list of users given the path of the users text file. The format of the text must be: user_name password.
	 * @return The list of users is returned. 
	 * @throws IllegalArgumentException If a different parameter than a string is passed to the method the error is signaled.
	 * @throws Exception If an unexpected error occurs.
	 */
	public List<User> ReadUsers() throws IllegalArgumentException, Exception{
		List<User> usersList = new ArrayList<User>();
		try {
			while(readedFile.hasNext()) {
				String line = readedFile.nextLine();
	            String[] parts = line.split(",");
	            if (parts.length >= 4) {
	                try {
	                    String name = parts[0];
	                    String password=parts[1];
	                    String role=parts[2];
	                    int permission = Integer.parseInt(parts[3]);
	                    int id =Integer.parseInt(parts[4]);
	                    User user = new User(name, password, role, permission, id);
	                    usersList.add(user);
	                } catch (NumberFormatException e) {
	                    System.out.println("Error parsing user in the textreader class: " + e.getMessage());
	                }
	            } else {
	                System.out.println("Errore textreader: numero insufficiente di elementi nella riga o riga vuota per utenti."+parts.length);
	            }
			};
		} catch (IllegalArgumentException e) {
			System.out.print("\nError: Unexpected parameter passed to the ReadUsers method of the TextReader class.\n");
		} catch (Exception d) {
			System.out.print("\nError: Unexpected error in the ReadUsers method of the TextReader class.\n");
		}
		return usersList;
		
	};
}

