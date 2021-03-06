import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DBLab extends Application {
  private Button btView = new Button("View");
  private Button btInsert = new Button("Insert");
  private Button btUpdate = new Button("Update");
  private Button btClear = new Button("Clear");
  private TextField tfID = new TextField();
  private TextField tfLastName = new TextField();
  private TextField tfFirstName = new TextField();
 
 
  private TextField tfCity = new TextField();
  private TextField tfState = new TextField();
  private TextField tfTelephone = new TextField();
  private Label lblStatus = new Label();
  
  // The Statement for processing queries
  private Statement stmt;

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    VBox vBox = new VBox(5);
    
    HBox hBox1 = new HBox(5);
    hBox1.getChildren().addAll(new Label("ID"), tfID);
    HBox hBox2 = new HBox(5);
    hBox2.getChildren().addAll(new Label("Last Name"), tfLastName,
      new Label("First Name"), tfFirstName);
    tfLastName.setPrefColumnCount(8);
    tfFirstName.setPrefColumnCount(8);
   
    
    HBox hBox4 = new HBox(5);
    hBox4.getChildren().addAll(new Label("City"), tfCity,
      new Label("State"), tfState);   
    HBox hBox5 = new HBox(5);
    hBox5.getChildren().addAll(new Label("Telephone"), tfTelephone);
 
    vBox.getChildren().addAll(hBox1, hBox2, hBox4, hBox5);
    
    HBox hBox = new HBox(5);
    hBox.getChildren().addAll(btView, btInsert, btUpdate, btClear);
    hBox.setAlignment(Pos.CENTER);
    
    BorderPane pane = new BorderPane();
    pane.setCenter(vBox);
    pane.setTop(lblStatus);
    pane.setBottom(hBox);
    
    // Create a scene and place it in the stage
    Scene scene = new Scene(pane, 400, 200);
    primaryStage.setTitle("DB Lab"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage

    initializeDB();
    
    btView.setOnAction(e -> view());
    btInsert.setOnAction(e -> insert());
    btUpdate.setOnAction(e -> update());
    btClear.setOnAction(e -> clear());    
  }
   
  private void initializeDB() {
    try {
    	 Class.forName("com.mysql.jdbc.Driver");

      // Connect to the local InterBase database
      Connection conn = DriverManager.getConnection      
        ("jdbc:mysql://localhost/project", "root", "");

      System.out.println("Database connected\n");

      lblStatus.setText("Database connected");

      // Create a statement
      stmt = conn.createStatement();
    }
    catch (Exception ex) {
      lblStatus.setText("Connection failed: " + ex);
    }
  }
    
    /**View record by ID*/
  private void view() {
    // Build a SQL SELECT statement
    String query = "SELECT * FROM Staff WHERE ID = "
      + "'" + tfID.getText().trim() + "'";

    try {
      // Execute query
  
		ResultSet rs= stmt.executeQuery(query);
		
		 // Test if record found and populate screen
		if(rs.next()){
			tfLastName.setText(rs.getString(2));
			tfFirstName.setText(rs.getString(2));
			tfCity.setText(rs.getString(4));
			tfState.setText(rs.getString(5));
			tfTelephone.setText(rs.getString(6));
		}
		else{
			clear();
			lblStatus.setText("Record not found");
			
		}
    }
	catch (SQLException ex) {
		lblStatus.setText("Select failed" + ex);
	}}
      
	 
     
  
 

  /**Insert a new record*/
  private void insert() {
    // Build a SQL INSERT statement
	  String insertStmt=
	  "INSERT INTO STAFF(ID, LastName, FistName,City, " + "State, Telephone) VALUES('"+
			  tfID.getText().trim()+"','"+
			  tfLastName.getText().trim()+"','"+
			  tfFirstName.getText().trim()+"','"+
			  tfCity.getText().trim()+"','"+
			  tfState.getText().trim()+"','"+
			  tfTelephone.getText().trim()+"');";

	  try {
		stmt.executeQuery(insertStmt);
		lblStatus.setText("Record inserted");

	} 
	  catch (SQLException ex) {
		lblStatus.setText("insert failed" +ex);
	}

			  
    
  }

  /**Update a record*/
  private void update() {
    // Build a SQL UPDATE statement
    String updateStmt = "UPDATE Staff " +
      "SET LastName = '" + tfLastName.getText().trim() + "'," +
      "FirstName = '" + tfFirstName.getText().trim() + "'," +        
      "City = '" + tfCity.getText().trim() + "'," +
      "State = '" + tfState.getText().trim() + "'," +
      "Telephone = '" + tfTelephone.getText().trim() + "' " +
      "WHERE ID = '" + tfID.getText().trim() + "'";

    try {
      stmt.executeUpdate(updateStmt);
      lblStatus.setText("Record updated");
    }
    catch(SQLException ex) {
      lblStatus.setText("Update failed: " + ex);
    }
  }

  /**Clear text fields*/
  private void clear() {
	  tfLastName.clear();
	  tfFirstName.clear();
	  tfCity.clear();
	  tfState.clear();
	  tfTelephone.clear();
  }


  /**
   * The main method is only needed for the IDE with limited
   * avaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}