import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MainMidlet extends MIDlet implements CommandListener, ItemCommandListener, ListUpdateListener {
	
	public static Display display;
	private List list;
	private Command addNote;
	private Command selectNote;
	private Form addNoteForm;

	public MainMidlet() {
		display = Display.getDisplay(this);
		list = new List("TaskBook", Choice.IMPLICIT);
		addNoteForm = new AddNoteForm(this, list);
		
		//Setting up commands
		addNote = new Command("Add", Command.SCREEN, 0);
		selectNote = new Command("Select", Command.SCREEN, 1);
		
		list.addCommand(addNote);
		list.addCommand(selectNote);
		list.setCommandListener(this);
	}
	
	protected void startApp() throws MIDletStateChangeException {
		System.out.println("Midlet: startApp()");
		display.setCurrent(list);
		inflateForm();
	}

	protected void pauseApp() {

	}
	
	protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {

	}

	public void commandAction(Command c, Displayable d) {
		if (c == addNote) {
			System.out.println("Command clicked: Add new note");
			addNewNote();
		} else if (c == selectNote) {
			Vector vector = RecordStoreManager.getInstance().getRecords();
			Note note = (Note) vector.elementAt(list.getSelectedIndex());
			System.out.println("Command clicked: Select note: " + note.toString());
			display.setCurrent(new ShowNoteForm(list, note));
		}
	}
	
	public static Display getDisplay() {
		return display;
	}
	
	private void inflateForm() {
		Vector vector = RecordStoreManager.getInstance().getRecords();
		Enumeration enumeration = vector.elements();
		while(enumeration.hasMoreElements()) {
			Note note = (Note) enumeration.nextElement();
			System.out.println("Read: " + note.toString());
			list.append(note.getTitle(), null);
		}
	}
	
	private void notifyItemInserted() {
		Vector vector = RecordStoreManager.getInstance().getRecords();
		Note note = (Note) vector.lastElement();
		list.append(note.getTitle(), null);
	}

	private void addNewNote() {
		display.setCurrent(addNoteForm);
	}

	public void commandAction(Command c, Item item) {
		System.out.println("Item " + item.toString() + " clicked");
	}

	public void onListUpdate() {
		notifyItemInserted();
	}
}
