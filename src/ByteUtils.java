import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ByteUtils {

	public static byte[] toByteArray(Note note) {

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);

		try {
			dout.writeUTF(note.getTitle());
			dout.writeUTF(note.getContent());
			dout.writeLong(note.getTimestamp());
			dout.writeUTF(note.getCategory());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return bout.toByteArray();
	}

	public static Note toNote(byte[] bytes) {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		DataInputStream dis = new DataInputStream(bais);

		String title = "";
		String content = "";
		long timestamp = 0;
		String category = "";

		try {
			title = dis.readUTF();
			content = dis.readUTF();
			timestamp = dis.readLong();
			category = dis.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new Note(title, content, timestamp, category);   
	}

}
