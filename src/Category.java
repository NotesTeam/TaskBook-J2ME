import java.io.IOException;

import javax.microedition.lcdui.Image;

// http://www.javaworld.com/article/2076970/core-java/create-enumerated-constants-in-java.html

public final class Category {

	private String id;
	public final int ord;
	private static int upperBound = 0;
	private Image icon;

	private Category(String anID) {
		this.id = anID;
		this.ord = upperBound++;
		try {
			if(anID.equals("Important"))
				this.icon = Image.createImage("/important.png");
			else if(anID.equals("Normal"))
				this.icon = Image.createImage("/normal.png");
			else
				this.icon = Image.createImage("/low.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String toString() {return this.id;}
	public static int size() {return upperBound;}

	public static final Category IMPORTANT = new Category("Important");
	public static final Category NORMAL = new Category("Normal");
	public static final Category LOW_PRIORITY = new Category("Low priority");

	public static final String[] elements = {
			IMPORTANT.id,
			NORMAL.id,
			LOW_PRIORITY.id
	};

	public static Category toCategory(String c) {
		if (c.equals("Important"))
			return IMPORTANT;
		else if (c.equals("Normal"))
			return NORMAL;
		else
			return LOW_PRIORITY;
	}

	public Image getIcon() {
		return icon;
	}

}
