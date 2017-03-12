
public class Note {
	
	public static String TITLE = "title";
	public static String CONTENT = "content";
	public static String TIMESTAMP = "timestamp";
	public static String PRIORITY = "priority";
	public static String CATEGORY = "category";
	
	private String title;
	private String content;
	private long timestamp;
	private int priority;
	private Category category;
	
	public Note(String title, String content, long timestamp, int priority, Category category) {
		super();
		this.title = title;
		this.content = content;
		this.timestamp = timestamp;
		this.priority = priority;
		this.category = category;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public int getPriority(){
		return priority;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public String toString(){
		return title + " " + " " + String.valueOf(priority) + " "+ content + " " + category + "\n";
	}
	
	public boolean equals(Object other){
		if(other instanceof Note) {
			Note otherNote = (Note) other;
			return title.equals(otherNote.getTitle()) 
					&& content.equals(otherNote.getContent())
					&& timestamp == otherNote.getTimestamp()
					&& category.equals(otherNote.getCategory());
		} else
			return false;
	}

}
