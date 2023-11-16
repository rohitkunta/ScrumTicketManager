/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.command;

/**
 * Encapsulates user commands that cause transitions to state of a Task to update
 * @author Rohit Kunta
 */
public class Command {
	/**Stores the note of the user's command*/
	private String note;
	/**Stores the owner's name of the command*/
	private String owner;
	/**Stores error message for invalid command*/
	private static final String COMMAND_ERROR_MESSAGE = "Invalid command.";
	/**
	 * Contains possible user actions that cause transitions
	 * @author Rohit Kunta
	 */
	public enum CommandValue { BACKLOG, CLAIM, PROCESS, VERIFY, COMPLETE, REJECT }
	/**Stores the command value*/
	private CommandValue c;
	
	/**
	 * Constructor for Command class
	 * @param c the commandValue of the command
	 * - throws IllegalArgumentException(); if null
	 * @param owner the owner of the command if its CommandValue is claim
	 * - throws IllegalArgumentException(); if null when CommandValue is claim, or when it is not null when CommandValue is not null.
	 * @param noteText the note for the command
	 * - throws IllegalArgumentException(); if null or empty string
	 */
	public Command(CommandValue c, String owner, String noteText)
	{
		if (c == null)
		{
			throw new IllegalArgumentException(COMMAND_ERROR_MESSAGE);
		}
		
		if (noteText == null || noteText.length() == 0)
		{
			throw new IllegalArgumentException(COMMAND_ERROR_MESSAGE);
		}
		
		if ((owner == null || owner.length() == 0 || "unowned".equals(owner)) && c == Command.CommandValue.CLAIM)
		{
			throw new IllegalArgumentException(COMMAND_ERROR_MESSAGE);
		}
		if (owner != null && c != Command.CommandValue.CLAIM)
		{
			throw new IllegalArgumentException(COMMAND_ERROR_MESSAGE);
		}
		
		
		this.c = c;
		this.owner = owner;
		this.note = noteText;
	}

	/**
	 * Returns the note associated with the command
	 * @return the note the note associated with the command
	 */
	public String getNoteText() {
		return note;
	}

	/**
	 * Returns the owner associated with the claim command
	 * @return the owner the owner of the claim command
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Returns the commanValue of the command
	 * @return the c the commandValue of the command;
	 */
	public CommandValue getCommand() {
		return c;
	}
	
	

}
