
import java.sql.*;
public class DropTables {
	private static Connection connect = null;

	public static void main(String[] args) 
	{

		try {
			//Set up connection parameters
			String userName = "coms363";
			String password = "password";
			String dbServer = "jdbc:mysql://localhost:3306/project1";
			//Set up connection
			connect = DriverManager.getConnection(dbServer,userName,password);
		} catch(Exception e) {

		}
		//initiate sql statement
		Statement stmt = null;
		try {
			stmt = connect.createStatement();
			//sql1 is the string of the sql code
			String create_student = "Drop table major, minor, register, students, departments, degrees, courses;";
			// To update data in a database, call the executeUpdate(String SQL) method
			stmt.executeUpdate(create_student);	
 
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