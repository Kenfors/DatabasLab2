package db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.BsonBinaryWriter;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.Block;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import books.representation.Author;
import books.representation.Book;
import books.representation.Review;

public class MongoHandler implements DBHandler {

	
	MongoClient mongo;
	MongoDatabase db;
	
	@Override
	public void connect(String user, String pwd) {
		// TODO Auto-generated method stub
		
		
		mongo = MongoClients.create("mongodb://client:123@localhost/?authSource=admin");
		db = mongo.getDatabase("Lab2DB");
		for (String s : db.listCollectionNames()) {
			System.out.println("Collection: " + s);
		}
			
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Book> getBooksfromIsbn(String Isbn) throws SQLException {
		// TODO Auto-generated method stub
		
		MongoCollection<Document> books = db.getCollection("Books");

		Bson filter = Filters.regex("isbn", Pattern.quote(Isbn));
		ArrayList<Document> result = books.find(filter).into(new ArrayList<>());
		ArrayList<Book> bookResult = new ArrayList<>();
		
		for (Document doc : result) {
			bookResult.add(docToBook(doc));
		}
		
		for (Book bo : bookResult) {
			System.out.println("Found: " + bo);
		}
		
		return bookResult;
	}

	@Override
	public List<Book> getBooksfromTitle(String title) throws SQLException {
		// TODO Auto-generated method stub
		MongoCollection<Document> books = db.getCollection("Books");

		Bson filter = Filters.regex("title", Pattern.quote(title));
		ArrayList<Document> result = books.find(filter).into(new ArrayList<>());
		ArrayList<Book> bookResult = new ArrayList<>();
		
		for (Document doc : result) {
			bookResult.add(docToBook(doc));
		}
		
		for (Book bo : bookResult) {
			System.out.println("Found: " + bo);
		}
		
		return bookResult;
	}

	@Override
	public ArrayList<Book> getBooksfromAuthor(String name) throws SQLException {
		// TODO Auto-generated method stub
		MongoCollection<Document> books = db.getCollection("Books");

		MongoCollection<Document> authorCollection = db.getCollection("Authors");
		Bson filter = Filters.regex("name", Pattern.quote(name));
		ObjectId authID = new ObjectId(authorCollection.find(filter).first().get("_id").toString());
		
		System.out.println("AuthorID: " + authID);
		
		filter = Filters.eq("authors", authID);
		
		ArrayList<Document> result = books.find(filter).into(new ArrayList<>());
		ArrayList<Book> bookResult = new ArrayList<>();
		
		System.out.println("Books matching id:" + result);
		
		for (Document doc : result) {
			bookResult.add(docToBook(doc));
		}
		
		for (Book bo : bookResult) {
			System.out.println("Found: " + bo);
		}
		
		return bookResult;
	}

	@Override
	public void AddBook(Book b) throws SQLException {
		// TODO Auto-generated method stub

		MongoCollection<Document> dbBooks = db.getCollection("Books");
		dbBooks.insertOne(AsDocument(b));
		
		
	}

	@Override
	public void AddReview(Review r) throws SQLException {
		// TODO Auto-generated method stub

	}
	

	private Document AsDocument(Book book) {
		Document newDoc = new Document();
		MongoCollection<Document> dbAuthors = db.getCollection("Authors");
		newDoc.append("title", book.getTitle());
		newDoc.append("isbn", book.getIsbn());
		newDoc.append("date", book.getRelease());
			
		
		//Might need to add new author.
		for (Author a : book.getAuthors()) {
			Bson filter = Filters.eq("name", a.getName());
			FindIterable<Document> result = dbAuthors.find(filter);
			
			Document d = result.first();
			if(d == null) {
				
				dbAuthors.insertOne(new Document().append("name", a.getName()));
			}

		}
		
		//get ID for authors
		ArrayList<Object> authIDs = new ArrayList<>();
		for (Author a : book.getAuthors()) {
			Bson filter = Filters.eq("name", a.getName());
			FindIterable<Document> result = dbAuthors.find(filter);
			
			Document d = result.first();
			if(d.containsKey("_id")) {
				authIDs.add(d.get("_id"));
			}

		}
		//Append Author ID's
		newDoc.append("authors", authIDs);
		
		
		//Append Genres
		newDoc.append("genres", book.getGenresAsList());
		
		return newDoc;
	}
	
	
	private Document AsDocument(Review review) {
		Document newDoc = new Document();
		newDoc.append("isbn", review.getISBN());
		newDoc.append("score", review.getScore());
		newDoc.append("message", review.getMessage());
		
		
		return newDoc;
	}
	
	
	private Document AsDocument(Author author) {
		Document newDoc = new Document();
		newDoc.append("name", author.getName());
		
		return newDoc;
	}
	
	private Book docToBook(Document d) {
		Book b = new Book();
		
		b.setTitle(d.getString("title"));
		b.setIsbn(d.getString("isbn"));
		b.setRelease(d.getString("date"));

		@SuppressWarnings("unchecked")
		ArrayList<Object> authorID =  (ArrayList<Object>) d.get("authors");
		MongoCollection<Document> dbAuthors = db.getCollection("Authors");
		ArrayList<Author> authors = new ArrayList<>();
		for (Object o : authorID) {
			Bson filter = Filters.eq("_id", new ObjectId(o.toString()));
			Document authDoc = dbAuthors.find(filter).first();
			authors.add(docToAuthor(authDoc));
		}		
		b.setAuthors(authors);
		
		@SuppressWarnings("unchecked")
		ArrayList<String> genres = (ArrayList<String>) d.get("genres");
		b.setGenres(genres);
		
		
		return b;
	}
	
	private Author docToAuthor(Document d) {
		Author a = new Author("");
		a.setName(d.getString("name"));
		
		return a;
	}
	
	private Review docToReview(Document d) {
		//ISBN
		
		//Score
		
		//Message
		
		return null;
	}

	@Override
	public ArrayList<Review> getReviewsfromIsbn(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
