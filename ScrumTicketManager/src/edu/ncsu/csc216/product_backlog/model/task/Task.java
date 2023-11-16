/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.task;

import java.util.ArrayList;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;

/**
 * The Task object the developers can add to Backlog
 * - includes an enumeration for all types of class
 * @author Rohit Kunta
 */
public class Task 
{
	
	/**Stores constant for Backlog State*/
	public static final String BACKLOG_NAME = "Backlog";
	/**Stores constant for Owned State*/
	public static final String OWNED_NAME = "Owned";
	/**Stores constant for Processing State*/
	public static final String PROCESSING_NAME = "Processing";
	/**Stores constant for Verifying State*/
	public static final String VERIFYING_NAME = "Verifying";
	/**Stores constant for Done State*/
	public static final String DONE_NAME = "Done";
	/**Stores constant for Rejected State*/
	public static final String REJECTED_NAME = "Rejected";
	
	/**Stores constant for Feature long name*/
	public static final String FEATURE_NAME = "Feature";
	/**Stores constant for Bug long name*/
	public static final String BUG_NAME = "Bug";
	/**Stores constant for Technical Work long name*/
	public static final String TECHNICAL_WORK_NAME = "Technical Work";
	/**Stores constant for Knowledge Acquisition long name*/
	public static final String KNOWLEDGE_ACQUISITION_NAME = "Knowledge Acquisition";
	
	/**Stores constant for Feature Short name*/
	public static final String T_FEATURE = "F";
	/**Stores constant for Bug Short name*/
	public static final String T_BUG = "B";
	/**Stores constant for Technical Work Short name*/
	public static final String T_TECHNICAL_WORK = "TW";
	/**Stores constant for Knowledge Acquisition Short name*/
	public static final String T_KNOWLEDGE_ACQUISITION = "KA";
	
	/**Stores constant Task that is not owned*/
	public static final String UNOWNED = "unowned";
	

	/**Maintains instance of backlog state*/
	private final TaskState backlogState = new BacklogState();
	/**Maintains instance of owned state*/
	private final TaskState ownedState = new OwnedState();
	/**Maintains instance of processing state*/
	private final TaskState processingState = new ProcessingState();
	/**Maintains instance of verifying state*/
	private final TaskState verifyingState = new VerifyingState();
	/**Maintains instance of done state*/
	private final TaskState doneState = new DoneState();
	/**Maintains instance of rejected state*/
	private final TaskState rejectedState = new RejectedState();
	
	/**
	 * Enumerates the types of Tasks
	 * @author Rohit Kunta
	 */
	public enum Type { FEATURE, BUG, TECHNICAL_WORK, KNOWLEDGE_ACQUISITION }
	
	/**Stores taskId of Task*/
	private int taskId;
	/**Stores title of Task*/
	private String title;
	/**Stores creator of Task*/
	private String creator;
	/**Stores owner of Task*/
	private String owner;
	/**Stores whether task has been verified or not*/
	private boolean isVerified;
	/**Stores type of task*/
	private Type type;
	/**Stores list of notes of Task*/
	private ArrayList<String> notes;
	/**Stores current State*/
	private TaskState currentState = new BacklogState(); 
	
	/**
	 * Arg constructor for Task.
	 * Initializes taskId, title, creator, type, note, and owner.
	 * - If any of the parameters are null or empty strings, then an IllegalArgumentException is thrown. 
	 * - If the id is zero or less, an IllegalArgumentException is thrown. 
	 * - A new Task starts in the backlogState. 
	 * - The owner is set to UNOWNED. 
	 * - The note is added to the newly constructed notes list.
	 * @param id the taskId
	 * @param title the title of the task
	 * @param type the type of the task
	 * @param creator the creator of the task
	 * @param note the note being added to the task's notes list
	 * @throws IllegalArgumentException if
	 * 							- any of the parameters are null or empty strings
	 * 							- id is zero or less
	 */
	public Task(int id, String title, Type type, String creator, String note)
	{
		
		setTaskId(id);
		setTitle(title);
		setType(type);
		setCreator(creator);
		notes = new ArrayList<String>();
		addNoteToList(note);
		setOwner(UNOWNED);
		
	}
	
	/**
	 * Full argument constructor of the Task Class
	 * @param id the id of the task
	 * @param state state of the task
	 * @param title the title of the task
	 * @param type the type of the task
	 * @param creator the creator of the task
	 * @param owner the owner of the task
	 * @param verified whether the task is verified or not
	 * @param notes the list of notes for the task
	 */
	public Task(int id, String state, String title, String type, String creator, String owner, String verified, ArrayList<String> notes)
	{
		
		setTaskId(id);
		setTitle(title);
		setCreator(creator);
		setOwner(owner);
		setVerified(verified);
		setNotes(notes);
		setTypeFromString(type);
		setState(state);
		
	}

