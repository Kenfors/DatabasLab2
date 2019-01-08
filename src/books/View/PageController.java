package books.View;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.cj.util.StringUtils.SearchMode;

import books.BookApp;
import books.representation.Author;
import books.representation.Book;
import books.representation.Review;
import db.DBHandler;
import db.SQLHandler;

public class PageController {
	

    private PageView view; // view
    private DBHandler db; // model
    

    /*Constructor
     * Connects the database
     * 
     * @param database interface for database
     * @param gui the view-class for
     * */
    public PageController(DBHandler database, PageView gui) {
    	db = database;
    	view = gui;
    	db.connect("client", "123");
    }
    
    /*CloseDb
     * Closes the database
     * 
     * 
     * */
    public void closeDB() {
    	db.disconnect();
    }
    
    /* setModel
     * Deprecated?
     * 
     * @param currentModel
     * */
    

    public void setView(PageView currentView) {	
    	this.view = currentView;
    	
    }
    
    
    /* 
     * Link between model and view
     * for selecting "search"
     * 
     * @param isbn the searchstring representing isbn-number
     * 
     * */
    public void onSearchBookIsbn(String isbn) {
    	new Thread(new Runnable() {
			@Override
			public void run() {
				ArrayList<Book> selection;
				try {
					selection = (ArrayList<Book>) db.getBooksfromIsbn(isbn);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					javafx.application.Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							view.showError(e.toString());
						}});
					return;
				}
				javafx.application.Platform.runLater(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						view.updateViewTable(selection);
					}});
			}
    		
    	}).start();
    }
    /* 
     * Link between model and view
     * for selecting "search"
     * 
     * @param title the searchstring representing title-number
     * 
     * */
    public void onSearchBookTitle(String title) {
    	new Thread(new Runnable() {
			@Override
			public void run() {
				ArrayList<Book> selection;
				try {
					selection = (ArrayList<Book>) db.getBooksfromTitle(title);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					javafx.application.Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							view.showError(e.toString());
						}});
					
					return;
				}
				javafx.application.Platform.runLater(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						view.updateViewTable(selection);
					}});
			}
    		
    	}).start();
    }
    /* 
     * Link between model and view
     * for selecting "search"
     * 
     * @param keyword the searchstring representing keyword-number
     * 
     * */
    public void onSearchAuthor(String keyword) {
    	new Thread(new Runnable() {
			@Override
			public void run() {
				ArrayList<Book> selection;
				try {
					selection = (ArrayList<Book>) db.getBooksfromAuthor(keyword);							
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					javafx.application.Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							view.showError(e.toString());
						}});
					return;
				}
				javafx.application.Platform.runLater(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						view.updateViewTable(selection);
					}});
			}
    		
    	}).start();
    }
    
    /* 
     * Link between model and view
     * for selecting "view reviews"
     * 
     * @param isbn the searchstring representing isbn-number
     * 
     * */
    public void onViewReviews(String isbn) {
    	
    	new Thread(new Runnable() {
			@Override
			public void run() {
				ArrayList<Review> selection;
				try {
					selection = (ArrayList<Review>) db.getReviewsfromIsbn(isbn);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					javafx.application.Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							view.showError(e.toString());
						}});
					return;
				}
				javafx.application.Platform.runLater(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						view.showReviews(selection);
					}});
			}
    		
    	}).start();
    	
    }
    /* 
     * Link between model and view
     * for selecting "Add Book"
     * 
     * @param bookToAdd the book-object to add
     * 
     * */
	public void addBook(Book bookToAdd) {
		
		if(bookToAdd == null) {
    		view.showError("Input error");
    		return;
    	}	
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					db.AddBook(bookToAdd);
				}
				catch (SQLException e) {
					javafx.application.Platform.runLater(new Runnable() {
						@Override
						public void run() {
							view.showError(e.toString());
						}});
					return;
				}
				finally{
					onSearchBookTitle("");
					
				}
			}	
		}).start();
	}
	/* 
     * Link between model and view
     * for selecting "Add Book"
     * 
     * @param r the review-object to add
     * 
     * */
	public void addReview(Review r) {
		if(r == null) {
    		view.showError("Input error");
    		return;
    	}
    	
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					db.AddReview(r);
				}
				catch (SQLException e) {
					javafx.application.Platform.runLater(new Runnable() {
						@Override
						public void run() {
							view.showError(e.toString());
						}});
					return;
				}
				finally{
					onSearchBookTitle("");					
				}
			}	
		}).start();
	}

}