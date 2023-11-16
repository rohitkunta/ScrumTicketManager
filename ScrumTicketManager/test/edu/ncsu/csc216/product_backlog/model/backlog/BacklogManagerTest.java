package edu.ncsu.csc216.product_backlog.model.backlog;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;
import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;


class BacklogManagerTest {
	
	/**Single instance of manager*/
	private BacklogManager backlog = BacklogManager.getInstance();

	/**
	 * Resets instance before every test to ensure no old data causes unexpected values
	 * @throws IOException if the file cannot be found and reset
	 */
	@BeforeEach
	void setUp() throws Exception {
		
		backlog.resetManager();
		
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_write_files.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "actualWriterResults.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
		
	}

	@Test
	void testGetInstance() {
		backlog.resetManager();
		BacklogManager.getInstance();
		
		assertEquals(0, backlog.getProductList().length);
		assertEquals(null, backlog.getProductName());
		
	}

	
	@Test
	void testAddProduct() {
		
		backlog.resetManager();
		BacklogManager.getInstance();
		
		backlog.addProduct("Samsung");
		assertEquals("Samsung", backlog.getProductName());
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> backlog.addProduct("Samsung"));
		assertEquals("Invalid product name.", e1.getMessage(), "Incorrect exception thrown with duplicate product - " + "Samsung");
		
		
	}

	@Test
	void testEditProduct() {
		BacklogManager.getInstance();
		
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> backlog.editProduct("Hello"));
		assertEquals("No product selected.", e1.getMessage(), "Incorrect exception thrown with null currentProduct - ");
		
		backlog.addProduct("Samsung");
		backlog.addProduct("Apple");
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> backlog.editProduct("Samsung"));
		assertEquals("Invalid product name.", e2.getMessage(), "Incorrect exception thrown with duplicate product - " + "Samsung");
		
		backlog.editProduct("iPhone");
		
		assertEquals("iPhone", backlog.getProductName(), "wrong product name, should be iPhone, got: " + backlog.getProductName());
		
	}

	@Test
	void testDeleteProduct() {
		BacklogManager.getInstance();
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> backlog.deleteProduct());
		assertEquals("No product selected.", e1.getMessage(), "Incorrect exception thrown with null currentProduct - ");
		
		backlog.addProduct("Samsung");
		backlog.addProduct("Apple.");
		
		backlog.deleteProduct();
		
		assertEquals(1, backlog.getProductList().length);
		
	}

	@Test
	void testLoadProduct() {
		BacklogManager.getInstance();
		
		backlog.addProduct("Samsung");
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> backlog.loadProduct("Apple"));
		assertEquals("Product not available.", e1.getMessage(), "Incorrect exception thrown when product not found- ");
		
	}
	
	@Test
	void testLoadFromFile() {
		BacklogManager.getInstance();
		
		backlog.loadFromFile("test-files/exp_task_backlog.txt");
		
		assertEquals(2, backlog.getProductList().length);
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> backlog.loadFromFile("boot"));
		assertEquals("Unable to load file.", e1.getMessage(), "Incorrect exception thrown when file not found- ");
		
	}

	@Test
	void testSaveToFile() {
		BacklogManager.getInstance();
		
		backlog.addProduct("Samsung");
		backlog.addProduct("Apple");
		backlog.addProduct("Nokia");
		
		
		backlog.addTaskToProduct("Mouse", Type.FEATURE, "Rohit", "gaming mouse");
		
		assertEquals(3, backlog.getProductList().length);
		
		backlog.saveToFile("test-files/actualWriterResults.txt");
		
		checkFiles("test-files/backlog_expected.txt", "test-files/actualWriterResults.txt");
		
		
		
	}


	@Test
	void testGetTasksAsArray() {
		BacklogManager.getInstance();
		
		backlog.addProduct("Samsung");
		
		backlog.addTaskToProduct("Mouse", Type.FEATURE, "Rohit", "Gaming Mouse");
		
		backlog.addTaskToProduct("Keyboard", Type.FEATURE, "Rohit", "Gaming Keyboard");
		
		String [][] taskArray = new String[][] {{"1", "Backlog", "Feature", "Mouse"},
			 									{"2", "Backlog", "Feature", "Keyboard"}};
		
		boolean equal = true;
		
		if (taskArray.length != backlog.getTasksAsArray().length || taskArray[0].length != backlog.getTasksAsArray()[0].length) {
            equal = false;
        }
		
		
		for (int i = 0; i < taskArray.length; i++) {
            for (int j = 0; j < taskArray[i].length; j++) {
                if (!taskArray[i][j].equals(backlog.getTasksAsArray()[i][j])) {
                    equal = false;
                }
            }
        }
		
		
		assertTrue(equal);										
	}

	@Test
	void testGetTaskByIdInvalid() {
		BacklogManager.getInstance();
		
		backlog.addProduct("Samsung");
		
		backlog.addTaskToProduct("Mouse", Type.FEATURE, "Rohit", "Gaming Mouse");
		
		assertNull(backlog.getTaskById(2));
		
	}

	@Test
	void testExecuteCommand() {
		BacklogManager.getInstance();
		
		backlog.addProduct("Samsung");
		
		backlog.addTaskToProduct("Mouse", Type.FEATURE, "Rohit", "Gaming Mouse");
		
		Task t = backlog.getTaskById(1);
		
		assertAll("Task", 
				() -> assertEquals(1, t.getTaskId(), "incorrect id"), 
				() -> assertEquals("Mouse", t.getTitle(), "incorrect title"),
				() -> assertEquals(Type.FEATURE, t.getType(), "incorrect type"),
				() -> assertEquals("Rohit", t.getCreator(), "incorrect creator"),
				() -> assertEquals("[Backlog] " + "Gaming Mouse", t.getNotes().get(0), "incorrect note"));	
		
		Command c = new Command(CommandValue.CLAIM, "Suchir", "Note for Command");
		
		backlog.executeCommand(1, c);
		
		Task t2 = backlog.getTaskById(1);
		
		assertAll("Task", 
				() -> assertEquals(1, t2.getTaskId(), "incorrect id"), 
				() -> assertEquals("Owned", t2.getStateName(), "incorrect Statename"), 
				() -> assertEquals("Mouse", t2.getTitle(), "incorrect title"),
				() -> assertEquals(Type.FEATURE, t2.getType(), "incorrect type"),
				() -> assertEquals("Suchir", t2.getOwner(), "incorrect Owner"),
				() -> assertEquals("Rohit", t2.getCreator(), "incorrect creator"),
				() -> assertEquals("- [Backlog] Gaming Mouse\r\n"
						+ "- [Owned] Note for Command\r\n", t2.getNotesList(), "incorrect note"));	
		
		
	}

	@Test
	void testDeleteTaskById() {
		BacklogManager.getInstance();
		
		backlog.addProduct("Samsung");
		
		backlog.addTaskToProduct("Mouse", Type.FEATURE, "Rohit", "Gaming Mouse");
		
		backlog.deleteTaskById(1);
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> backlog.saveToFile("test-files/actualWriterResults.txt"));
		assertEquals("Unable to save file.", e1.getMessage(), "Incorrect exception thrown with invalid number of tasks - " + 0);
	}

	@Test
	void testAddTaskToProduct() {
		BacklogManager.getInstance();
		
		backlog.addProduct("Samsung");
		
		backlog.addTaskToProduct("Mouse", Type.FEATURE, "Rohit", "Gaming Mouse");
		
		Task t = backlog.getTaskById(1);
		
		assertAll("Task", 
				() -> assertEquals(1, t.getTaskId(), "incorrect id"), 
				() -> assertEquals("Mouse", t.getTitle(), "incorrect title"),
				() -> assertEquals(Type.FEATURE, t.getType(), "incorrect type"),
				() -> assertEquals("Rohit", t.getCreator(), "incorrect creator"),
				() -> assertEquals("[Backlog] " + "Gaming Mouse", t.getNotes().get(0), "incorrect note"));	
	}

	@Test
	void testClearProducts() {
		BacklogManager.getInstance();
		
		backlog.addProduct("Samsung");
		
		backlog.clearProducts();
		
		assertEquals(0, backlog.getProductList().length);
		
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
