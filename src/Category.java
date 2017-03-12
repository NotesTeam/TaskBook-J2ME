import java.io.IOException;

import javax.microedition.lcdui.Image;

// http://www.javaworld.com/article/2076970/core-java/create-enumerated-constants-in-java.html

public final class Category implements Comparable {

	private String id;
	public final int ord;
	private static int upperBound = 0;
	private Image icon;

	private Category(String id) {
		this.id = id;
		ord = upperBound++;
		icon = getCategoryImage(id);
	}

	public String toString() {return this.id;}
	public static int size() {return upperBound;}

	public static final Category WORK = new Category("Work");
	public static final Category FAMILY = new Category("Family");
	public static final Category SCHOOL = new Category("School");

	public static final String[] elements = {
			FAMILY.id,
			WORK.id,
			SCHOOL.id
	};

	public static Category toCategory(String c) {
		if (c.equals("Family"))
			return FAMILY;
		else if (c.equals("Work"))
			return WORK;
		else
			return SCHOOL;
	}

	public Image getIcon() {
		return icon;
	}

	public int compareTo(Object o) {
		Category other = (Category) o;
		if(other == FAMILY || (other == WORK && this == SCHOOL))
			return -1;
		else if (other == this)
			return 0;
		else
			return 1;
	}

	private Image getCategoryImage(String id) {
		Image image = null;
		String path;

		if(id.equals("Family"))
			path = "/family.png";
		else if(id.equals("Work"))
			path = "/work.png";
		else
			path = "/school.png";

		try {
			image = Image.createImage(path);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return image;
	}
}
