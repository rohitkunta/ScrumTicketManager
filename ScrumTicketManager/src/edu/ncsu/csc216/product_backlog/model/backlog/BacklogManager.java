/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.backlog;

import java.util.ArrayList;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.io.ProductsReader;
import edu.ncsu.csc216.product_backlog.model.io.ProductsWriter;
import edu.ncsu.csc216.product_backlog.model.product.Product;
import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

/**
 * Manages Product Backlog(Singleton)
 * @author Rohit Kunta
 */
public class BacklogManager {
	/**Contains list of products*/
	private ArrayList<Product> productList;
	
	/**Single instance of Backlog manager*/	
	private static BacklogManager instance;
	
	/**Keeps track of current Product*/	
	private Product currentProduct;

	/**
	 * Private constructor for BacklogManager(Singleton)
	 * Called if getInstance() returns null
	 */
	private BacklogManager()
	{
		productList = new ArrayList<Product>();
		currentProduct = null;
	}
	
	/**
	 * Returns the single instance of BacklogManager
	 * - Creates BacklogManager object if singleton instance in null
	 * @return singletonInstance the single instance of Backlog Manager used in the program
	 */
	public static BacklogManager getInstance()
	{
		if (instance == null)
		{
			instance = new BacklogManager();
		}
		return instance;
	}
	
	/**
	 * Adds product to the product list and sets it to current Product with loadProduct()
	 * @param productName the name of the product being added
	 * @throws IllegalArgumentException if there is a duplicate name or name is null or empty string
	 */
	public void addProduct(String productName)
	{
		
		try
		{
			isDuplicateProduct(productName);
		} catch (IllegalArgumentException e)
		{
			throw new IllegalArgumentException("Invalid product name.");
		}
		
		
		Product product = new Product(productName);
		
		productList.add(product);
		
		loadProduct(productName);
	}
	
	/**
	 * Updates the currentProduct’s name to the given value.
	 * @param productName the name to be updated to
	 * @throws IllegalArgumentException if the currentProduct is null when attempting to edit
	 */
	public void editProduct(String productName)
	{
		if (currentProduct == null)
		{
			throw new IllegalArgumentException("No product selected.");
		}
		
		try
		{
			isDuplicateProduct(productName);
		} catch (IllegalArgumentException e)
		{
			throw new IllegalArgumentException("Invalid product name.");
		}
		
		currentProduct.setProductName(productName);
		
	}
	
	/**
	 * Deletes the current product
	 * - The currentProduct is updated to the product at index 0 in the list or null if there are no products left
	 * @throws IllegalArgumentException if currentProduct is null
	 */
	public void deleteProduct()
	{
		if (currentProduct == null)
		{
			throw new IllegalArgumentException("No product selected.");
		}
		
		
		
		for (int i = 0; i < productList.size(); i++)
		{
			if (this.currentProduct.getProductName() == productList.get(i).getProductName())
			{
				productList.remove(i);
			}
		}
		
		if (productList.size() != 0)
		{
			currentProduct = productList.get(0);
		}
		else if (productList.size() == 0)
		{
			currentProduct = null;
		}
	}
	
	/**
	 * Finds the Product with the given name in the list, make it the active or currentProduct.
	 * @param productName the product being made active.
	 * @throws IllegalArgumentException if Product is not in the list
	 */
	public void loadProduct(String productName)
	{
		boolean found = false;
		
		for (int i = 0; i < productList.size(); i++)
		{
			if (productName.equals(productList.get(i).getProductName()))
			{
				found = true;
				this.currentProduct = productList.get(i);
			}
		}
		
		if (!found)
		{
			throw new IllegalArgumentException("Product not available.");
		}
	}
	
	/**
	 * Gets the current product's name. If product is null, null is returned.
	 * @return productName the name of the current product
	 */
	public String getProductName()
	{
		if (currentProduct == null)
		{
			return null;
		}
		return currentProduct.getProductName();	
	}
	
	/**
	 * Returns a String array of product names in the order they are listed in the products list. 
	 * @return productList the list of products
	 */
	public String[] getProductList()
	{
		String productNames[] = new String[productList.size()];
		
		for (int i = 0; i < productList.size(); i++)
		{
			productNames[i] = productList.get(i).getProductName();		
		}
		
		return productNames;
	}
	
