package books.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import books.representation.Author;
import books.representation.Book;
import books.representation.Review;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Popup {

	
	
	public static String titleSearch() {
		TextInputDialog dialog = new TextInputDialog("titlename...");
		dialog.setTitle("Search...");
		dialog.setHeaderText("Search in book titles");
		dialog.setContentText("Search for:");


		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			return result.get();
		}
		else
			return "";
	}
	
	public static String IsbnSearch() {
		TextInputDialog dialog = new TextInputDialog("ISBN number...");
		dialog.setTitle("Search...");
		dialog.setHeaderText("Search by ISBN");
		dialog.setContentText("Search for:");


		Optional<String> result = dialog.showAndWait();
		return result.get();
	}
	
	public static String authorSearch() {
		TextInputDialog dialog = new TextInputDialog("author");
		dialog.setTitle("Search...");
		dialog.setHeaderText("Search in author names");
		dialog.setContentText("Search for:");


		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			return result.get();
		}
		else
			return "";
	}
	
	public static String addWhat() {
		List<String> choices = new ArrayList<>();
		choices.add("Author");
		choices.add("Review");
		choices.add("Book");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("Book", choices);
		dialog.setTitle("");
		dialog.setHeaderText("Add to database");
		dialog.setContentText("Entity: ");


		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			return result.get();
		}

		return "";
	}
	
	public static Book addBook() {
		Book newBook = new Book();
		Dialog<Book> d = new Dialog<>();
		
		d.setTitle("Add...");
		d.setHeaderText("Type fields for new book");
		GridPane fields = new GridPane();
		fields.setHgap(10);
		fields.setVgap(10);
		fields.setPadding(new Insets(0, 10, 0, 10));
		
		TextField BookTitle = new TextField();
		BookTitle.setPromptText("Title");
		fields.add(BookTitle, 0, 0);
		
		TextField BookIsbn = new TextField();
		BookIsbn.setPromptText("Isbn");
		fields.add(BookIsbn, 1, 0);

		TextField BookRelease = new TextField();
		BookRelease.setPromptText("Publishing Year");
		fields.add(BookRelease, 2, 0);	
		
		ListView<TextField> BookAuthors = new ListView<>();
		TextField a = new TextField();
		a.setPromptText("Author");
		a.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				TextField t = new TextField();
				t.setText(a.getText());
				t.setEditable(false);
				a.setText("");
				BookAuthors.getItems().add(t);
			}
			
		});
		BookAuthors.getItems().add(a);
		
		fields.add(BookAuthors, 3, 0);
		
		ListView<CheckBox> BookGenres = new ListView<>();
		BookGenres.getItems().add(new CheckBox("Horror"));
		BookGenres.getItems().add(new CheckBox("Drama"));
		BookGenres.getItems().add(new CheckBox("Fantasy"));
		BookGenres.getItems().add(new CheckBox("Thriller"));
		BookGenres.getItems().add(new CheckBox("Crime"));
		BookGenres.getItems().add(new CheckBox("Sci-Fi"));
		BookGenres.getItems().add(new CheckBox("Adventure"));
		
		fields.add(BookGenres, 4, 0);
		
		
		d.getDialogPane().setContent(fields);
		
		Button b = new Button();
		b.setText("Add");
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				newBook.setTitle(BookTitle.getText());
				newBook.setIsbn(BookIsbn.getText());
				newBook.setRelease(BookRelease.getText());
				ArrayList<Author> a = new ArrayList<>();
				for (int i = 1; i < BookAuthors.getItems().size(); i++) {
					a.add(new Author(BookAuthors.getItems().get(i).getText()));
				}
				newBook.setAuthors(a);
				
				ArrayList<String> g = new ArrayList<>();
				for(CheckBox box : BookGenres.getItems()) {
					if(box.isSelected()) {
						g.add(box.getText());
					}
				}
				newBook.setGenres(g);
				
				d.setResult(newBook);
				
			}});
		fields.add(b, 2, 1);
		
		Optional<Book> result = d.showAndWait();
		if (result.isPresent()){
			return result.get();
		}
		else
			return null;
	}

	public static Review reviewDialog() {
		Review newReview = new Review();
		Dialog<Review> d = new Dialog<>();
		
		d.setTitle("Add...");
		d.setHeaderText("Type fields for review");
		GridPane fields = new GridPane();
		fields.setHgap(10);
		fields.setVgap(10);
		fields.setPadding(new Insets(0, 10, 0, 10));
		
		
		TextField BookISBN = new TextField();
		BookISBN.setPromptText("Enter Isbn");
		fields.add(new Text("ISBN"), 0, 0);
		fields.add(BookISBN, 0, 1);
		
		Slider score = new Slider(0, 5, 1);
		score.setShowTickMarks(true);
		score.setShowTickLabels(true);
		score.setMajorTickUnit(1);
		score.setBlockIncrement(1);
		score.setSnapToTicks(true);
		score.valueProperty().addListener((obs, oldval, newVal) -> 
	    	score.setValue(newVal.intValue()));
		
		fields.add(new Text("Score 1-5"), 1, 0);
		fields.add(score, 1, 1);

		TextField message = new TextField();
		message.setPromptText("Enter text");
		fields.add(new Text("Description"), 2, 0);
		fields.add(message, 2, 1);
		
		Button b = new Button();
		b.setText("Add");
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				newReview.setMessage(message.getText());
				newReview.setScore((int) score.getValue());
				newReview.setISBN(BookISBN.getText());
				d.setResult(newReview);
				}
			});
		fields.add(b, 1, 2);
		
		d.getDialogPane().setContent(fields);
		
		Optional<Review> result = d.showAndWait();
		if (result.isPresent()){
			return result.get();
		}
		else
			return null;
	}
	
	
	
	
	public static String getStringDialog(String header, String description) {
		TextInputDialog dialog = new TextInputDialog("author");
		dialog.setHeaderText(header);
		dialog.setContentText(description);


		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			return result.get();
		}
		else
			return "";
	}
	
	
	public static void showReviews(List<Review> inReviews) {
		Alert d = new Alert(AlertType.INFORMATION);
		TableView<Review> table = new TableView<>();
		ObservableList<Review> reviews = FXCollections.observableArrayList();
		
		
		TableColumn<Review, String> col1 = new TableColumn<>("ISBN");
		TableColumn<Review, Integer> col2 = new TableColumn<>("Score");
		TableColumn<Review, String> col3 = new TableColumn<>("Message");
		
		table.setMinWidth(500);
		table.getColumns().addAll(col1, col2, col3);
		
		col1.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
		col2.setCellValueFactory(new PropertyValueFactory<>("score"));
		col3.setCellValueFactory(new PropertyValueFactory<>("message"));
		
		reviews.addAll(inReviews);
		table.setItems(reviews);
		d.getDialogPane().setContent(table);
		
		d.showAndWait();
		
		
	}
	
}


