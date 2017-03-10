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
	private Command exit;
	private Command selectNote;
	private Command sortByPriority;
	private Command sortByTime;
	private Display display;
	private Form addNoteForm;
	private MainOperations mainCallback;

	public NotesList(MainOperations mainOperations) {
		super("TaskBook", Choice.IMPLICIT);
		display = MainMidlet.getDisplay();
		addNoteForm = new AddNoteForm(this, this);
		setupCommands();
		this.mainCallback = mainOperations;
	}

	private void setupCommands() {
		addNote = new Command("Add", Command.SCREEN, 0);
		exit = new Command("Exit", Command.EXIT, -1);
		selectNote = new Command("Select", Command.SCREEN, 1);
		sortByPriority = new Command("By Prior.", Command.SCREEN, 0);
		sortByTime = new Command("By Time", Command.SCREEN, 0);
		addCommand(addNote);
		addCommand(exit);
		addCommand(selectNote);
		addCommand(sortByPriority);
		addCommand(sortByTime);
		setCommandListener(this);
	}

	public void commandAction(Command c, Displayable d) {
		if (c == addNote) 
			addNewNote();
		else if (c == selectNote)
			showNoteDetails();
		else if (c == sortByPriority)
			sortListByPriority();
		else if (c == sortByTime)
			sortListByTime();
		else if (c == exit)
			exitApplication();
	}

	private void exitApplication() {
		mainCallback.exitApplication(false);
	}

	private void sortListByTime() {
		this.deleteAll();
		RecordStoreManager.getInstance().sortByTime();
		inflateList();
	}

	private void sortListByPriority() {
		this.deleteAll();
		RecordStoreManager.getInstance().sortByPriority();
		inflateList();
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
