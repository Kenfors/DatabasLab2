package books.View;


import java.util.ArrayList;
import java.util.List;

import javax.swing.event.HyperlinkEvent.EventType;

import com.mysql.cj.util.StringUtils.SearchMode;


import books.BookApp;
import books.representation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PageView extends VBox{
	
	Scene scene;
	
    private TableView<Book> viewTable;
    private ObservableList<Book> booksInTable; // the data backing the table view

    private ComboBox<SearchMode> searchModeBox;
    private TextField searchField;
    private Button searchButton;

    private MenuBar menuBar;
    BorderPane mainPane;
	
    
	public PageView() {
		//TextField
		//ComboBox

	}
	
	
	public void start(PageController control) {
		
		control.setView(this);
        booksInTable = FXCollections.observableArrayList();

        
        initViewTable();
        initMenus(control);

        FlowPane bottomPane = new FlowPane();
        bottomPane.setHgap(10);
        bottomPane.setPadding(new Insets(10, 10, 10, 10));
        //bottomPane.getChildren().addAll(searchModeBox, searchField, searchButton);

        
        mainPane = new BorderPane();
        mainPane.setCenter(viewTable);
        mainPane.setBottom(bottomPane);
        mainPane.setPadding(new Insets(10, 10, 10, 10));        

        mainPane.setTop(menuBar);;
        this.getChildren().addAll(mainPane);
        
        VBox.setVgrow(mainPane, Priority.ALWAYS);

        
	}
	private void initMenus(PageController control) {

        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				javafx.application.Platform.exit();
			}        	
        });
        
        fileMenu.getItems().addAll(exitItem);

        Menu searchMenu = new Menu("Search");
        MenuItem titleItem = new MenuItem("Title");
        titleItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				control.onSearchBookTitle(Popup.titleSearch());
			}
        });
        
        MenuItem isbnItem = new MenuItem("ISBN");
        isbnItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				control.onSearchBookIsbn(Popup.IsbnSearch());
			}
        });
        MenuItem authorItem = new MenuItem("Author");
        authorItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				control.onSearchAuthor(Popup.authorSearch());
			}
        });
        MenuItem reviewItem = new MenuItem("Review");
        authorItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				control.onViewReviews(Popup.IsbnSearch());
			}
        });
        searchMenu.getItems().addAll(titleItem, isbnItem, authorItem, reviewItem);

        Menu manageMenu = new Menu("Manage");
        MenuItem addItem = new MenuItem("Add Book");
        addItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				control.addBook(Popup.addBook());
			}
        });
        MenuItem addItem2 = new MenuItem("Add Review");
        addItem2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				control.addReview(Popup.reviewDialog());
			}
        });
        
        manageMenu.getItems().addAll(addItem, addItem2);

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, searchMenu, manageMenu);
		
	}

	public void updateViewTable(List<Book> displayBooks) {
		
		if(displayBooks == null) {
			return;
		}
		booksInTable.clear();
		booksInTable.addAll(displayBooks);
		
	}
	
	public void showError(String errorMessage) {

		Alert a = new Alert(AlertType.ERROR);
		a.setContentText(errorMessage);
		a.setHeaderText("Error");
		a.setTitle("Oops...");
		a.showAndWait();
	}
	
	public void showReviews(List<Review> reviews) {
		Popup.showReviews(reviews);
	}
	
	private void initViewTable() {
		
		viewTable = new TableView<>();
        viewTable.setEditable(false); // don't allow user updates (yet)
        
        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
        TableColumn<Book, String> publishedCol = new TableColumn<>("Published");
        
        TableColumn<Book, String> authorCol = new TableColumn<>("Authors");
        TableColumn<Book, String> genreCol = new TableColumn<>("Genres");

        viewTable.getColumns().addAll(titleCol, isbnCol, publishedCol, authorCol, genreCol);
        
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        publishedCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("release"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("authors"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genres"));
        
        viewTable.setItems(booksInTable);
	
		
	}

}