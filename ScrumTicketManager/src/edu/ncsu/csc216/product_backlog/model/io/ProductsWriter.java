/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import edu.ncsu.csc216.product_backlog.model.product.Product;

/**
 * Writes Product Objects from Product ArrayList
 * @author Rohit Kunta
 */
public class ProductsWriter {
	
	/**
	 * Writes a list of products down to a file
	 * @param fileName the name of the file being written to.
	 * @param list the list of Products to write from
	 * @throws IllegalArgumentException if any errors or exceptions.
	 */
	public static void writeProductsToFile(String fileName, ArrayList<Product> list)
	{
		
		try
		{
			PrintStream fileWriter = new PrintStream(new File(fileName));

			writeProducts(list, fileWriter);
			
		} catch(IllegalArgumentException | FileNotFoundException e)
		{
			throw new IllegalArgumentException("Unable to save file.");
		}
		
		
	}
	
	/**
	 * Writes individual products from list
	 * @param list the list of products
	 * @param fileWriters the name of the file to write to
	 */
	private static void writeProducts(ArrayList<Product> list, PrintStream fileWriters)
	{
		PrintStream fileWriter = fileWriters;
		
		for (int i = 0; i < list.size(); i++) {
			fileWriter.printf("# %s\r\n", list.get(i).getProductName());
			
			for (int j = 0; j < list.get(i).getTasks().size(); j++)
			{
				fileWriter.print(list.get(i).getTasks().get(j).toString());
			}
		}

		fileWriter.close();
	}

}
