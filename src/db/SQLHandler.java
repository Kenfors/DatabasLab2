package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import books.representation.Author;
import books.representation.Book;
import books.representation.Review;

public class SQLHandler implements DBHandler {

	private static Connection conn;
	
	
	@Override
	public void connect(String user, String pwd) {
		String database = "laboration1"; // the name of the specific database 
        String server
                = "jdbc:mysql://localhost/"
                		+ database
                		+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(server, user, pwd);
            System.out.println("Connected!");
        } 
		catch (Exception e) {
            System.out.println("Database error, " + e.toString());
        }
	}

	@Override
	public void disconnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
@Override
	public List<Book> getBooksfromIsbn(String isbn) throws SQLException {
		ArrayList<Book> result = new ArrayList<>();
		ResultSet queryResult;
		
		PreparedStatement query = conn.prepareStatement("select * from t_book where isbn = ?;");
		query.setString(1, isbn);
		queryResult = query.executeQuery();
		result = getBooksFromSet(queryResult);
		
		for(Book b : result) {
			query = conn.prepareStatement("select namn, Isbn "
										+ "from (t_author inner join rt_writing on t_author.authorID = rt_writing.authorID) "
										+ "where isbn = ?;");
			query.setString(1, isbn);
			queryResult = query.executeQuery();
			b.setAuthors(getAuthorsFromSet(queryResult));
		}
		
		for(Book b : result) {
			query = conn.prepareStatement("select typename, Isbn "
										+ "from (t_genre inner join rt_bookgenres on t_genre.TypeID = rt_bookgenres.TypeID) "
										+ "where isbn = ?;");
			query.setString(1, isbn);
			queryResult = query.executeQuery();
			b.setGenres(getGenresFromSet(queryResult));
		}
		
		return result;
	}
