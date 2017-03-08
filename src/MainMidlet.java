import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MainMidlet extends MIDlet implements CommandListener, ListUpdateListener {

	public static Display display;
	private Command addNote;
	private Command selectNote;
	private Form addNoteForm;
	private List list;

	public MainMidlet() {
		display = Display.getDisplay(this);
		list = new List("TaskBook", Choice.IMPLICIT);
		addNoteForm = new AddNoteForm(this, list);
		setupCommands();
	}

	protected void startApp() throws MIDletStateChangeException {
		System.out.println("Midlet: startApp()");
		display.setCurrent(list);
		inflateList();
	}

	protected void pauseApp() {

	}

	protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {

	}

	public void commandAction(Command c, Displayable d) {
		if (c == addNote) 
			addNewNote();
		else if (c == selectNote)
			showNoteDetails();
	}
	
	public static Display getDisplay() {
		return display;
	}
	
	public void onListUpdate() {
		notifyItemInserted();
	}
	
	private void setupCommands() {
		addNote = new Command("Add", Command.SCREEN, 0);
		selectNote = new Command("Select", Command.SCREEN, 1);		
		list.addCommand(addNote);
		list.addCommand(selectNote);
		list.setCommandListener(this);
	}

	private void showNoteDetails() {
		Vector vector = RecordStoreManager.getInstance().getRecords();
		Note note = (Note) vector.elementAt(list.getSelectedIndex());
		System.out.println("Command clicked: Select note: " + note.toString());
		display.setCurrent(new ShowNoteForm(list, note));
	}

	private void inflateList() {
		Vector vector = RecordStoreManager.getInstance().getRecords();
		Enumeration enumeration = vector.elements();
		while(enumeration.hasMoreElements())
			addListRow(enumeration);
	}

	private void addListRow(Enumeration enumeration) {
		Note note = (Note) enumeration.nextElement();
		System.out.println("Read: " + note.toString());
		list.append(note.getTitle(), null);
	}

	private void notifyItemInserted() {
		Vector vector = RecordStoreManager.getInstance().getRecords();
		Note note = (Note) vector.lastElement();
		list.append(note.getTitle(), null);
	}

	private void addNewNote() {
		System.out.println("Command clicked: Add new note");
		display.setCurrent(addNoteForm);
	}
}
