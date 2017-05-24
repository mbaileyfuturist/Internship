import java.sql.*;
public class MainDriver {

	public static void main(String[] args) {
		try{
			//1. Get connection to database.
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/practice_data_base", "root", "mikespasword123$");
			//2. Create a statement.
			Statement myStatement = myConnection.createStatement();
			//3. Execute SQL Query.
			ResultSet myRS = myStatement.executeQuery("select * from person;");
			//4. Process the result set.
			while(myRS.next()){
				System.out.println(myRS.getString("first_name") + " " + myRS.getString("last_name") + " " + myRS.getString("age"));
			}
		}
		
		catch(Exception e){
			e.printStackTrace();
		}

	}

}
