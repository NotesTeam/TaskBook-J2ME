import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.StringItem;

public class ShowNoteForm extends Form implements CommandListener {

	private Command back;
	private Displayable backDisplay;
	private Display display;
	private Note note;
	
	private ImageItem imageItem;
	private StringItem titleItem;
	private StringItem contentItem;
	private StringItem dateItem;
	private StringItem priorityItem;
	
	public ShowNoteForm(Displayable backDisplay, Note note) {
		super("Note informations");
		display = MainMidlet.getDisplay();
		this.backDisplay = backDisplay;
		this.note = note;
		setupCommands();
		setupFields();
		inflateView();
	}
	
	public void commandAction(Command c, Displayable d) {
		if(c == back)
			display.setCurrent(backDisplay);
	}

	private void setupFields() {
		imageItem = new ImageItem("Category: " + note.getCategory().toString(), note.getCategory().getIcon(), 0, "ctgimg");
		titleItem = new StringItem("Title", null);
		titleItem.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
		contentItem = new StringItem("Content", null);
		dateItem = new StringItem("Date", null);
		priorityItem = new StringItem("Priority", null);
		this.append(imageItem);
		this.append(titleItem);
		this.append(contentItem);
		this.append(dateItem);
		this.append(priorityItem);
	}

	private void setupCommands() {
		back = new Command("Back", Command.BACK, 0);
		this.addCommand(back);
		this.setCommandListener(this);
	}

	private void inflateView() {
		titleItem.setText(note.getTitle());
		contentItem.setText(note.getContent());
		dateItem.setText(Utils.getReadableDate(note.getTimestamp()));
		priorityItem.setText(String.valueOf(note.getPriority()));
	}
}