	/**
	 * Uses the ProductsReader to read the given fileName. 
	 * The returned list of Products are added to the end of the products list.
	 * - The first product in the list returned from ProductsReader is made the currentProduct.
	 * @param fileName the name of the fule being loaded from
	 */
	public void loadFromFile(String fileName) 
	{
		
		productList.addAll(ProductsReader.readProductsFile(fileName));
		currentProduct = productList.get(0);
		
	}
	
	/**
	 * Saves the Backlog to a file through ProductsWriter
	 * @param fileName the file being saved to
	 * @throws IllegalArgumentException If the currentProject is null or if there are no Tasks in the currentProject.
	 */
	public void saveToFile(String fileName)
	{
		if(currentProduct == null || currentProduct.getTasks().size() == 0)
		{
			throw new IllegalArgumentException("Unable to save file.");
		}
			
		ProductsWriter.writeProductsToFile(fileName, productList);

	}
	
	/**
	 * Sets singleton to null to reset manager
	 */
	protected void resetManager() {
		BacklogManager.instance = null;
	}
	
	/**
	 * Determines whether a product is a duplicate
	 * @param productName the product name being checked for duplicate
	 */
	private void isDuplicateProduct(String productName)
	{
		for (int i = 0; i < productList.size(); i++)
		{
			if (productName.equals(productList.get(i).getProductName()))
			{
				throw new IllegalArgumentException("Invalid product name.");
			}
		}
	}
	
	
	/**
	 * returns a 2D String array that is used to populate the TaskTableModel (and inner class of the ProductBacklogGUI.ProductPanel) with information. 
	 * The 2D String array stores [rows][columns]. The array should have 1 row for every Task that you need to return. There has 4 columns:
	 *	Index 0. Task’s id number
	 *	Index 1. Task’s state name
	 *	Index 2. Task’s type (using the long string)
	 *  Index 3. Task’s title
	 * @return taskArray the 2D array of tasks.
	 */
	public String[][] getTasksAsArray()
	{
		if (currentProduct == null)
		{
			return null;
		}
		
		String[][] taskArray = new String[currentProduct.getTasks().size()][4];
		
		for (int i = 0; i < currentProduct.getTasks().size(); i++)
		{
			taskArray[i][0] = Integer.toString(currentProduct.getTasks().get(i).getTaskId());
	        taskArray[i][1] = currentProduct.getTasks().get(i).getStateName();
	        taskArray[i][2] = currentProduct.getTasks().get(i).getTypeLongName();
	        taskArray[i][3] = currentProduct.getTasks().get(i).getTitle();
			
		}
		
		return taskArray;
	}
	
	/**
	 * Returns the task by ID
	 * @param id the id of the task being found
	 * @return t the task found by ID
	 */
	public Task getTaskById(int id)
	{
		for (int i = 0; i < productList.size(); i++)
		{
			for (int j = 0; j < productList.get(i).getTasks().size(); j++)
			{
				if (id == productList.get(i).getTasks().get(j).getTaskId())
				{
					return productList.get(i).getTasks().get(j);
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Will find the Task with the given id and update it by passing in the given Command.
	 * @param id the ID of the task in the list
	 * @param c the given command passed onto task
	 */
	public void executeCommand(int id, Command c)
	{
		if (!(currentProduct == null))
		{
			currentProduct.executeCommand(id, c);
		}
	}
	
	/**
	 * Deletes task by the ID
	 * @param id of the task being deleted
	 */
	public void deleteTaskById(int id)
	{
		if (!(currentProduct == null))
		{
			currentProduct.deleteTaskById(id);
		}
		for (int i = 0; i < productList.size(); i++)
		{
			productList.get(i).deleteTaskById(id);
		}
	}
	
	/**
	 * Creates a new task object and adds to task list of current product
	 * @param title the title of the task being created
	 * @param type the type of the task ''
	 * @param creator the creator of the task ''
	 * @param note the note of the task ''
	 */
	public void addTaskToProduct(String title, Type type, String creator, String note)
	{
		if (!(currentProduct == null))
		{
			currentProduct.addTask(title, type, creator, note);
		}
		
	}

	/**
	 * clears all products in Backlog
	 */
	public void clearProducts() {
		this.productList = new ArrayList<Product>();
		currentProduct = null;
	}

}
