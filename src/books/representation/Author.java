package books.representation;

public class Author {
	
	private int authorID;
	private String Name;
	
	public Author(int authorIn, String firstIn) {
		setAuthorID(authorIn);
		setName(firstIn);
		
	}
	public Author(String name) {
		setName(name);
		
	}
	
	public int getAuthorID() {return authorID;}
	public void setAuthorID(int authorID) {this.authorID = authorID;}
	public String getName() {return Name;}
	public void setName(String firstName) {this.Name = firstName;}
	
	
	
	public String toString() {
		
		return Name;
		
	}

}