	/**
	 * Returns the Task's ID
	 * @return taskId the taskId of the task
	 */
	public int getTaskId() {
		return taskId;
	}

	/**
	 * Sets the task's ID
	 * @param taskId the taskId to set the task's ID to
	 * @throws IllegalArgumentException if the taskId is less than 0.
	 */
	private void setTaskId(int taskId) {
		if(taskId < 0)
		{
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.taskId = taskId;
	}

	/**
	 * Returns the Tasks's title
	 * @return title the title of the task
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the task
	 * @param title the title to set the task's title to
	 * @throws IllegalArgumentException if  is empty title or is null
	 */
	private void setTitle(String title) {
		if(title == null || title.length() == 0)
		{
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.title = title;
	}

	/**
	 * Returns the creator of the task
	 * @return creator the creator the creator of the task
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * Sets the creator of the task
	 * @param creator the creator to set the creator field to
	 * @throws IllegalArgumentException if the creator is empty or is null
	 */
	private void setCreator(String creator) {
		
		if(creator == null || creator.length() == 0)
		{
			throw new IllegalArgumentException("Invalid task information.");
		}
		
		this.creator = creator;
	}

	/**
	 * Returns the owner of the task
	 * @return owner the owner of the task
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Sets the owner of the task
	 * @param owner the owner to set the owner of the task to
	 * @throws IllegalArgumentException if the owner is empty or is null
	 */
	private void setOwner(String owner) {
		if(owner == null || owner.length() == 0)
		{
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.owner = owner;
	}

	/**
	 * Returns true of false depending on what isVerified is set to.
	 * @return isVerified the boolean being returned
	 */
	public boolean isVerified() {
		return isVerified;
	}

	/**
	 * Sets whether the task has been verified or not from a string
	 * @param verified string that is used to determine whether to set isVerified to true or false
	 * @throws IllegalArgumentException if the parameter is empty or is null
	 */
	private void setVerified(String verified) {
		if(verified == null || verified.length() == 0)
		{
			throw new IllegalArgumentException("Invalid task information.");
		}
		
		if ("true".equals(verified))
		{
			isVerified = true;
		}
		else if ("false".equals(verified))
		{
			isVerified = false;
		}
		else
		{
			throw new IllegalArgumentException("Invalid task information.");
		}
	}

	/**
	 * Returns the type field
	 * @return type the type object being returned from Task's state
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Sets the type field
	 * @param type the Type parameter that is going to set the type field
	 */
	private void setType(Type type) {
		if (type == null)
		{
			throw new IllegalArgumentException("Invalid task information.");
		}
		
		this.type = type;
	}
	
	/**
	 * Sets the type field from String
	 * @param type the Type parameter that is going to set the type field
	 * @throws IllegalArgumentException if the string doesn't match any task Type.
	 */
	private void setTypeFromString(String type) {
		
		if(type == null || type.length() == 0)
		{
			throw new IllegalArgumentException("Invalid task information.");
		}
		
		
		
		if (type.equals(T_FEATURE))
		{
			this.type = Type.FEATURE;
		}
		else if (type.equals(T_BUG))
		{
			this.type = Type.BUG;
		}
		else if (type.equals(T_TECHNICAL_WORK))
		{
			this.type = Type.TECHNICAL_WORK;
		}
		else if (type.equals(T_KNOWLEDGE_ACQUISITION))
		{
			this.type = Type.KNOWLEDGE_ACQUISITION;
		}
		else
		{
			throw new IllegalArgumentException("Invalid task information.");
		}
		
		
		
	}

	/**
	 * Returns the notes ArrayList
	 * @return the notes the array list of notes 
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}
	
	/**
	 * Returns String array of notes
	 * @return array of notes
	 */
	public String[] getNotesArray()
	{
		String[] notesList = new String[notes.size()];
		
		int i = 0;
		
		for (String note : notes)
		{
			notesList[i] = note;
			i++;
		}
		
		return notesList;
	}

	/**
	 * Sets the Notes ArrayList 
	 * @param notes the notes array list to set notes to.
	 */
	private void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}

	/**
	 * Sets the currentState from a String
	 * @param state the state to set currentState to
	 */
	private void setState(String state) {
		
		if(state == null || state.length() == 0)
		{
			throw new IllegalArgumentException("Invalid task information.");
		}
			
		if (state.equals(BACKLOG_NAME))
		{
			this.currentState = new BacklogState();
			setOwner("unowned");
		}
		else if (state.equals(OWNED_NAME))
		{
			this.currentState = new OwnedState();
			
		}
		else if (state.equals(PROCESSING_NAME))
		{
			this.currentState = new ProcessingState();
		}
		else if (state.equals(VERIFYING_NAME))
		{
			this.currentState = new VerifyingState();
		}
		else if (state.equals(DONE_NAME))
		{
			this.currentState = new DoneState();
		}
		else if (state.equals(REJECTED_NAME))
		{
			this.currentState = new RejectedState();
		}
		else
		{
			throw new IllegalArgumentException("Invalid task information.");
		}
		
	}

	/**
	 * Adds notes to the list and appends stateName to the front of the note
	 * @param note the note being added to the list
	 * @throws IllegalArgumentException if note is empty or null
	 */
	public void addNoteToList(String note) {
		if (note == null || note.length() == 0)
		{
			throw new IllegalArgumentException("Invalid task information.");
		}
		
		if (!"Rejected".equals(getStateName()))
		{
			String noteAdded;
			
			noteAdded = String.format("[%s] " + note, currentState.getStateName());
			
			this.notes.add(noteAdded);
		}
		else
		{
			String noteAdded;
			
			noteAdded = "[Rejected] " + note;
			
			this.notes.add(noteAdded);
		}
		
	}
	/**
	 * Returns the name of the current state
	 * @return stateName the name of the current state
	 */
	public String getStateName()
	{
		return currentState.getStateName();
	}	
	
	/**
	 * Returns short name of the type of the task
	 * @return shortName the short name of the state
	 */
	public String getTypeShortName()
	{
		switch (this.type)
		{
			case FEATURE:
				return T_FEATURE;
			case BUG:
				return T_BUG;
			case TECHNICAL_WORK:
				return T_TECHNICAL_WORK;
			case KNOWLEDGE_ACQUISITION:
				return T_KNOWLEDGE_ACQUISITION;
			default:
				throw new IllegalArgumentException("Invalid task information.");
		}
	}
	
	/**
	 * Returns long name of the type of the task
	 * @return longName the long name of the state
	 */
	public String getTypeLongName()
	{
		switch (this.type)
		{
			case FEATURE:
				return FEATURE_NAME;
			case BUG:
				return BUG_NAME;
			case TECHNICAL_WORK:
				return TECHNICAL_WORK_NAME;
			case KNOWLEDGE_ACQUISITION:
				return KNOWLEDGE_ACQUISITION_NAME;
			default:
				throw new IllegalArgumentException("Invalid task information.");
		}
	}
	
	/**
	 * Returns the list of notes as a string
	 * Each note is on a new line
	 * @return notesList the list of notes as a string
	 */
	public String getNotesList()
	{
		String notesList = "";
		
		for (int i = 0; i < notes.size(); i++)
		{
			notesList +=  "- " + notes.get(i) + "\r\n";
		}
		
		return notesList;
	}


	/**
	 * Returns the string representation of the Task used in 
	 */
	@Override
	public String toString() {
		String toString = "";
		
		toString += String.format("* %d,%s,%s,%s,%s,%s,%b\r\n", getTaskId(), currentState.getStateName(), getTitle(), getTypeShortName(), getCreator(), getOwner(), isVerified());
		
		toString += getNotesList();
		
		return toString;
	}
	
	
	/**
	 * Interface for states in the Task State Pattern.  All 
	 * concrete task states must implement the TaskState interface.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu) 
	 */
	private interface TaskState {
		
		/**
		 * Update the Task based on the given Command
		 * An UnsupportedOperationException is thrown if the Command is not a
		 * is not a valid action for the given state.  
		 * @param c Command describing the action that will update the Task
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		void updateState(Command c);
		
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		String getStateName();
	
	}
	
	/**
	 * Inner class Backlog State for Task
	 */
	private class BacklogState implements TaskState
	{

		@Override
		public void updateState(Command c) {
			
		if (c.getCommand() == CommandValue.BACKLOG)
		{
			throw new UnsupportedOperationException("Invalid transition.");
		}
		else if (c.getCommand() == CommandValue.CLAIM)
		{
			currentState = ownedState;
			if (UNOWNED.equals(c.getOwner()))
			{
				throw new UnsupportedOperationException("Invalid transition.");
			}
			setOwner(c.getOwner());
			addNoteToList(c.getNoteText());
		}
		else if (c.getCommand() == CommandValue.REJECT)
		{
			currentState = rejectedState;
			addNoteToList(c.getNoteText());
			setOwner("unowned");
		}
		else
		{
			throw new UnsupportedOperationException("Invalid transition.");
		}
			
		}

		@Override
		public String getStateName() {
			return BACKLOG_NAME;
		}
	}
	
	/**
	 * Inner class Owned State for Task
	 */
	private class OwnedState implements TaskState
	{

		@Override
		public void updateState(Command c) {
			
			if (c.getCommand() == CommandValue.BACKLOG)
			{
				currentState = backlogState;
				addNoteToList(c.getNoteText());
				setOwner("unowned");
			}
			else if (c.getCommand() == CommandValue.PROCESS)
			{
				currentState = processingState;
				addNoteToList(c.getNoteText());
			}
			else if (c.getCommand() == CommandValue.REJECT)
			{
				currentState = rejectedState;
				addNoteToList(c.getNoteText());
				setOwner("unowned");
			}
			else
			{
				throw new UnsupportedOperationException("Invalid transition.");
			}
				
			
			
			
		}

		@Override
		public String getStateName() {
			return OWNED_NAME;
		}
	}
	
	/**
	 * Inner class Processing State for Task
	 */
	private class ProcessingState implements TaskState
	{

		@Override
		public void updateState(Command c) {
			
			if (c.getCommand() == CommandValue.BACKLOG)
			{
				currentState = backlogState;
				addNoteToList(c.getNoteText());
				setOwner("unowned");
			}
			else if (c.getCommand() == CommandValue.PROCESS)
			{
				currentState = processingState;
				addNoteToList(c.getNoteText());
			}
			else if (c.getCommand() == CommandValue.VERIFY)
			{
				if (type == Type.KNOWLEDGE_ACQUISITION)
				{
					throw new UnsupportedOperationException("Invalid transition.");
				}
				
				currentState = verifyingState;
				addNoteToList(c.getNoteText());
			}
			else if (c.getCommand() == CommandValue.COMPLETE)
			{
				if (Type.KNOWLEDGE_ACQUISITION == getType())
				{
					currentState = doneState;
					addNoteToList(c.getNoteText());
				}
				else
				{
					throw new UnsupportedOperationException("Invalid transition.");
				}
			}
			else
			{
				throw new UnsupportedOperationException("Invalid transition.");
			}
			
		}

		@Override
		public String getStateName() {
			return PROCESSING_NAME;
		}
	}
	
	/**
	 * Inner class Verifying State for Task
	 */
	private class VerifyingState implements TaskState
	{

		@Override
		public void updateState(Command c) {
			
			if (c.getCommand() == CommandValue.PROCESS)
			{
				currentState = processingState;
				addNoteToList(c.getNoteText());
			}
			else if (c.getCommand() == CommandValue.COMPLETE)
			{
				currentState = doneState;
				setVerified("true");
				addNoteToList(c.getNoteText());
			}
			else
			{
				throw new UnsupportedOperationException("Invalid transition.");
			}
			
		}

		@Override
		public String getStateName() {
			return VERIFYING_NAME;
		}
	}

	/**
	 * Inner class Done State for Task
	 */
	private class DoneState implements TaskState
	{

		@Override
		public void updateState(Command c) {
			
			if (c.getCommand() == CommandValue.BACKLOG)
			{
				currentState = backlogState;
				addNoteToList(c.getNoteText());
				setOwner(UNOWNED);
				setVerified("false");
			}
			else if (c.getCommand() == CommandValue.PROCESS)
			{
				currentState = processingState;
				addNoteToList(c.getNoteText());
				setVerified("false");
			}
			else
			{
				throw new UnsupportedOperationException("Invalid transition.");
			}
			
		}

		@Override
		public String getStateName() {
			return DONE_NAME;
		}
	}
	
	/**
	 * Inner class Rejected State for Task
	 */
	private class RejectedState implements TaskState
	{

		@Override
		public void updateState(Command c) {
			
			if (c.getCommand() == CommandValue.BACKLOG)
			{
				currentState = backlogState;
				addNoteToList(c.getNoteText());
				setOwner("unowned");
			}
			else if (c.getCommand() != CommandValue.BACKLOG)
			{
				throw new UnsupportedOperationException("Invalid transition.");
			}
			
		}

		@Override
		public String getStateName() {
			return REJECTED_NAME;
		}
	}
	
	/**
	 * Delegates to the currentStates updateState(Command) method
	 * @throws UnsupportedOperationException if the currentState determines that the transition, 
	 * 								  as encapsulated by the Command, is not appropriate for the FSM
	 * @param c the command used to update the currentState
	 */
	public void update(Command c) throws UnsupportedOperationException
	{
		
		if (c.getCommand() == CommandValue.BACKLOG)
		{
			currentState.updateState(c);
		}
		else if (c.getCommand() == CommandValue.CLAIM)
		{
			currentState.updateState(c);
		}
		else if (c.getCommand() == CommandValue.PROCESS)
		{
			currentState.updateState(c);
		}
		else if (c.getCommand() == CommandValue.VERIFY)
		{
			currentState.updateState(c);
		}
		else if (c.getCommand() == CommandValue.COMPLETE)
		{
			currentState.updateState(c);
		}
		else if (c.getCommand() == CommandValue.REJECT)
		{
			currentState.updateState(c);
		}
		else
		{
			throw new UnsupportedOperationException("Invalid transition.");
		}
		
		
	}
	
	
	
	
	
	
	
}
