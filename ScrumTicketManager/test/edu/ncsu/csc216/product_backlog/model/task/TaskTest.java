package edu.ncsu.csc216.product_backlog.model.task;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

/**
 * Tests Task concrete class and inner classes
 */
class TaskTest {
	
	/**Sets the constant for taskId*/
	private static final int TASK_ID = 3;
	/**Sets the constant for title*/
	private static final String TITLE = "Express Rohit";
	/**Sets the constant for creator*/
	private static final String CREATOR = "Rohit";
	/**Sets the constant for owner*/
	private static final String OWNER = "Suchir";
	/**Sets the constant for is verified*/
	private static final boolean IS_VERIFIED = false;
	/**Sets the constant for is verified*/
	private static final String VERIFIED = "false";
	/**Sets the constant for type*/
	private static final Type TYPE = Type.FEATURE;
	/**Sets the constant for owner*/
	private static final String TYPE_STRING = "F";
	/**Sets the constant for owner*/
	private static final String STATE = "Backlog";
	/**Sets the constant for note*/
	private static final String NOTE = "THIS IS TASK NOTE";
	/**Sets the constant for note*/
	private static final ArrayList<String> NOTES = new ArrayList<String>();
	
	/**Sets the note text for transitions state updates*/
	private static final String NOTE_UPDATE = "Needed to fix something";
	
	
	@Test
	void testTaskShortConstructorValid() {
		// Test a valid construction
		Task t = assertDoesNotThrow(
				() -> new Task(TASK_ID, TITLE, TYPE, CREATOR, NOTE),
				"Should not throw exception");
		
		assertAll("Task", 
				() -> assertEquals(TASK_ID, t.getTaskId(), "incorrect id"), 
				() -> assertEquals(TITLE, t.getTitle(), "incorrect title"),
				() -> assertEquals(TYPE, t.getType(), "incorrect type"),
				() -> assertEquals("unowned", t.getOwner(), "incorrect owner"),
				() -> assertEquals(CREATOR, t.getCreator(), "incorrect creator"),
				() -> assertEquals("[Backlog] " + NOTE, t.getNotes().get(0), "incorrect note"));	
		
	}

