import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;

public class NotesList extends List implements CommandListener, ListUpdateListener {
	
	private Command addNote;
	private Command selectNote;
	private Display display;
	private Form addNoteForm;

	public NotesList() {
		super("TaskBook", Choice.IMPLICIT);
		display = MainMidlet.getDisplay();
		addNoteForm = new AddNoteForm(this, this);
		setupCommands();
	}
	
	private void setupCommands() {
		addNote = new Command("Add", Command.SCREEN, 0);
		selectNote = new Command("Select", Command.SCREEN, 1);		
		addCommand(addNote);
		addCommand(selectNote);
		setCommandListener(this);
	}

	public void commandAction(Command c, Displayable d) {
		if (c == addNote) 
			addNewNote();
		else if (c == selectNote)
			showNoteDetails();
	}
	
	public void inflateList() {
		Vector vector = RecordStoreManager.getInstance().getRecords();
		Enumeration enumeration = vector.elements();
		while(enumeration.hasMoreElements())
			addListRow(enumeration);
	}
	
	public void onListUpdate(Object newNote) {
		notifyItemInserted(newNote);
	}

	private void addListRow(Enumeration enumeration) {
		Note note = (Note) enumeration.nextElement();
		System.out.println("Read: " + note.toString());
		this.append(note.getTitle(), note.getCategory().getIcon());
	}

	private void notifyItemInserted(Object newNote) {
		Vector vector = RecordStoreManager.getInstance().getRecords();
		int index = vector.indexOf(newNote);
		System.out.println("Index: " + String.valueOf(index));
		Note note = (Note) newNote;
		this.insert(index, note.getTitle(), note.getCategory().getIcon());
	}
		
	private void addNewNote() {
		System.out.println("Command clicked: Add new note");
		display.setCurrent(addNoteForm);
	}
	
	private void showNoteDetails() {
		Vector vector = RecordStoreManager.getInstance().getRecords();
		Note note = (Note) vector.elementAt(getSelectedIndex());
		System.out.println("Command clicked: Select note: " + note.toString());
		display.setCurrent(new ShowNoteForm(this, note));
	}

}
