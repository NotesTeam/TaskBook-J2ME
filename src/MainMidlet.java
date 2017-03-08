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
	private Form addNoteForm;

	public MainMidlet() {
		display = Display.getDisplay(this);
		list = new List("TaskBook", Choice.IMPLICIT);
		addNoteForm = new AddNoteForm(this, list);
		
		//Setting up commands
		addNote = new Command("Dodaj", Command.SCREEN, 1);
		list.addCommand(addNote);
		list.setCommandListener(this);
		
//		StringItem sampleItem = new StringItem("Title", "Content", Item.BUTTON);
//		form.append(sampleItem);
//		form.append(new StringItem("Title", "Content", Item.BUTTON));
//		sampleItem.setDefaultCommand(new Command("Set", Command.ITEM, 1));    
//		sampleItem.setItemCommandListener(this);   
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
