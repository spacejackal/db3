
import java.sql.*;
public class CreateTables {
	private static Connection connect = null;

	public static void main(String[] args) 
	{

		try {
			//Set up connection parameters
			String userName = "root";
			String password = "Franky12!";
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
			String create_student = "CREATE TABLE students (\r\n" + 
					"	snum INT,\r\n" + 
					"	ssn INTEGER,\r\n" + 
					"    name VARCHAR(10),\r\n" + 
					"    gender VARCHAR(1),\r\n" + 
					"    dob DATE, \r\n" + 
					"    c_addr VARCHAR(20),\r\n" + 
					"    c_phone VARCHAR(10),\r\n" + 
					"    p_addr VARCHAR(20),\r\n" + 
					"    p_phone VARCHAR(10),\r\n" + 
					"    PRIMARY KEY(ssn),\r\n" + 
					"    UNIQUE(snum)\r\n" + 
					");";
			// To update data in a database, call the executeUpdate(String SQL) method
			stmt.executeUpdate(create_student);	
			System.out.println("Created students table");  
            create_student = "create table departments("+
	"dcode int,"+
    "    dname varchar(50) NOT NULL,"+
    "    phone varchar(10),"+
    "    college varchar(20),"+
    "    primary key(dcode),"+
    "    unique key(dname)"+
	");";
            stmt.executeUpdate(create_student);
            System.out.println("Created departments table");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			
			stmt.executeBatch();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			//insert multiple records
			stmt.addBatch("INSERT INTO students VALUE ("
					+ "1001,65465234,'Randy','M','2000-12-01','301 E Hall','5152700988','121 Main','7083066321');");				
			stmt.addBatch("INSERT INTO students VALUE ("
					+ "1002,123456789,'Vicky','F','2000-12-01','301 E Hall','5152700988','121 Main','7083066321');");				
			stmt.executeBatch();
			System.out.println("insert records in table ...");  

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
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