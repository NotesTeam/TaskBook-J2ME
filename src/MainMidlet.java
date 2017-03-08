import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MainMidlet extends MIDlet {

	public static Display display;
	private NotesList notesList;

	public MainMidlet() {
		display = Display.getDisplay(this);
		notesList = new NotesList();
	}

	protected void startApp() throws MIDletStateChangeException {
		System.out.println("Midlet: startApp()");
		display.setCurrent(notesList);
		notesList.inflateList();
	}

	protected void pauseApp() {

	}

	protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {

	}
	
	public static Display getDisplay() {
		return display;
	}
}
