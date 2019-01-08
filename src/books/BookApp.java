package books;



import db.DBHandler;
import db.MongoHandler;
import db.SQLHandler;
import books.View.PageView;
import books.View.Popup;
import books.representation.Author;
import books.representation.Book;
import books.representation.Review;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import books.View.PageController;
import javafx.application.Application;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class BookApp extends Application {
	
	

	private PageController controller;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch();
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		

		PageView gui = new PageView();
    	//controller = new PageController(new SQLHandler(), gui);
    	controller = new PageController(new MongoHandler(), gui);
    	gui.start(controller);
    	
        Scene scene = new Scene(gui, 800, 600);

        primaryStage.setTitle("Books Database Client");
        // add an exit handler to the stage (X) ?
        primaryStage.setOnCloseRequest(event -> {
            try {
            	controller.closeDB();
            } catch (Exception e) {}
        });
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
    	
        //controller.onSearchBookIsbn("9876345");
		controller.onSearchBookTitle("");
		
		
		Review r = new Review();
		r.setISBN("1111111");
		r.setScore(4);
		r.setMessage("TestMessage");
		Review r2 = new Review();
		r2.setISBN("1111111");
		r2.setScore(4);
		r2.setMessage("TestMessage");
		ArrayList<Review> ttt = new ArrayList<>();
		ttt.add(r2);
		ttt.add(r);
		
		Popup.showReviews(ttt);
	
	}

}
