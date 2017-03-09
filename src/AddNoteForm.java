import java.util.Calendar;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;


public class AddNoteForm extends Form implements CommandListener {

	private Command save;
	private Command cancel;
	private Display display;
	private Displayable backDisplay;
	private ListUpdateListener callback;
	
	private ChoiceGroup categoryField;
	private DateField dateField;
	private TextField titleField;
	private TextField contentField;
	
	public AddNoteForm(ListUpdateListener callback, Displayable backDisplay) {
		super("Add note");
		this.backDisplay = backDisplay;
		this.callback = callback;
		display = MainMidlet.getDisplay();
		setupCommands();
		setupFields();
	}

	public void commandAction(Command c, Displayable d) {
		if(c == save)
			saveNote();
		else if (c == cancel)
			closeForm();
	}

	private void saveNote() {
		saveNoteToDB();
		closeForm();
		cleanFields();
	}

	private void closeForm() {
		display.setCurrent(backDisplay);
	}

	private void saveNoteToDB() {
		System.out.println("Command clicked: Saved");
		Note note = createNote();
		if(note!=null) {
			System.out.println("Saving: " + note.toString());
			byte[] record = ByteUtils.toByteArray(note);
			RecordStoreManager.getInstance().saveRecord(record);
			callback.onListUpdate(note);
		}
	}

	private Note createNote() {
		String title = titleField.getString();
		if(title.equals(""))
			return null;
		long dateInMilis = getDateInMilis();
		return new Note(
				title,
				contentField.getString(),
				dateInMilis,
				Category.toCategory(categoryField.getString(categoryField.getSelectedIndex())));
	}

	private long getDateInMilis() {
		long dateInMilis = dateField.getDate() == null ? Calendar.getInstance().getTime().getTime() : dateField.getDate().getTime();
		return dateInMilis;
	}
	
	private void setupCommands(){
		save = new Command("Save", Command.BACK, 1);
		cancel = new Command("Cancel", Command.CANCEL, 1);
		this.addCommand(save);
		this.addCommand(cancel);
		this.setCommandListener(this);
	}
	
	private void setupFields() {
		titleField = new TextField("Title", "", 32, TextField.ANY);
		contentField = new TextField("Content", "", 128, TextField.ANY);
		dateField = new DateField("Date", DateField.DATE);
		categoryField = new ChoiceGroup("Category", Choice.EXCLUSIVE, Category.elements, null);
		
		this.append(titleField);
		this.append(contentField);
		this.append(dateField);
		this.append(categoryField);
	}

	private void cleanFields() {
		titleField.setString("");
		contentField.setString("");
		dateField.setDate(Calendar.getInstance().getTime());
		categoryField.setSelectedIndex(0, true);
	}
}
