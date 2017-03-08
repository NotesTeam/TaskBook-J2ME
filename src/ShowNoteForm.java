import java.util.Calendar;
import java.util.Date;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

public class ShowNoteForm extends Form implements CommandListener {

	private Displayable backDisplay;
	private Command back;
	private Display display;
	private Note note;
	
	private StringItem titleItem;
	private StringItem contentItem;
	private StringItem dateItem;
	private StringItem categoryItem;

	
	public ShowNoteForm(Displayable backDisplay, Note note) {
		super("Note informations");
		this.backDisplay = backDisplay;
		back = new Command("Back", Command.BACK, 0);
		this.addCommand(back);
		this.setCommandListener(this);
		this.note = note;
		display = MainMidlet.getDisplay();
		titleItem = new StringItem("Title", null);
		contentItem = new StringItem("Content", null);
		dateItem = new StringItem("Date", null);
		categoryItem = new StringItem("Category", null);
		
		this.append(titleItem);
		this.append(contentItem);
		this.append(dateItem);
		this.append(categoryItem);
		
		inflateView();
	}

	private void inflateView() {
		titleItem.setText(note.getTitle());
		contentItem.setText(note.getContent());
		dateItem.setText(getReadableDate(note.getTimestamp()));
		categoryItem.setText(note.getCategory());
	}

	private String getReadableDate(long timestamp) {
		
		Date date = new Date(timestamp);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		String dateString = 
				String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))+"/"
				+String.valueOf(calendar.get(Calendar.MONTH)) +"/"
				+String.valueOf(calendar.get(Calendar.YEAR));
		
		return dateString;
	}

	public void commandAction(Command c, Displayable d) {
		if(c == back) {
			System.out.println("Command clicked: Back");
			display.setCurrent(backDisplay);
		}
	}

}
