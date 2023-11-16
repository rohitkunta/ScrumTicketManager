package edu.ncsu.csc216.product_backlog.model.io;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.product.Product;

class ProductsReaderTest {
	/**Sets valid file name*/
	private static final String VALID_FILE = "test-files/exp_task_backlog.txt";
	
	/**Sets invalid file name for invalid owner for backlog state*/
	private static final String INVALID_FILE = "test-files/tasks16.txt";
	
	/**Sets invalid file name for invalid owner for owned state*/
	private static final String INVALID_FILE2 = "test-files/tasks17.txt";
	
	/**Sets invalid file name for invalid owner for Processing state*/
	private static final String INVALID_FILE3 = "test-files/tasks18.txt";
	
	/**Sets invalid file name for invalid owner for Verifying state*/
	private static final String INVALID_FILE4 = "test-files/tasks19.txt";
	
	/**Sets invalid file name for invalid owner for Done state*/
	private static final String INVALID_FILE5 = "test-files/tasks20.txt";
	
	/**Sets invalid file name for Bug in done state without verification*/
	private static final String INVALID_FILE6 = "test-files/tasks21.txt";
	

	/**
	 * Tests readProductsFile
	 */
	@Test
	void testReadProductsFile() {
		ArrayList<Product> products = ProductsReader.readProductsFile(VALID_FILE);
		assertEquals(2, products.size());
		assertEquals(3, products.get(0).getTasks().size());
		
		
		ArrayList<Product> productsList = ProductsReader.readProductsFile(INVALID_FILE);
		assertEquals(0, productsList.size());
		
		productsList = ProductsReader.readProductsFile(INVALID_FILE2);
		assertEquals(0, productsList.size());
		
		productsList = ProductsReader.readProductsFile(INVALID_FILE3);
		assertEquals(0, productsList.size());
		
		productsList = ProductsReader.readProductsFile(INVALID_FILE4);
		assertEquals(0, productsList.size());

		productsList = ProductsReader.readProductsFile(INVALID_FILE5);
		assertEquals(0, productsList.size());
		
		productsList = ProductsReader.readProductsFile(INVALID_FILE6);
		assertEquals(0, productsList.size());
	}

}
