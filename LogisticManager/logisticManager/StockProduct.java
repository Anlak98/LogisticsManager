package logisticManager;


/**
 * @author Anas Laknitri
 * @version 11/12/2023
 * <br>
 * A StockProduct object has a unique code, a name, a price, a position in the warehouse, a quantity, 
 * a quantity relative to the last inventory and a category that defines it's commercial area.
 * The class variables are private. The methods of the setters and getters must be used to obtain access.
 * Try_catch structures are used to manage errors. 
 * <br>
 *  @param code Defines the product's unique code.
 *  @param name Defines the product's name.
 *  @param price Defines the product's price.
 *  @param position Defines the product's position in the warehouse.
 *  @param quantity Defines the product's quantity.
 *  @param lastInv Defines the product's inventory countend quantity.
 *  @param category Defines the product's commercial category.
 */
public class StockProduct {
	
	private int code;
	private String name;
	private Double price;
	private String position;
	private int quantity;
	private int lastInv;
	private String category;
	

	/**
	 * Explicit constructor: the parameters given to the constructor are assigned to the object proprieties.
	 *  @param code Defines the product's unique code.
	 *  @param name Defines the product's name.
	 *  @param price Defines the product's price.
	 *  @param position Defines the product's position in the warehouse.
	 *  @param quantity Defines the product's quantity.
	 *  @param lastInv Defines the product's inventory countend quantity.
	 *  @param category Defines the product's commercial category.
	 */
	public StockProduct(int code, String name, Double price, String position, int quantity, int lastInv, String category) {
		this.code = code;
		this.name = name;
		this.price = price;
		this.position = position;
		this.quantity = quantity;
		this.lastInv = lastInv;
		this.category=category;
	}
	/**
	 * Explicit constructor (variant): the parameters given to the constructor are assigned to the object proprieties.
	 *  @param code Defines the product's unique code.
	 *  @param name Defines the product's name.
	 *  @param price Defines the product's price.
	 *  @param position Defines the product's position in the warehouse.
	 *  @param quantity Defines the product's quantity.
	 *  @param lastInv Defines the product's inventory countend quantity.
	 */
	public StockProduct(int code, String name, Double price, String position, int quantity, int lastInv) {
		this.code = code;
		this.name = name;
		this.price = price;
		this.position = position;
		this.quantity = quantity;
		this.lastInv = lastInv;
	}
	/**
	 * Explicit constructor (variant): the parameters given to the constructor are assigned to the object proprieties.
	 *  @param code Defines the product's unique code.
	 *  @param name Defines the product's name.
	 *  @param price Defines the product's price.
	 *  @param position Defines the product's position in the warehouse.
	 *  @param quantity Defines the product's quantity.
	 */
	public StockProduct(int code, String name, Double price, String position, int quantity) {
		this.code = code;
		this.name = name;
		this.price = price;
		this.position = position;
		this.quantity = quantity;
	}


	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * @return the last inventory
	 */
	public int getInv() {
		return lastInv;
	}



	/**
	 * @param the inventory to set
	 */
	public void setInv(int lastInv) {
		this.lastInv = lastInv;
	}
	
	
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}



	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}



	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}



	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}



	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}



	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}



	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}