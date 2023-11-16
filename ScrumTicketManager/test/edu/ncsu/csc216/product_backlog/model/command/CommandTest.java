package edu.ncsu.csc216.product_backlog.model.command;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;

/**
 * Tests the Command Class
 */
class CommandTest {

	/**Sets the constant for commandValue*/
	public static final CommandValue VALID_C = CommandValue.BACKLOG;
	/**Sets the constant for owner*/
	public static final String VALID_OWNER = null;
	/**Sets the constant for Note*/
	public static final String VALID_NOTE = "HELLOWORLD";
	/**Stores error message for invalid command*/
	private static final String COMMAND_ERROR_MESSAGE = "Invalid command.";
	/**Sets constant for CLAIM commandValue*/
	public static final CommandValue VALID_CLAIM = CommandValue.CLAIM;
	

	/**
	 * Tests Command Constructor with valid parameters
	 */
	@Test
	void testCommandValid() {
		// Test a valid construction
		Command c = assertDoesNotThrow(
				() -> new Command(VALID_C, VALID_OWNER, VALID_NOTE),
				"Should not throw exception");
		
		assertAll("Command", 
				() -> assertEquals(VALID_C, c.getCommand(), "incorrect command"), 
				() -> assertEquals(VALID_OWNER, c.getOwner(), "incorrect owner"),
				() -> assertEquals(VALID_NOTE, c.getNoteText(), "incorrect Note"));
	}
	
	/**
	 * Tests Command Constructor with Invalid parameters
	 */
	@Test
	void testCommandInvalid() {
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Command(null, VALID_OWNER, VALID_NOTE));
		assertEquals(COMMAND_ERROR_MESSAGE, e1.getMessage(), "Incorrect exception thrown with invalid Command c - " + null);
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Command(VALID_C, VALID_OWNER, ""));
		assertEquals(COMMAND_ERROR_MESSAGE, e2.getMessage(), "Incorrect exception thrown with invalid noteText - " + null);
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Command(VALID_CLAIM, null, VALID_NOTE));
		assertEquals(COMMAND_ERROR_MESSAGE, e3.getMessage(), "Incorrect exception thrown with invalid Owner null - " + null);
		
		Exception e4 = assertThrows(IllegalArgumentException.class,
				() -> new Command(CommandValue.BACKLOG, "Rohit", VALID_NOTE));
		assertEquals(COMMAND_ERROR_MESSAGE, e4.getMessage(), "Incorrect exception thrown with invalid Owner Rohit - " + null);
		
		
	}


	/**
	 * Tests getNoteText()
	 */
	@Test
	void testGetNoteText() {
		Command c = new Command(VALID_C, VALID_OWNER, VALID_NOTE);
		
		assertEquals(VALID_NOTE, c.getNoteText(), "Incorrect note text, was instead " + c.getNoteText());
	}

	/**
	 * Tests getOwner()
	 */
	@Test
	void testGetOwner() {
		Command c = new Command(VALID_C, VALID_OWNER, VALID_NOTE);
		
		assertEquals(null, c.getOwner(), "Incorrect Owner, should be null. Instead was " + c.getOwner());
		
		Command c2 = new Command(VALID_CLAIM, "Rohit", VALID_NOTE);
		
		assertEquals("Rohit", c2.getOwner(), "Incorrect Owner, should be Rohit. Instead was " + c2.getOwner());
	}

	/**
	 * Tests getCommand()
	 */
	@Test
	void testGetCommand() {
		Command c2 = new Command(VALID_CLAIM, "Rohit", VALID_NOTE);
		
		assertEquals(CommandValue.CLAIM, c2.getCommand(), "Incorrect Owner, should be CLAIM. Instead was " + c2.getCommand());
		
	}

}
