package logisticManager;

/**
 * 
 * 
 * @author Anas Laknitri
 * @version 11/12/2023
 * <br>
 * The class is used to define the user entity. A user is defined by a name and a password. Those parameters are private.
 * 
 * <br>
 * @param name Contains the user_name and is set by the constructor.
 * @param password Contains the user_password and is set by the constructor.
 *  
 */
public class User {
	
	private String name; 

	private String password;
	
	private String role;
	
	private int permission;
	
	private int id;
	
	/**
	 * Explicit constructor: the parameters given to the constructor are assigned to the class variables: name and password.
	 * @param name Is assigned to the user name explicitly.
	 * @param password Is assigned to the user password explicitly.
	 * @param role Is assigned to the user role explicitly.
	 * @param permission Is assigned to the user permission explicitly.
	 * @param id Is assigned to the user unique id code.
	 * @throws IllegalArgumentException If an illegal argument is passed as a parameter to name or password.
	 * @throws Exception If an unexpected error occurs.
	 */
	public User(String name, String password, String role, int permission, int id) throws IllegalArgumentException, Exception{ 
		
		try {
			this.name=name;
			this.password=password;
			this.role=role;
			this.permission=permission;
			this.id=id;
		} catch (IllegalArgumentException e) {
			System.out.print("\nError: Unexpected parameter passed to the User class constructor.\n");
		} catch (Exception d) {
			System.out.print("\nError: Unexpected error in the User class constructor.\n");
		}
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Implicit constructor: the default values given to name and password are "none".
	 */
	public User() { 
		
		this.name="none";
		this.password="none";
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * @return the permission
	 */
	public int getPermission() {
		return permission;
	}
	/**
	 * @param permission the permission to set
	 */
	public void setPermission(int permission) {
		this.permission = permission;
	}
	
		
	
	
}
