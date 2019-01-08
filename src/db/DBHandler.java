package db;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import books.representation.Book;
import books.representation.Review;


public interface DBHandler {

	
	public void connect(String user, String pwd);
	public void disconnect();
	
	public List<Book> getBooksfromIsbn(String Isbn) throws SQLException;
	public List<Book> getBooksfromTitle(String title) throws SQLException;
	public ArrayList<Book> getBooksfromAuthor(String name) throws SQLException;
	public ArrayList<Review> getReviewsfromIsbn(String name) throws SQLException;
	
	public void AddBook(Book b) throws SQLException;
	void AddReview(Review r) throws SQLException;

	
	
	
	
}
