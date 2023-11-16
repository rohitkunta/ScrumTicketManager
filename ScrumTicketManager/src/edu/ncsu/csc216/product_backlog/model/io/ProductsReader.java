/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.ncsu.csc216.product_backlog.model.product.Product;
import edu.ncsu.csc216.product_backlog.model.task.Task;

/**
 * Reads Product from a stringfileReader.hasNextLine()
 * @author Rohit Kunta
 */
public class ProductsReader {
	
	/**
	 * Returns an array of Products containing their tasks lists
	 * - Any invalid products or tasks are ignored
	 * @param fileName the name of the file being read from
	 * @return productsList the ArrayList of products.
	 * @throws IllegalArgumentException if file cannot be loaded
	 */
	public static ArrayList<Product> readProductsFile(String fileName)
	{
		
		try {
			String file = "";
			String line = "";
			
			Scanner fileReader = new Scanner(new FileInputStream(fileName)); //Create a file scanner to read the file
			
			//Adds \n delimiter to end of each line in file.
			while (fileReader.hasNextLine())
			{
				line = fileReader.nextLine() + "\n";
				file += line;
			}
			
			
			ArrayList<Product> products = new ArrayList<Product>(); //Create an empty array of product objects
			
			if (!file.startsWith("#"))
			{
				return products;
			}
			
			Scanner scanner = new Scanner(file);
			
			scanner.useDelimiter("\\r?\\n?[#]");
			
			while (scanner.hasNext())
			{
				try {
					
					String productString = scanner.next();
					System.out.println("productString: " + productString);
					Product product = processProduct(productString); // passes one product
					products.add(product);
					
				} catch (IllegalArgumentException e){
					
					//We leave this empty
				}
			}
			
		    //Close the Scanner b/c we're responsible with our file handles
		    fileReader.close();
		    scanner.close();
		    //Return the ArrayList with all the products we read!
		    for (int i = 0; i < products.size(); i++)
		    {
		    	System.out.println("Product name: " + products.get(i).getProductName());
		    }
		    return products;
			
		} catch (FileNotFoundException | IllegalArgumentException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		
	}

	/**
	 * Helper method: Reads the product from a file	
	 * Only passes 1 entire product string
	 * @param oneProduct the line being read for a product
	 * @return product the product with its respective tasks and notes
	 * @throws IllegalArgumentException if there is a problem creating product or task.
	 */
	private static Product processProduct(String oneProduct) throws IllegalArgumentException{
		try
		{
			
			
			Product product;
			//creates abstraction within product
			Scanner scannerP = new Scanner(oneProduct);
			
			// creates entire [product string] will; product, tasks and notes
			String productString = oneProduct;
			
			//gets name of Product
			Scanner scannerName = new Scanner(productString);
			scannerName.useDelimiter("\n");
			String productName = scannerName.next();
			
			//creates Product object
			product = new Product(productName.trim());
			
			Scanner placeholder = new Scanner(productString);
			
			//creates abstraction within task
			Scanner scannerT = new Scanner(productString); // takes one product String
			scannerT.useDelimiter("\\n");
			scannerT.next();
			
			scannerT.useDelimiter("\\r?\\n?[*]"); // divides into tasks with notes
			
			//creates task object and notes list
			while (scannerT.hasNext()) 
			{
				
				Task taskAdded = processTask(scannerT.next());
				
				try
				{
					product.addTask(taskAdded);
				} catch (IllegalArgumentException e)
				{
					//ignore don't add task
				}
				
			}
			
			scannerP.close();
			scannerT.close();
			scannerName.close();
			placeholder.close();
			
			
			return product;
			
		} catch (IllegalArgumentException e) // caught exception
		{
			throw new IllegalArgumentException("Invalid product");
		}
			
		
	}
	
	/**
	 * Helper Method: Processes a task from a single line
	 * @param taskString the string with the task and notes
	 * @return taskAdded the task to be added to Product
	 * @throws IllegalArgumentException if there is an incorrect type-owner pair, or other error
	 */
	private static Task processTask(String taskString) throws IllegalArgumentException
	{
		Scanner newLine = new Scanner(taskString);
		newLine.useDelimiter("\n");
		
		if(!newLine.hasNext()) {
			newLine.close();
			throw new IllegalArgumentException("There is no note.");
		}
		
		String justTask = newLine.next();
		
		if(!newLine.hasNext()) {
			newLine.close();
			throw new IllegalArgumentException("There is no note.");
		}
		
		Scanner taskDelim = new Scanner(justTask);
		taskDelim.useDelimiter(","); // divides one task 
		
		ArrayList<String> paramaters = new ArrayList<String>();
		
		int id;
		String state, title, type, creator, owner, verified;
		
		ArrayList<String> notesList = new ArrayList<String>();
		
		try
		{
			
			//read in tokens for task fields
			while (taskDelim.hasNext())
			{
				String parameter = taskDelim.next();
				
				paramaters.add(parameter);	
			}
			
			if (paramaters.size() != 7)
			{
				newLine.close();
				taskDelim.close();
				throw new IllegalArgumentException();
			}
			
			//Assigns local variables to respective parameters, All variables except notes list are good for one task
			id = Integer.parseInt(paramaters.get(0).trim());
			state = paramaters.get(1).trim();
			title = paramaters.get(2).trim();
			type = paramaters.get(3).trim();
			creator = paramaters.get(4).trim();
			owner = paramaters.get(5).trim();
			verified = paramaters.get(6).trim();
			
			
			 // Checks if there is an invalid owner for the associated states
			 
			if(("Backlog".equals(state) || "Rejected".equals(state)) && !"unowned".equals(owner))
			{
				taskDelim.close();
				newLine.close();
				throw new IllegalArgumentException("Invalid owner for task");
			}
			
			if(("Owned".equals(state) || "Processing".equals(state) || "Verifying".equals(state) || "Done".equals(state)) && "unowned".equals(owner))
			{
				taskDelim.close();
				newLine.close();
				throw new IllegalArgumentException("Invalid owner for task");
			}
			
			
			//checks if a Feature, Bug, Technical Work, are done and not verified
			if("Done".equals(state) && ("F".equals(type) || "B".equals(type) || "TW".equals(type)) && "false".equals(verified))
			{
				taskDelim.close();
				newLine.close();
				throw new IllegalArgumentException("Invalid verification for task");
			}
			
			
			//Checks if KA is not state is not verified
			if("Done".equals(state) && "KA".equals(type) && "true".equals(verified))
			{
				taskDelim.close();
				newLine.close();
				throw new IllegalArgumentException("Invalid verification for task");
			}
			
			//Changes delimiter of newline to read after justTask String and until next Task
			newLine.useDelimiter("\\r?\\n?[-]");
			
			//adds notes to task
			while (newLine.hasNext())
			{
				String noteString = newLine.next().trim();
				notesList.add(noteString);
				
			}
			
			Task taskAdded = new Task(id, state, title, type, creator, owner, verified, notesList);
			
			
			taskDelim.close();
			newLine.close();
			
			return taskAdded;
		} catch (IndexOutOfBoundsException | IllegalArgumentException e)
		{
			throw new IllegalArgumentException("Invalid task information.");
		}
		
		
	}


}
