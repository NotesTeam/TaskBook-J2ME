import java.io.UnsupportedEncodingException;
import java.util.Vector;

import javax.microedition.rms.RecordComparator;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;

public class RecordStoreManager {

	private final RecordComparator categoryComparator = new CategoryComparator();
	private final RecordComparator timeComparator = new TimeComparator();
	private final RecordComparator priorityComparator = new PriorityComparator();

	private RecordComparator recordComparator;
	private RecordStore recordStore;
	private RecordEnumeration iterator;

	private static RecordStoreManager instance = null;

	public static RecordStoreManager getInstance() {
		if(instance==null)
			instance = new RecordStoreManager();
		return instance;
	}

	private RecordStoreManager() {
		recordStore = getRecordStore();
		recordComparator = timeComparator;
	}

	public boolean saveRecord(byte[] record) {
		try {
			recordStore.addRecord(record, 0, record.length);
			return true;
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Vector getRecords() {
		Vector vector = new Vector();
		try {
			iterator = recordStore.enumerateRecords(null, recordComparator, false);
			while (iterator.hasNextElement()) {
				byte[] record = iterator.nextRecord();
				vector.addElement(ByteUtils.toNote(record));
			}
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
		return vector;
	}

	public String getRecord(int position) {
		try {
			return new String(recordStore.getRecord(position), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void sortByCategory() {
		recordComparator = categoryComparator;
	}
	
	public void sortByTime(){
		recordComparator = timeComparator;
	}
	
	public void sortByPriority() {
		recordComparator = priorityComparator;
	}
	
	public void closeRecordStore(){
		try {
			recordStore.closeRecordStore();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
	}

	private RecordStore getRecordStore() {
		try {
			return RecordStore.openRecordStore("MainRecordStore", true);
		} catch (RecordStoreFullException e) {
			e.printStackTrace();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private class CategoryComparator implements RecordComparator {
		public int compare(byte[] rec1, byte[] rec2) {
			Note note1 = ByteUtils.toNote(rec1);
			Note note2 = ByteUtils.toNote(rec2);
			int result = note2.getCategory().compareTo(note1.getCategory());

			if (result == 0)
				return RecordComparator.EQUIVALENT;
			else if (result < 0)
				return RecordComparator.PRECEDES;
			else
				return RecordComparator.FOLLOWS;
		}
	}

	private class TimeComparator implements RecordComparator {
		public int compare(byte[] rec1, byte[] rec2) {
			Note note1 = ByteUtils.toNote(rec1);
			Note note2 = ByteUtils.toNote(rec2);
			int result = (int) (note2.getTimestamp()/1000 - note1.getTimestamp()/1000);

			if (result == 0)
				return RecordComparator.EQUIVALENT;
			else if (result < 0)
				return RecordComparator.PRECEDES;
			else
				return RecordComparator.FOLLOWS;
		}
	}

	private class PriorityComparator implements RecordComparator {
		public int compare(byte[] rec1, byte[] rec2) {
			Note note1 = ByteUtils.toNote(rec1);
			Note note2 = ByteUtils.toNote(rec2);

			int result = note2.getPriority() - note1.getPriority();

			if (result == 0)
				return RecordComparator.EQUIVALENT;
			else if (result < 0)
				return RecordComparator.PRECEDES;
			else
				return RecordComparator.FOLLOWS;
		}
	}
}