@Override
	public List<Book> getBooksfromTitle(String title) throws SQLException {
	ArrayList<Book> result = new ArrayList<>();
	ResultSet queryResult;
	
	PreparedStatement query = conn.prepareStatement("select * from t_book where position( ? IN t_book.Title) > 0;");
	query.setString(1, title);
	queryResult = query.executeQuery();
	result = getBooksFromSet(queryResult);
	
	for(Book b : result) {
		query = conn.prepareStatement("select namn, Isbn "
									+ "from (t_author inner join rt_writing on t_author.authorID = rt_writing.authorID) "
									+ "where isbn = ?;");
		query.setString(1, b.getIsbn());
		queryResult = query.executeQuery();
		b.setAuthors(getAuthorsFromSet(queryResult));
	}
	
	for(Book b : result) {
		query = conn.prepareStatement("select typename, Isbn "
									+ "from (t_genre inner join rt_bookgenres on t_genre.TypeID = rt_bookgenres.TypeID) "
									+ "where isbn = ?;");
		query.setString(1, b.getIsbn());
		queryResult = query.executeQuery();
		b.setGenres(getGenresFromSet(queryResult));
	}
	
	return result;
	}
	@Override
	public ArrayList<Book> getBooksfromAuthor(String name) throws SQLException {
		ArrayList<Book> result = new ArrayList<>();
		ResultSet queryResult;
		
		PreparedStatement query = conn.prepareStatement("select t_book.Isbn, ReleaseYr, Title from "
													+ "(t_author inner join rt_writing on t_author.authorID = rt_writing.authorID) "
													+ "inner join t_book on rt_writing.Isbn = t_book.Isbn "
													+ "where position( ? IN namn) > 0;");
		query.setString(1, name);
		queryResult = query.executeQuery();
		result = getBooksFromSet(queryResult);
		
		for(Book b : result) {
			query = conn.prepareStatement("select namn, Isbn "
										+ "from (t_author inner join rt_writing on t_author.authorID = rt_writing.authorID) "
										+ "where isbn = ?;");
			query.setString(1, b.getIsbn());
			queryResult = query.executeQuery();
			b.setAuthors(getAuthorsFromSet(queryResult));
		}
		
		for(Book b : result) {
			query = conn.prepareStatement("select typename, Isbn "
										+ "from (t_genre inner join rt_bookgenres on t_genre.TypeID = rt_bookgenres.TypeID) "
										+ "where isbn = ?;");
			query.setString(1, b.getIsbn());
			queryResult = query.executeQuery();
			b.setGenres(getGenresFromSet(queryResult));
		}
		
		return result;
	}

	private ArrayList<Book> getBooksFromSet(ResultSet set) throws SQLException{
		ArrayList<Book> result = new ArrayList<>();
		
		if(!set.first()) return result;
		
		String Title = "";
		String Isbn = "";
		String ReleaseYr = "";
		
		do{
			
			Isbn = set.getString(1);
			Title = set.getString(2);
			ReleaseYr = set.getString(3);
			Book newBook = new Book(Isbn, ReleaseYr, Title, new ArrayList<String>(), new ArrayList<Author>());
			result.add(newBook);
			
		}while(set.next());
		
		System.out.println("Book result:");
		System.out.println(result);
		
		return result;
	}
	private ArrayList<Author> getAuthorsFromSet(ResultSet set) throws SQLException{
		ArrayList<Author> result = new ArrayList<>();
		
		if(!set.first()) return result;
		
		String authorName = "";
		do{
			authorName = set.getString(1);
			result.add(new Author(authorName));
			
		}while(set.next());
		
		return result;
	}
	private ArrayList<String> getGenresFromSet(ResultSet set) throws SQLException{

		ArrayList<String> result = new ArrayList<>();
		
		if(!set.first()) return result;
		
		String genre = "";
		do{
			genre = set.getString(1);
			result.add(genre);
			
		}while(set.next());
		
		return result;
	}

	private ArrayList<Review> getReviewsFromSet(ResultSet set) throws SQLException{
		ArrayList<Review> result = new ArrayList<>();
		
		if(!set.first()) return result;
		
		String isbn = "";
		int score;
		String message = "";
		do{
			isbn = set.getString(1);
			score = set.getInt(2);
			message = set.getString(3);
			
			result.add(new Review(isbn, message, score));
			
		}while(set.next());
		
		return result;
	}
	
	@Override
	public void AddBook(Book b) throws SQLException {
		// TODO Auto-generated method stub
		
		PreparedStatement addBook = conn.prepareStatement("insert into T_book(Isbn, title, releaseYr) values(?,?,?);");
		PreparedStatement addAuthor = conn.prepareStatement("insert into T_Author(namn) values (?);");
		PreparedStatement authorRelation = conn.prepareStatement("insert into rt_writing(authorID, Isbn) "
				+ "values ((select authorID from t_author where namn LIKE ?), ?);");
		PreparedStatement genreRelation = conn.prepareStatement("insert into rt_bookgenres(typeID, Isbn) "
				+ "values ((select typeID from t_genre where TypeName LIKE ?), ?);");
		PreparedStatement findAuthor = conn.prepareStatement("select authorID from t_author where namn LIKE ?");
		try {
			//Auto Commit off.
			conn.setAutoCommit(false);
			
			//Add Book
			addBook.setString(2, b.getTitle());
			addBook.setString(1, b.getIsbn());
			addBook.setString(3, b.getRelease());
			addBook.executeUpdate();
			addBook.close();
			
			//Add Author
			
			for (Author a : b.getAuthors()) {
				addAuthor.setString(1, a.getName());
				findAuthor.setString(1, a.getName());
				ResultSet authors = findAuthor.executeQuery();
				
				if(!authors.last()) {
					addAuthor.executeUpdate();					
				}
				
				//Add Author Relation
				authorRelation.setString(1, a.getName());
				authorRelation.setString(2, b.getIsbn());
				authorRelation.executeUpdate();
			}
			addAuthor.close();
			authorRelation.close();
			//Add Genre Relation
			for (String genre : b.getGenresAsList()) {
				genreRelation.setString(1, genre);
				genreRelation.setString(2, b.getIsbn());
				genreRelation.executeUpdate();
			}
			genreRelation.close();
		}
		catch (SQLException e){
			System.err.println("Rollback");
			conn.rollback();
			throw e;
		}
		finally {
			//Auto Commit on.
			conn.setAutoCommit(true);
		}
		
	}

	@Override
	public void AddReview(Review r) throws SQLException{
		
		PreparedStatement reviewInsert = conn.prepareStatement("insert into T_Review(Score, Message, Isbn) "
				+ "values (?, ?, ?);");
		reviewInsert.setString(1, "" + r.getScore());
		reviewInsert.setString(2, r.getMessage());
		reviewInsert.setString(3, r.getISBN());
		
		try {
			reviewInsert.executeUpdate();
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			reviewInsert.close();
		}
		
	}

	@Override
	public ArrayList<Review> getReviewsfromIsbn(String isbn) throws SQLException {
		// TODO Auto-generated method stub
		

		PreparedStatement query = conn.prepareStatement("select * from T_Review where isbn = ?;");
		query.setString(1, isbn);
		
		ResultSet queryResult = query.executeQuery();
		ArrayList<Review> reviews = this.getReviewsFromSet(queryResult);
		
		return reviews;
	}
	

}
