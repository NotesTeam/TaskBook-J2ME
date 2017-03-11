import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.json.me.JSONException;
import org.json.me.JSONObject;


public class ByteUtils {
	
	public static byte[] toByteArray(Note note) {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);
		
		JSONObject jsonNote = parseToJson(note);
		
		writeToStream(dout, jsonNote);
		return bout.toByteArray();
	}

	public static Note toNote(byte[] bytes) {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		DataInputStream dis = new DataInputStream(bais);
		
		String noteFromStream = readFromStream(dis);
		JSONObject jsonNote = readJson(noteFromStream);
		Note note = parseJsonToNote(jsonNote);
		
		return note;
	}

	private static Note parseJsonToNote(JSONObject jsonNote) {
		Note note = new Note(
				jsonNote.optString(Note.TITLE),
				jsonNote.optString(Note.CONTENT),
				jsonNote.optLong(Note.TIMESTAMP),
				Category.toCategory(jsonNote.optString(Note.CATEGORY))
				);
		return note;
	}

	private static JSONObject readJson(String noteFromStream) {
		JSONObject jsonNote = null;
		
		try {
			jsonNote = new JSONObject(noteFromStream);
					} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return jsonNote;
	}

	private static String readFromStream(DataInputStream dis) {
		String noteFromStream = null;
		try {
			noteFromStream = dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return noteFromStream;
	}
	
	private static void writeToStream(DataOutputStream dout, JSONObject jsonNote) {
		try {
			dout.writeUTF(jsonNote.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static JSONObject parseToJson(Note note) {
		JSONObject jsonNote = new JSONObject();
		try {
			jsonNote.put(Note.TITLE, note.getTitle());
			jsonNote.put(Note.CONTENT, note.getContent());
			jsonNote.put(Note.TIMESTAMP, note.getTimestamp());
			jsonNote.put(Note.CATEGORY, note.getCategory());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return jsonNote;
	}
}
