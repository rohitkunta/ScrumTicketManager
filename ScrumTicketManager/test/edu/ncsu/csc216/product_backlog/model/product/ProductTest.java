package edu.ncsu.csc216.product_backlog.model.product;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;
import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;
/**
 * Tests Product Class
 */
class ProductTest {
	
	/**Sets the name for the product*/
	private static final String NAME = "IPHONE";
	
	/**Sets the constant for taskId*/
	private static final int TASK_ID = 3;
	/**Sets the constant for title*/
	private static final String TITLE = "Express Rohit";
	/**Sets the constant for creator*/
	private static final String CREATOR = "Rohit";
	/**Sets the constant for owner*/
	private static final String OWNER = "Suchir";
	/**Sets the constant for type*/
	private static final Type TYPE = Type.FEATURE;
	/**Sets the constant for note*/
	private static final String NOTE = "THIS IS TASK NOTE";
	
	/**Sets the note text for transitions state updates*/
	private static final String NOTE_UPDATE = "Needed to fix something";
	

	@Test
	void testProductValid() {
	// Test a valid construction
	Product p = assertDoesNotThrow(
			() -> new Product(NAME),
			"Should not throw exception");
	
	assertAll("Product", 
			() -> assertEquals(NAME, p.getProductName(), "incorrect name"), 
			() -> assertEquals(1, p.getTasks().size() + 1, "incorrect Number of tasks in Array"));
	}
	
	@Test
	void testProductInvalid() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Product(""));
		assertEquals("Invalid product name.", e1.getMessage(), "Incorrect exception thrown with invalid product name - " + "");
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Product(null));
		assertEquals("Invalid product name.", e2.getMessage(), "Incorrect exception thrown with invalid product name - " + null);
	}

	@Test
	void testGetProductName() {
		Product p = new Product(NAME);
		
		assertEquals(NAME, p.getProductName());
		
	}

	@Test
	void testSetProductName() {
		Product p = new Product(NAME);
		p.setProductName("15PROMAX");
		
		assertEquals("15PROMAX", p.getProductName());
	}

	@Test
	void testAddTask() {
		Product p = new Product(NAME);
		
		Task t = new Task(TASK_ID, TITLE, TYPE, CREATOR, NOTE);
		
		p.addTask(t);
		
		Task t2 = new Task(4, "OPPO", TYPE, CREATOR, NOTE);
		
		p.addTask(t2);
		
		
		assertEquals(2, p.getTasks().size(), "Incorrect number of tasks");
		assertEquals(0, p.getTasks().indexOf(t));
		assertEquals(1, p.getTasks().indexOf(t2));
		
	}

	@Test
	void testAddTaskStringTypeString() {
		
		Product p = new Product(NAME);
		
		p.addTask(TITLE, TYPE, CREATOR, NOTE);
		p.addTask("OPPO", Type.BUG, "Advaith", "Bye World");
		
		assertEquals(2, p.getTasks().size(), "Incorrect number of tasks");
		
		assertEquals("Express Rohit", p.getTaskById(1).getTitle(), "Incorrect name for task 1");
		
		assertEquals("OPPO", p.getTaskById(2).getTitle(), "Incorrect name for task 2");
		
	}

	@Test
	void testDeleteTaskById() {
		
		Product p = new Product(NAME);
		
		Task t = new Task(TASK_ID, TITLE, TYPE, CREATOR, NOTE);
		
		p.addTask(t);
		
		Task t2 = new Task(4, "OPPO", TYPE, CREATOR, NOTE);
		
		p.addTask(t2);
		
		p.deleteTaskById(TASK_ID);
		
		assertEquals(1, p.getTasks().size());
		assertEquals("OPPO", p.getTasks().get(0).getTitle());
	}

	@Test
	void testExecuteCommand() {
		Product p = new Product(NAME);
		
		Task t = new Task(TASK_ID, TITLE, TYPE, CREATOR, NOTE);
		
		p.addTask(t);
		
		Task t2 = new Task(4, "OPPO", TYPE, CREATOR, NOTE);
		
		p.addTask(t2);
		
		Command c = new Command(CommandValue.CLAIM, OWNER, NOTE_UPDATE);
		
		p.executeCommand(TASK_ID, c);
		
		assertEquals(OWNER, p.getTaskById(TASK_ID).getOwner());
		assertEquals("Owned", p.getTaskById(TASK_ID).getStateName());
		
	}

}
