import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

public class ShowNoteForm extends Form implements CommandListener {

	private Command back;
	private Displayable backDisplay;
	private Display display;
	private Note note;
	
	private StringItem titleItem;
	private StringItem contentItem;
	private StringItem dateItem;
	private StringItem categoryItem;
	
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
		if(c == back) {
			System.out.println("Command clicked: Back");
			display.setCurrent(backDisplay);
		}
	}

	private void setupFields() {
		titleItem = new StringItem("Title", null);
		titleItem.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_LARGE));
		contentItem = new StringItem("Content", null);
		dateItem = new StringItem("Date", null);
		categoryItem = new StringItem("Category", null);
		this.append(titleItem);
		this.append(contentItem);
		this.append(dateItem);
		this.append(categoryItem);
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
		categoryItem.setText(note.getCategory().toString());
	}
}
