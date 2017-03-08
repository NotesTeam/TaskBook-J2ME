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
	
	private TextField titleField;
	private TextField contentField;
	private DateField dateField;
	private ChoiceGroup categoryField;
	
	public AddNoteForm(ListUpdateListener callback, Displayable backDisplay) {
		super("Add note");
		this.backDisplay = backDisplay;
		this.callback = callback;
		setupCommands();
		display = MainMidlet.getDisplay();
		setupFields();
	}

	public void commandAction(Command c, Displayable d) {
		if(c == save) {
			System.out.println("Command clicked: Saved");
			saveNote();
			display.setCurrent(backDisplay);
		} else if (c == cancel) {
			System.out.println("Command clicked: Cancel");
			display.setCurrent(backDisplay);
		}
	}

	private void saveNote() {
		Note note = createNote();
		if(note!=null) {
			System.out.println("Saving: " + note.toString());
			byte[] record = ByteUtils.toByteArray(note);
			RecordStoreManager.getInstance().saveRecord(record);
			callback.onListUpdate();
			cleanFields();
		}
	}

	private Note createNote() {
		String title = titleField.getString();
		if(title.equals(""))
			return null;
		long dateInMilis = dateField.getDate() == null ? Calendar.getInstance().getTime().getTime() : dateField.getDate().getTime();
		return new Note(
				title, 
				contentField.getString(),
				dateInMilis,
				categoryField.getString(categoryField.getSelectedIndex()));
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
		categoryField = new ChoiceGroup("Category", Choice.EXCLUSIVE, new String[] {
			"Important",
			"Normal",
			"Archive"
		}, null);
		
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