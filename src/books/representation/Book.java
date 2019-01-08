package books.representation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Book {
	
	private String isbn;
	private String title;
	private String release;
	private ArrayList<String> genres;
	private ArrayList<Author> authors;
	private ArrayList<Review> reviews;
	
	public Book(String isbnIn, String titleIn, String releaseIn, ArrayList<String> genresIn) {
		setIsbn(isbnIn);
		setTitle(titleIn);
		setRelease(releaseIn);
		setGenres(genresIn);
		
	}
	
	public Book(String isbnIn, String titleIn, String releaseIn, ArrayList<String> genresIn, ArrayList<Author> authors) {
		setIsbn(isbnIn);
		setTitle(titleIn);
		setRelease(releaseIn);
		setGenres(genresIn);
		setAuthors(authors);
		
	}
	public Book(String isbnIn, String titleIn, String releaseIn) {
		setIsbn(isbnIn);
		setTitle(titleIn);
		setRelease(releaseIn);
		setGenres(null);
		setAuthors(null);
		
	}

	public Book() {
		// TODO Auto-generated constructor stub
	}

	public String getIsbn() {return isbn;}
	public void setIsbn(String isbn) {this.isbn = isbn;}
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	public String getRelease() {return release;}
	public void setRelease(String releaseIn) {this.release = releaseIn;}
	public String getGenres() {
		if (genres == null) return "";
		String g = "";
		for(String s : genres) {
			g += s + ", ";			
		}
		return g;
	}
	public ArrayList<String> getGenresAsList(){
		return this.genres;
	}
	
	public void setGenres(ArrayList<String> genres) {
		this.genres = genres;
	}
	public ArrayList<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(ArrayList<Author> authors) {
		this.authors = authors;
	}
	public ArrayList<Review> getReviews() {
		return reviews;
	}
	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}
	
	
	
	
}
