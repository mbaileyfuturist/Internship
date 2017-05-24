
import java.sql.*;

public class Practice{
	public static void main(String[] args){
		String protocol = "jdbc:mysql://localhost:3306/practice_data_base";
		String username = "root";
		String password = "mikespasword123$";
		try{
			//Establish a connection to the SQL Database.
			Connection myConnection = DriverManager.getConnection(protocol, username, password);
			
			//Add Column.
			String update = "UPDATE books SET price = 13.99 WHERE id = 1";
			
			//select Statement.
			String statement = "select * from books";
			Statement myStatement = myConnection.createStatement();
			
			//update database.
			myStatement.executeUpdate(update);
			
			//Get Results.
			ResultSet results = myStatement.executeQuery(statement);
			System.out.println("\t\t\tBooks Database");
			System.out.println("-------------------------------------------------------------------");
			while(results.next()){
				System.out.println( results.getString("id") + " " + results.getString("title") + " " + results.getString("author")
				+ " "+ results.getString("publisher") + " " + results.getString("publication_date") + " " + results.getString("genre")
				+ " " + results.getString("copy") + " " + results.getString("price"));
			}
		}
		catch(Exception e){
			System.out.println("Error: " + e.getMessage());
		}
	}
}