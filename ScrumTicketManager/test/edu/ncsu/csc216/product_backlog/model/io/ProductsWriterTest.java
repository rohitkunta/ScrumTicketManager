package edu.ncsu.csc216.product_backlog.model.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;
import edu.ncsu.csc216.product_backlog.model.product.Product;
import edu.ncsu.csc216.product_backlog.model.task.Task;

/**
 * Tests ProductsWriter
 */
class ProductsWriterTest {
	
	/**Stores the valid file to compare to*/
	private static final String VALID_FILE = "test-files/tasks1.txt";
	
	/**Stores the actual file to compare to*/
	private static final String ACTUAL_RESULTS = "test-files/actualWriterResults.txt";
	
	/**Stores the product1 name*/
	private static final String PRODUCT_NAME_1 = "Shopping Cart Simulation";
	
	/**Stores the product2 name*/
	private static final String PRODUCT_NAME_2 = "WolfScheduler";
	/**Stores the product2 name*/
	private static final ArrayList<String> NOTES_1 = new ArrayList<String>();
	/**Stores the product2 name*/
	private static final ArrayList<String> NOTES_2 = new ArrayList<String>();
	/**Stores the product2 name*/
	private static final ArrayList<String> NOTES_3 = new ArrayList<String>();
	/**Stores the product2 name*/
	private static final ArrayList<String> NOTES_4 = new ArrayList<String>();
	/**Stores the product2 name*/
	private static final ArrayList<String> NOTES_5 = new ArrayList<String>();
	/**Stores the product2 name*/
	private static final ArrayList<String> NOTES_6 = new ArrayList<String>();
	/**Stores the product2 name*/
	private static Task task1 = new Task(1, "Backlog", "Express Carts", "F", "jep", "sesmith5", "false", NOTES_1);
	/**Stores the product2 name*/
	private static Task task2 = new Task(2, "Backlog", "Regular Carts", "F", "jep", "sesmith5", "false", NOTES_2);
	/**Stores the product2 name*/
	private static Task task3 = new Task(3, "Backlog", "Java Swing", "KA", "sesmith5", "sesmith5", "false", NOTES_3);
	/**Stores the product2 name*/
	private static Task task10 = new Task(10, "Backlog", "Flatbed carts", "F", "jep", "unowned", "false", NOTES_4);
	/**Stores the product2 name*/
	private static Task task2p2 = new Task(2, "Backlog", "Weekly Repeat", "F", "sesmith5", "unowned", "false", NOTES_5);
	/**Stores the product2 name*/
	private static Task task6 = new Task(6, "Backlog", "Add Course", "F", "sesmith5", "jctetter", "true", NOTES_6);
	
	/**
	 * Resets course_records.txt for use in other tests.
	 */
	@Before
	public void setUp() throws Exception {
		
		//Reset course_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_write_files.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "actualWriterResults.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
		
		NOTES_1.clear();
		NOTES_2.clear();
		NOTES_3.clear();
		NOTES_4.clear();
		NOTES_5.clear();
		NOTES_6.clear();
		
		task1 = new Task(1, "Backlog", "Express Carts", "F", "jep", "sesmith5", "false", NOTES_1);
		
		task2 = new Task(2, "Backlog", "Regular Carts", "F", "jep", "sesmith5", "false", NOTES_2);
		
		task3 = new Task(3, "Backlog", "Java Swing", "KA", "sesmith5", "sesmith5", "false", NOTES_3);
		
		task10 = new Task(3, "Backlog", "Flatbed carts", "F", "jep", "unowned", "false", NOTES_4);
		
		task2p2 = new Task(2, "Backlog", "Weekly Repeat", "F", "sesmith5", "unowned", "false", NOTES_5);
		
		task6 = new Task(6, "Backlog", "Add Course", "F", "sesmith5", "jctetter", "true", NOTES_6);
	}	

	@Test
	void testWriteProductsToFile() {
		
		//Reset course_records.txt so that it's fine for other needed tests
				Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_write_files.txt");
				Path destinationPath = FileSystems.getDefault().getPath("test-files", "actualWriterResults.txt");
				try {
					Files.deleteIfExists(destinationPath);
					Files.copy(sourcePath, destinationPath);
				} catch (IOException e) {
					fail("Unable to reset files");
				}
				
				//task1
				task1.addNoteToList("Express carts always choose the shortest line. "
						+ "If there are multiple shortest lines, "
						+ "an express cart chooses the one with the smallest index.");
				
				//task2
				task2.addNoteToList("Regular carts always choose the shortest line excluding the express register line (at index 0). "
						+ "If there are multiple shortest lines, "
						+ "a regular cart chooses one with the smallest index.");
				Command c = new Command(CommandValue.CLAIM, "sesmith5", "Adding to sesmith5 backlog.");
				
				task2.update(c);
				
				//task3
				task3.addNoteToList("Learn more about Swing to debug GUI.");
				
				c = new Command(CommandValue.CLAIM, "sesmith5", "Adding to sesmith5 backlog.");
				task3.update(c);
				
				c = new Command(CommandValue.PROCESS, null, "Found Swing tutorials at http://docs.oracle.com/javase/tutorial/uiswing/start/.\r\n"
						+ "If there are multiple shortest special register lines, a special cart\r\n"
						+ "chooses one with the smallest index.");
				
				task3.update(c);
				
				
				//task10
				task10.addNoteToList("Add flatbed carts to simulation.");
				
				c = new Command(CommandValue.REJECT, null, "Rejected. Flatbed carts won't fit through physical register stations.");
				
				task10.update(c);
				
				
				//Product 1
				Product p1 = new Product(PRODUCT_NAME_1);
				
				p1.addTask(task1);
				p1.addTask(task2);
				p1.addTask(task3);
				p1.addTask(task10);
				
				
				
				//task2p2
				task2p2.addNoteToList("Events should have a weekly repeat of every 1, 2, 3, or 4 weeks.");
				
				c = new Command(CommandValue.REJECT, null, "Weekly repeat is unnecessary when creating ideal week.");
				
				task2p2.update(c);		
				
				//task6
				
				task6.addNoteToList("Users can add courses to their schedule.");
				
				c = new Command(CommandValue.CLAIM, "jctetter", "Assigning to jctetter.");
				
				task6.update(c);
				
				c = new Command(CommandValue.PROCESS, null, "Creating Course class.");
				
				task6.update(c);
				
				c = new Command(CommandValue.PROCESS, null, "Adding error checking on course name.");
				
				task6.update(c);

				c = new Command(CommandValue.PROCESS, null, "Adding tests for Course.");
				
				task6.update(c);
				
				c = new Command(CommandValue.VERIFY, null, "Request peer review.");
				
				task6.update(c);
				
				c = new Command(CommandValue.COMPLETE, null, "Updates meet requirements and test pass.");
				
				task6.update(c);
				
				Product p2 = new Product(PRODUCT_NAME_2);
				
				p2.addTask(task2p2);
				p2.addTask(task6);
				
				ArrayList<Product> products = new ArrayList<Product>();
				
				products.add(p1);
				products.add(p2);
				
				
				
				ProductsWriter.writeProductsToFile(ACTUAL_RESULTS, products);
				
			
				checkFiles(VALID_FILE, ACTUAL_RESULTS);
				
		
		
	}

	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
			 Scanner actScanner = new Scanner(new File(actFile));) {
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
