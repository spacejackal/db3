
import java.sql.*;
public class Query {
	private static Connection connect = null;

	public static void main(String[] args) 
	{

		try {
			//Set up connection parameters
			String userName = "root";
			String password = "password";
			String dbServer = "jdbc:mysql://localhost:3306/project1";
			//Set up connection
			connect = DriverManager.getConnection(dbServer,userName,password);
		} catch(Exception e) {

		}
		//initiate sql statement
		Statement stmt = null;
		
		try {
			//To execute a SELECT query, call the executeQuery(String) method with the SQL to use
			String query_student = "select ssn, name, gender from students;";
					
			
			ResultSet rs = stmt.executeQuery(query_student);
        	System.out.println("result for query");	
        	System.out.println("ssn|name|gender");
	         while(rs.next()){
	            //Display values

	             System.out.println(rs.getInt("ssn")+ "|"+ rs.getString("name")+ "|"+ rs.getString("gender"));

	         }
		           

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
		finally {
			try {
				// Close connection
				if (stmt != null) {
					stmt.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}