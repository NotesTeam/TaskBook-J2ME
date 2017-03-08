import java.io.UnsupportedEncodingException;
import java.util.Vector;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;

public class RecordStoreManager {
	
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
			iterator = recordStore.enumerateRecords(null, null, false);
			while (iterator.hasNextElement()) {
				byte[] record = iterator.nextRecord();
				vector.addElement(ByteUtils.toNote(record));
			}
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
		System.out.println("getRecords size: " + vector.size());
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
	
//	private boolean isRecordStoreEmpty(){
//		try {
//			if(recordStore.getNumRecords() == 0) {
//				System.out.println("Record store is empty");
//				return true;
//			} else {
//				System.out.println("Record store is not empty");
//				System.out.println("Size of RecordStore: " + recordStore.getNumRecords());
//				return false;
//			}
//		} catch (RecordStoreNotOpenException e) {
//			e.printStackTrace();
//		}
//		return true;
//	}
}
