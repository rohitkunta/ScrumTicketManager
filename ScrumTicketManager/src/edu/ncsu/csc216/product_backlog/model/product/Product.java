/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.product;

import java.util.ArrayList;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

/**
 * The Product class, with which tasks are associated
 * @author Rohit Kunta
 */
public class Product {
	/**Stores name of product*/
	private String productName;
	/**Maintains a count of tasks, used to set Ids of tasks*/
	private int counter;
	/**list of tasks associated with product*/
	private ArrayList<Task> taskList;
	
	/**
	 * Constructor for Product
	 * - Sets name, 
	 * - initializes task list to new ArrayList,
	 * - sets counter to 1
	 * @param name the name of the product
	 */
	public Product(String name)
	{
		setProductName(name);
		this.taskList = new ArrayList<Task>();
		this.counter = 1;
		
	}

	/**
	 * Returns the name of the product
	 * @return productName the name of the product
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Sets the name of the product
	 * @param productName the productName to set
	 * @throws IllegalArgumentException if productName is empty or null.
	 */
	public void setProductName(String productName) {
		if (productName == null || productName.length() == 0)
		{
			throw new IllegalArgumentException("Invalid product name.");
		}
		this.productName = productName;
	}
	
	/**
	 * Adds task to task ArrayList in a sorted Order based on ID
	 * - Sorts after adding Task using private helper method
	 * @param task the task being added to the list
	 * @throws IllegalArgumentException if there is a task in the list with the same Id as the parameter.
	 */
	public void addTask(Task task)
	{
		
		for (int i = 0; i < taskList.size(); i++)
		{
			int taskId = taskList.get(i).getTaskId();
			
			if(taskId == task.getTaskId())
			{
				throw new IllegalArgumentException("Task cannot be added.");
			}
		}
		
		taskList.add(task);
		sortTaskList();
		setTaskCounter();
		this.counter++;
	}
	
	/**
	 * Creates a new task and adds it to the task list in a sorted Order based on ID
	 * @param title the title of the task
	 * @param type the type of the task
	 * @param creator the creator of the task
	 * @param note the note of the task
	 * @throws IllegalArgumentException if there is a task in the list with the same Id as the parameter.
	 */
	public void addTask(String title, Type type, String creator, String note)
	{
		setTaskCounter();
		
		Task task = new Task(counter, title, type, creator, note);
		
		for (int i = 0; i < taskList.size(); i++)
		{
			int taskId = taskList.get(i).getTaskId();
			
			if(taskId == task.getTaskId())
			{
				throw new IllegalArgumentException("Task cannot be added.");
			}
		}
		
		taskList.add(task);
		sortTaskList();
		setTaskCounter();
		this.counter++;
	}
	
	/**
	 * Returns the ArrayList of Sorted Tasks
	 * @return taskList the arrayList of tasks
	 */
	public ArrayList<Task> getTasks()
	{
		sortTaskList();
		return this.taskList;
	}
	
	/**
	 * Returns the task in the list with a given id
	 * @param id the id of the task returned
	 * @return task the task with the given id, null if there is no task id.
	 */
	public Task getTaskById(int id)
	{
		
		for (int i = 0; i < taskList.size(); i++)
		{
			int taskId = taskList.get(i).getTaskId();
			
			if (taskId == id)
			{
				return taskList.get(i);
			}
		}
		
		return null;
	}
	
	/**
	 * Deletes the task in the list with a given id
	 * @param id of the task in the list being deleted
	 */
	public void deleteTaskById(int id)
	{
		for (int i = 0; i < taskList.size(); i++)
		{
			int taskId = taskList.get(i).getTaskId();
			
			if (taskId == id)
			{
				
				taskList.remove(i);
			}
		}
		
	}
	
	/**
	 * Will find the Task with the given id and update it by passing in the given Command.
	 * @param id the ID of the task in the list
	 * @param c the given command passed onto task
	 */
	public void executeCommand(int id, Command c)
	{
		if (getTaskById(id) != null)
		{
			getTaskById(id).update(c);
		}

		sortTaskList();
	}
	
	/**
	 * Helper Method: finds the largest task id in the task list and sets the counter field to the max + 1.
	 * - If the list is empty, the counter is set to 1. 
	 */
	private void setTaskCounter()
	{
		int largestId = 0;
		
		for (int i = 0; i < taskList.size(); i++)
		{
			int taskId = taskList.get(i).getTaskId();
			
			if (taskId > largestId)
			{
				largestId = taskId;
			}
		}
		
		this.counter = largestId + 1;
	}
	
	/**
	 * Helper Method - Sorts the taskList by Ascending taskId.
	 * Implements Bubble Sort Algorithm to sort list. if list isn't size of 2
	 */
	private void sortTaskList()
	{
		if(taskList.size() == 2)
		{
			Task firstTask = taskList.get(0);
			Task secondTask = taskList.get(1);
			
			if (firstTask.getTaskId() > secondTask.getTaskId())
			{
				taskList.set(0, secondTask);
				taskList.set(1, firstTask);
			}
			else if (firstTask.getTaskId() < secondTask.getTaskId())
			{
				taskList.set(0, firstTask);
				taskList.set(1, secondTask);
			}

		}
		else
		{
			int i, j; 
			Task temp;
	        boolean swapped;
	        for (i = 0; i < taskList.size() - 1; i++) {
	            swapped = false;
	            for (j = 0; j < taskList.size() - i - 1; j++) {
	                if (taskList.get(j).getTaskId() > taskList.get(j + 1).getTaskId()) {
	                     
	                    // Swap tasks at j and j+1
	                    temp = taskList.get(j);
	                    taskList.set(j, taskList.get(j + 1));
	                    taskList.set(j + 1, temp);
	                    swapped = true;
	                }
	            }
	 
	            // If no two elements were
	            // swapped by inner loop, then break
	            if (!swapped)
	                break;
	        }
		}
		
	}
	
	
	

}
