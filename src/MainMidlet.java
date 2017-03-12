import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MainMidlet extends MIDlet implements MainOperations {

	public static Display display;
	private NotesList notesList;

	public MainMidlet() {
		display = Display.getDisplay(this);
		notesList = new NotesList(this);
	}

	protected void startApp() throws MIDletStateChangeException {
		System.out.println("Midlet: startApp()");
		display.setCurrent(notesList);
		notesList.inflateList();
	}

	protected void pauseApp() {

	}
	
	public static Display getDisplay() {
		return display;
	}
	
	public void exitApplication(boolean unconditional) {
		try {
			destroyApp(unconditional);
		} catch (MIDletStateChangeException e) {
			e.printStackTrace();
		}
		notifyDestroyed();
	}

	protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
		RecordStoreManager.getInstance().closeRecordStore();
	}
}