	@Test
	void testTaskShortConstructorInvalid() {
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(-1, TITLE, TYPE, CREATOR, NOTE));
		assertEquals("Invalid task information.", e1.getMessage(), "Incorrect exception thrown with invalid task id - " + -1);
		
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASK_ID, null, TYPE, CREATOR, NOTE));
		assertEquals("Invalid task information.", e2.getMessage(), "Incorrect exception thrown with invalid task title - " + null);
		
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASK_ID, "", TYPE, CREATOR, NOTE));
		assertEquals("Invalid task information.", e3.getMessage(), "Incorrect exception thrown with invalid task title - " + "");
		
		Exception e4 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASK_ID, TITLE, TYPE, null, NOTE));
		assertEquals("Invalid task information.", e4.getMessage(), "Incorrect exception thrown with invalid task creator - " + null);
		
		
		Exception e5 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASK_ID, TITLE, TYPE, "", NOTE));
		assertEquals("Invalid task information.", e5.getMessage(), "Incorrect exception thrown with invalid task creator - " + "");
		
		Exception e6 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASK_ID, TITLE, TYPE, CREATOR, null));
		assertEquals("Invalid task information.", e6.getMessage(), "Incorrect exception thrown with invalid task note - " + null);
		
		
		Exception e7 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASK_ID, TITLE, TYPE, CREATOR, ""));
		assertEquals("Invalid task information.", e7.getMessage(), "Incorrect exception thrown with invalid task  - " + "");
		
		Exception e8 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASK_ID, TITLE, null, CREATOR, NOTE));
		assertEquals("Invalid task information.", e8.getMessage(), "Incorrect exception thrown with invalid task type - " + null);
	}
	
	@Test
	void testTaskLongConstructorValid() {
		// Test a valid construction
		Task t = assertDoesNotThrow(
				() -> new Task(TASK_ID, STATE, TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES),
				"Should not throw exception");
		
		assertAll("Task", 
				() -> assertEquals(TASK_ID, t.getTaskId(), "incorrect id"),
				() -> assertEquals(STATE, t.getStateName(), "incorrect State name"),
				() -> assertEquals(TITLE, t.getTitle(), "incorrect title"),
				() -> assertEquals(TYPE_STRING, t.getTypeShortName(), "incorrect type"),
				() -> assertEquals(CREATOR, t.getCreator(), "incorrect creator"),
				() -> assertEquals("unowned", t.getOwner(), "incorrect owner"),
				() -> assertFalse(t.isVerified(), "incorrect verfication"),
				() -> assertEquals(NOTES.size(), t.getNotes().size(), "incorrect notes"));	
	}	
	
	@Test
	void testTaskLongConstructorInvalid() {
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASK_ID, "", TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES));
		assertEquals("Invalid task information.", e1.getMessage(), "Incorrect exception thrown with invalid task state - " + "");
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASK_ID, null, TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES));
		assertEquals("Invalid task information.", e2.getMessage(), "Incorrect exception thrown with invalid task state - " + null);
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASK_ID, STATE, TITLE, "", CREATOR, OWNER, VERIFIED, NOTES));
		assertEquals("Invalid task information.", e3.getMessage(), "Incorrect exception thrown with invalid task type - " + "");
		
		Exception e4 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASK_ID, STATE, TITLE, TYPE_STRING, CREATOR, "", VERIFIED, NOTES));
		assertEquals("Invalid task information.", e4.getMessage(), "Incorrect exception thrown with invalid task Owner - " + "");
		
		Exception e5 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASK_ID, STATE, TITLE, TYPE_STRING, CREATOR, null, VERIFIED, NOTES));
		assertEquals("Invalid task information.", e5.getMessage(), "Incorrect exception thrown with invalid task state - " + null);
		
		Exception e6 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASK_ID, STATE, TITLE, TYPE_STRING, CREATOR, OWNER, "", NOTES));
		assertEquals("Invalid task information.", e6.getMessage(), "Incorrect exception thrown with invalid task Verfied - " + "");
		
		Exception e7 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASK_ID, STATE, TITLE, TYPE_STRING, CREATOR, OWNER, null, NOTES));
		assertEquals("Invalid task information.", e7.getMessage(), "Incorrect exception thrown with invalid task Verfied - " + null);
		
		Exception e8 = assertThrows(IllegalArgumentException.class,
				() -> new Task(TASK_ID, STATE, TITLE, TYPE_STRING, CREATOR, OWNER, "poop", NOTES));
		assertEquals("Invalid task information.", e8.getMessage(), "Incorrect exception thrown with invalid task Verfied - " + "poop");
		
	}	

	@Test
	void testGetTaskId() {
		Task t = new Task(TASK_ID, TITLE, TYPE, CREATOR, NOTE);
		
		assertEquals(TASK_ID, t.getTaskId());
	}

	@Test
	void testGetTitle() {
		Task t = new Task(TASK_ID, TITLE, TYPE, CREATOR, NOTE);
		
		assertEquals(TITLE, t.getTitle());
	}

	@Test
	void testGetCreator() {
		Task t = new Task(TASK_ID, TITLE, TYPE, CREATOR, NOTE);
		
		assertEquals(CREATOR, t.getCreator());
	}

	@Test
	void testGetOwner() {
		Task t = new Task(TASK_ID, TITLE, TYPE, CREATOR, NOTE);
		
		assertEquals("unowned", t.getOwner());
		
		Task t2 = new Task(TASK_ID, "Owned", TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES);
		
		assertEquals(OWNER, t2.getOwner());
	}

	@Test
	void testIsVerified() {
		
		Task t2 = new Task(TASK_ID, STATE, TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES);
		
		assertEquals(IS_VERIFIED, t2.isVerified());
	}

	@Test
	void testGetType() {
		
		Task t = new Task(TASK_ID, TITLE, TYPE, CREATOR, NOTE);
		
		assertEquals(TYPE, t.getType());
	}

	@Test
	void testGetNotes() {
		Task t = new Task(TASK_ID, TITLE, TYPE, CREATOR, NOTE);
		
		assertEquals("[Backlog] " + NOTE, t.getNotes().get(0));
		
		Task t2 = new Task(TASK_ID, STATE, TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES);
		
		assertEquals(NOTES, t2.getNotes());
	}

	@Test
	void testGetStateName() {
		Task t2 = new Task(TASK_ID, STATE, TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES);
		
		assertEquals(STATE, t2.getStateName());
		
	}

	@Test
	void testGetTypeShortName() {
		Task t = new Task(TASK_ID, TITLE, TYPE, CREATOR, NOTE);
		
		assertEquals("F", t.getTypeShortName());
	}

	@Test
	void testGetTypeLongName() {
		Task t = new Task(TASK_ID, TITLE, TYPE, CREATOR, NOTE);
		
		assertEquals("Feature", t.getTypeLongName());
	}

	@Test
	void testGetNotesList() {
		Task t = new Task(TASK_ID, TITLE, TYPE, CREATOR, NOTE);
		
		assertEquals("- [Backlog] " + NOTE + "\r\n", t.getNotesList());
	}

	@Test
	void testToString() {
		Task t = new Task(TASK_ID, TITLE, TYPE, CREATOR, NOTE);
		
		assertEquals("* 3,Backlog,Express Rohit,F,Rohit,unowned,false\r\n"
				+ "- [Backlog] THIS IS TASK NOTE\r\n", t.toString(), String.format("Incorrect, expected:%s got %s", "* 3,Backlog,F,Rohit,Suchir,false\r\n" + "- THIS IS TASK NOTE\r\n", t.toString()));
	}

	/**
	 * Tests State Pattern
	 */
	@Test
	void testUpdate() {
		
		//claim on backlog BACKLOG A
		NOTES.clear();
		Task tBacklogClaim = new Task(TASK_ID, STATE, TITLE, TYPE_STRING, CREATOR, "unowned", VERIFIED, NOTES);
		Command c = new Command(CommandValue.CLAIM, OWNER, NOTE_UPDATE);
		
		tBacklogClaim.update(c);
		
		assertAll("Task", 
				() -> assertEquals(TASK_ID, tBacklogClaim.getTaskId(), "incorrect id"),
				() -> assertEquals("Owned", tBacklogClaim.getStateName(), "incorrect State name"),
				() -> assertEquals(TITLE, tBacklogClaim.getTitle(), "incorrect title"),
				() -> assertEquals(TYPE_STRING, tBacklogClaim.getTypeShortName(), "incorrect type"),
				() -> assertEquals(CREATOR, tBacklogClaim.getCreator(), "incorrect creator"),
				() -> assertEquals(OWNER, tBacklogClaim.getOwner(), "incorrect owner"),
				() -> assertFalse(tBacklogClaim.isVerified(), "incorrect verfication"),
				() -> assertEquals(NOTES.get(0), tBacklogClaim.getNotes().get(0), "incorrect notes"));
		
		
		//Rejected on backlog BACKLOG B
		NOTES.clear();
		Task tBacklogReject = new Task(TASK_ID, STATE, TITLE, TYPE_STRING, CREATOR, "unowned", VERIFIED, NOTES);
		Command c2 = new Command(CommandValue.REJECT, null, NOTE_UPDATE);
		
		tBacklogReject.update(c2);
		
		assertAll("Task", 
				() -> assertEquals(TASK_ID, tBacklogReject.getTaskId(), "incorrect id"),
				() -> assertEquals("Rejected", tBacklogReject.getStateName(), "incorrect State name"),
				() -> assertEquals(TITLE, tBacklogReject.getTitle(), "incorrect title"),
				() -> assertEquals(TYPE_STRING, tBacklogReject.getTypeShortName(), "incorrect type"),
				() -> assertEquals(CREATOR, tBacklogReject.getCreator(), "incorrect creator"),
				() -> assertEquals("unowned", tBacklogReject.getOwner(), "incorrect owner"),
				() -> assertFalse(tBacklogReject.isVerified(), "incorrect verfication"),
				() -> assertEquals(NOTES.size(), tBacklogReject.getNotes().size(), "incorrect notes"));
		
		
		//Process on Owned OWNED A
		NOTES.clear();
		Task tClaimProcessing = new Task(TASK_ID, "Owned", TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES);
		Command c3 = new Command(CommandValue.PROCESS, null, NOTE_UPDATE);
		
		tClaimProcessing.update(c3);
		
		assertAll("Task", 
				() -> assertEquals(TASK_ID, tClaimProcessing.getTaskId(), "incorrect id"),
				() -> assertEquals("Processing", tClaimProcessing.getStateName(), "incorrect State name"),
				() -> assertEquals(TITLE, tClaimProcessing.getTitle(), "incorrect title"),
				() -> assertEquals(TYPE_STRING, tClaimProcessing.getTypeShortName(), "incorrect type"),
				() -> assertEquals(CREATOR, tClaimProcessing.getCreator(), "incorrect creator"),
				() -> assertEquals(OWNER, tClaimProcessing.getOwner(), "incorrect owner"),
				() -> assertFalse(tClaimProcessing.isVerified(), "incorrect verfication"),
				() -> assertEquals(NOTES.get(0), tClaimProcessing.getNotes().get(0), "incorrect notes"));
		
		
		//backlog on Owned OWNED C
		NOTES.clear();
		Task tClaimBacklog = new Task(TASK_ID, "Owned", TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES);
		Command c4 = new Command(CommandValue.REJECT, null, NOTE_UPDATE);
		
		tClaimBacklog.update(c4);
		
		assertAll("Task", 
				() -> assertEquals(TASK_ID, tClaimBacklog.getTaskId(), "incorrect id"),
				() -> assertEquals("Rejected", tClaimBacklog.getStateName(), "incorrect State name"),
				() -> assertEquals(TITLE, tClaimBacklog.getTitle(), "incorrect title"),
				() -> assertEquals(TYPE_STRING, tClaimBacklog.getTypeShortName(), "incorrect type"),
				() -> assertEquals(CREATOR, tClaimBacklog.getCreator(), "incorrect creator"),
				() -> assertEquals("unowned", tClaimBacklog.getOwner(), "incorrect owner"),
				() -> assertFalse(tClaimBacklog.isVerified(), "incorrect verfication"),
				() -> assertEquals(NOTES.get(0), tClaimBacklog.getNotes().get(0), "incorrect notes"));
		
		
		//Reject on Owned OWNED B
		NOTES.clear();
		Task tClaimReject = new Task(TASK_ID, "Owned", TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES);
		Command c5 = new Command(CommandValue.BACKLOG, null, NOTE_UPDATE);
		
		tClaimReject.update(c5);
		
		assertAll("Task", 
				() -> assertEquals(TASK_ID, tClaimReject.getTaskId(), "incorrect id"),
				() -> assertEquals("Backlog", tClaimReject.getStateName(), "incorrect State name"),
				() -> assertEquals(TITLE, tClaimReject.getTitle(), "incorrect title"),
				() -> assertEquals(TYPE_STRING, tClaimReject.getTypeShortName(), "incorrect type"),
				() -> assertEquals(CREATOR, tClaimReject.getCreator(), "incorrect creator"),
				() -> assertEquals("unowned", tClaimReject.getOwner(), "incorrect owner"),
				() -> assertFalse(tClaimReject.isVerified(), "incorrect verfication"),
				() -> assertEquals(NOTES.get(0), tClaimReject.getNotes().get(0), "incorrect notes"));
		
		
		
		//Process on Processing PROCESS A
		NOTES.clear();
		Task tProcessProcess = new Task(TASK_ID, "Processing", TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES);
		Command c6 = new Command(CommandValue.PROCESS, null, NOTE_UPDATE);
		
		tProcessProcess.update(c6);
		
		assertAll("Task", 
				() -> assertEquals(TASK_ID, tProcessProcess.getTaskId(), "incorrect id"),
				() -> assertEquals("Processing", tProcessProcess.getStateName(), "incorrect State name"),
				() -> assertEquals(TITLE, tProcessProcess.getTitle(), "incorrect title"),
				() -> assertEquals(TYPE_STRING, tProcessProcess.getTypeShortName(), "incorrect type"),
				() -> assertEquals(CREATOR, tProcessProcess.getCreator(), "incorrect creator"),
				() -> assertEquals(OWNER, tProcessProcess.getOwner(), "incorrect owner"),
				() -> assertFalse(tProcessProcess.isVerified(), "incorrect verfication"),
				() -> assertEquals(NOTES.get(0), tProcessProcess.getNotes().get(0), "incorrect notes"));
		
		
		//Verify on Processing PROCESS B
		NOTES.clear();
		Task tProcessVerify = new Task(TASK_ID, "Processing", TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES);
		Command c7 = new Command(CommandValue.VERIFY, null, NOTE_UPDATE);
		
		tProcessVerify.update(c7);
		
		assertAll("Task", 
				() -> assertEquals(TASK_ID, tProcessVerify.getTaskId(), "incorrect id"),
				() -> assertEquals("Verifying", tProcessVerify.getStateName(), "incorrect State name"),
				() -> assertEquals(TITLE, tProcessVerify.getTitle(), "incorrect title"),
				() -> assertEquals(TYPE_STRING, tProcessVerify.getTypeShortName(), "incorrect type"),
				() -> assertEquals(CREATOR, tProcessVerify.getCreator(), "incorrect creator"),
				() -> assertEquals(OWNER, tProcessVerify.getOwner(), "incorrect owner"),
				() -> assertFalse(tProcessVerify.isVerified(), "incorrect verfication"),
				() -> assertEquals(NOTES.get(0), tProcessVerify.getNotes().get(0), "incorrect notes"));
		
		
		
		//Done on Processing PROCESS C
		NOTES.clear();
		Task tProcessDone = new Task(TASK_ID, "Processing", TITLE, "KA", CREATOR, OWNER, VERIFIED, NOTES);
		Command c8 = new Command(CommandValue.COMPLETE, null, NOTE_UPDATE);
		
		tProcessDone.update(c8);
		
		assertAll("Task", 
				() -> assertEquals(TASK_ID, tProcessDone.getTaskId(), "incorrect id"),
				() -> assertEquals("Done", tProcessDone.getStateName(), "incorrect State name"),
				() -> assertEquals(TITLE, tProcessDone.getTitle(), "incorrect title"),
				() -> assertEquals("KA", tProcessDone.getTypeShortName(), "incorrect type"),
				() -> assertEquals(CREATOR, tProcessDone.getCreator(), "incorrect creator"),
				() -> assertEquals(OWNER, tProcessDone.getOwner(), "incorrect owner"),
				() -> assertFalse(tProcessDone.isVerified(), "incorrect verfication"),
				() -> assertEquals(NOTES.get(0), tProcessDone.getNotes().get(0), "incorrect notes"));
		
		
		
		//Backlog on Processing PROCESS D
		NOTES.clear();
		Task tProcessBacklog = new Task(TASK_ID, "Processing", TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES);
		Command c9 = new Command(CommandValue.BACKLOG, null, NOTE_UPDATE);
		
		tProcessBacklog.update(c9);
		
		assertAll("Task", 
				() -> assertEquals(TASK_ID, tProcessBacklog.getTaskId(), "incorrect id"),
				() -> assertEquals("Backlog", tProcessBacklog.getStateName(), "incorrect State name"),
				() -> assertEquals(TITLE, tProcessBacklog.getTitle(), "incorrect title"),
				() -> assertEquals(TYPE_STRING, tProcessBacklog.getTypeShortName(), "incorrect type"),
				() -> assertEquals(CREATOR, tProcessBacklog.getCreator(), "incorrect creator"),
				() -> assertEquals("unowned", tProcessBacklog.getOwner(), "incorrect owner"),
				() -> assertFalse(tProcessBacklog.isVerified(), "incorrect verfication"),
				() -> assertEquals(NOTES.get(0), tProcessBacklog.getNotes().get(0), "incorrect notes"));
		
		
		
		//Done on Verifying VERIFY A
		NOTES.clear();
		Task tVerifyDone = new Task(TASK_ID, "Verifying", TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES);
		Command c10 = new Command(CommandValue.COMPLETE, null, NOTE_UPDATE);
		
		tVerifyDone.update(c10);
		
		assertAll("Task", 
				() -> assertEquals(TASK_ID, tVerifyDone.getTaskId(), "incorrect id"),
				() -> assertEquals("Done", tVerifyDone.getStateName(), "incorrect State name"),
				() -> assertEquals(TITLE, tVerifyDone.getTitle(), "incorrect title"),
				() -> assertEquals(TYPE_STRING, tVerifyDone.getTypeShortName(), "incorrect type"),
				() -> assertEquals(CREATOR, tVerifyDone.getCreator(), "incorrect creator"),
				() -> assertEquals(OWNER, tVerifyDone.getOwner(), "incorrect owner"),
				() -> assertTrue(tVerifyDone.isVerified(), "incorrect verfication"),
				() -> assertEquals(NOTES.get(0), tVerifyDone.getNotes().get(0), "incorrect notes"));
		
		
		
		//Process on Verifying VERIFY B
		NOTES.clear();
		Task tVerifyProcess = new Task(TASK_ID, "Verifying", TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES);
		Command c11 = new Command(CommandValue.PROCESS, null, NOTE_UPDATE);
		
		tVerifyProcess.update(c11);
		
		assertAll("Task", 
				() -> assertEquals(TASK_ID, tVerifyProcess.getTaskId(), "incorrect id"),
				() -> assertEquals("Processing", tVerifyProcess.getStateName(), "incorrect State name"),
				() -> assertEquals(TITLE, tVerifyProcess.getTitle(), "incorrect title"),
				() -> assertEquals(TYPE_STRING, tVerifyProcess.getTypeShortName(), "incorrect type"),
				() -> assertEquals(CREATOR, tVerifyProcess.getCreator(), "incorrect creator"),
				() -> assertEquals(OWNER, tVerifyProcess.getOwner(), "incorrect owner"),
				() -> assertFalse(tVerifyProcess.isVerified(), "incorrect verfication"),
				() -> assertEquals(NOTES.get(0), tVerifyProcess.getNotes().get(0), "incorrect notes"));
		
		
		//Process on Done DONE A
		NOTES.clear();
		Task tDoneProcess = new Task(TASK_ID, "Done", TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES);
		Command c12 = new Command(CommandValue.PROCESS, null, NOTE_UPDATE);
		
		tDoneProcess.update(c12);
		
		assertAll("Task", 
				() -> assertEquals(TASK_ID, tDoneProcess.getTaskId(), "incorrect id"),
				() -> assertEquals("Processing", tDoneProcess.getStateName(), "incorrect State name"),
				() -> assertEquals(TITLE, tDoneProcess.getTitle(), "incorrect title"),
				() -> assertEquals(TYPE_STRING, tDoneProcess.getTypeShortName(), "incorrect type"),
				() -> assertEquals(CREATOR, tDoneProcess.getCreator(), "incorrect creator"),
				() -> assertEquals(OWNER, tDoneProcess.getOwner(), "incorrect owner"),
				() -> assertEquals(NOTES.get(0), tDoneProcess.getNotes().get(0), "incorrect notes"));
		
		
		//Backlog on Done DONE B
		NOTES.clear();
		Task tDoneBacklog = new Task(TASK_ID, "Done", TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES);
		Command c13 = new Command(CommandValue.BACKLOG, null, NOTE_UPDATE);
		
		tDoneBacklog.update(c13);
		
		assertAll("Task", 
				() -> assertEquals(TASK_ID, tDoneBacklog.getTaskId(), "incorrect id"),
				() -> assertEquals("Backlog", tDoneBacklog.getStateName(), "incorrect State name"),
				() -> assertEquals(TITLE, tDoneBacklog.getTitle(), "incorrect title"),
				() -> assertEquals(TYPE_STRING, tDoneBacklog.getTypeShortName(), "incorrect type"),
				() -> assertEquals(CREATOR, tDoneBacklog.getCreator(), "incorrect creator"),
				() -> assertEquals("unowned", tDoneBacklog.getOwner(), "incorrect owner"),
				() -> assertEquals(NOTES.get(0), tDoneBacklog.getNotes().get(0), "incorrect notes"));
		
		
		
		//Backlog on Rejected REJECT A
		NOTES.clear();
		Task tRejectedBacklog = new Task(TASK_ID, "Rejected", TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES);
		Command c14 = new Command(CommandValue.BACKLOG, null, NOTE_UPDATE);
		
		tRejectedBacklog.update(c14);
		
		assertAll("Task", 
				() -> assertEquals(TASK_ID, tRejectedBacklog.getTaskId(), "incorrect id"),
				() -> assertEquals("Backlog", tRejectedBacklog.getStateName(), "incorrect State name"),
				() -> assertEquals(TITLE, tRejectedBacklog.getTitle(), "incorrect title"),
				() -> assertEquals(TYPE_STRING, tRejectedBacklog.getTypeShortName(), "incorrect type"),
				() -> assertEquals(CREATOR, tRejectedBacklog.getCreator(), "incorrect creator"),
				() -> assertEquals("unowned", tRejectedBacklog.getOwner(), "incorrect owner"),
				() -> assertFalse(tVerifyProcess.isVerified(), "incorrect verfication"),
				() -> assertEquals(NOTES.get(0), tRejectedBacklog.getNotes().get(0), "incorrect notes"));
		
		//Invalid Transition of Rejected 
		//Backlog on Rejected REJECT A
		NOTES.clear();
		Task tRejectedInvalid = new Task(TASK_ID, "Rejected", TITLE, TYPE_STRING, CREATOR, OWNER, VERIFIED, NOTES);
		Command c15 = new Command(CommandValue.CLAIM, "Rohit", NOTE_UPDATE);
		
		
		Exception e1 = assertThrows(UnsupportedOperationException.class,
				() -> tRejectedInvalid.update(c15));
		assertEquals("Invalid transition.", e1.getMessage(), "Incorrect exception thrown with invalid transition on rejected - " + e1.getMessage());
				
	}
	
	
	


}
