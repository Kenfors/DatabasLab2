package books.representation;

public class Review{
	private String ISBN;
	private String message;
	private int score;

	public Review(String isbn, String msg, int scoreIn) {
		setISBN(isbn);
		setMessage(msg);
		setScore(scoreIn);
		
	}
	public Review(String msg, int scoreIn) {
		setMessage(msg);
		setScore(scoreIn);
		
	}
	public Review() {
		message = null;
		score = 0;
	}
	
	public String getMessage() {return message;}
	public void setMessage(String message) {this.message = message;}
	public int getScore() {return score;}
	public void setScore(int score) {this.score = score;}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	
	
}