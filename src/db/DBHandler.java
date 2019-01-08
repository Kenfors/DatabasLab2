package db;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import books.representation.Book;
import books.representation.Review;


public interface DBHandler {

	/*Connect.
	 * Attempts to connect to the database
	 * 
	 * @param user your username for the database
	 * @param pwd your password for the database
	 * */
	public void connect(String user, String pwd);
	
	/*Disconnect
	 * Attempts to disconnect from the database
	 * 
	 * */
	public void disconnect();
	
	/* 
	 * Attempts to find books by the isbn keyword
	 * 
	 * @param Isbn the isbn number of the book to find
	 * @returns List of found books
	 * */
	public List<Book> getBooksfromIsbn(String Isbn) throws SQLException;
	public List<Book> getBooksfromTitle(String title) throws SQLException;
	public ArrayList<Book> getBooksfromAuthor(String name) throws SQLException;
	public ArrayList<Review> getReviewsfromIsbn(String name) throws SQLException;
	
	/*AddBook
	 * @param b the book-object to add
	 * */
	public void AddBook(Book b) throws SQLException;
	/*AddReview
	 * @param r The review-object to find
	 * */
	void AddReview(Review r) throws SQLException;

}
