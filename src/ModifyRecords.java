
import java.sql.*;
public class ModifyRecords {
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
			e.printStackTrace();

		}
		//initiate sql statement
		Statement stmt = null;
		try {
			stmt = connect.createStatement();
			//sql1 is the string of the sql code
			String create_student = "UPDATE students " +
					"SET name = 'Frank' " +
					"WHERE ssn = 144673371;";
			// To update data in a database, call the executeUpdate(String SQL) method
			stmt.executeUpdate(create_student);	

			create_student = "UPDATE major " +
					"SET name = 'Computer Science', " +
					"level = 'MS' " +
					"WHERE sid IN (SELECT sid FROM students WHERE ssn = 144673371);";
			stmt.executeUpdate(create_student);

            create_student = "DELETE FROM register " +
					"WHERE regtime = 'Summer2024';";
			stmt.executeUpdate(create_student);


            create_student = "DELETE FROM register " +
                    "WHERE course_number IN (" +
                    "  SELECT cnumber " +
                    "  FROM courses c1 " +
                    "  WHERE EXISTS (" +
                    "    SELECT 1 FROM courses c2 " +
                    "    WHERE c2.level = c1.level " +
                    "    AND c2.department_code = c1.department_code " +
                    "    AND c2.cnumber < c1.cnumber" +
                    "  )" +
                    ");";
            stmt.executeUpdate(create_student);


            create_student = "DELETE FROM courses " +
                    "WHERE cnumber IN (" +
                    "  SELECT cnumber " +
                    "  FROM (" +
                    "    SELECT c1.cnumber " +
                    "    FROM courses c1 " +
                    "    WHERE EXISTS (" +
                    "      SELECT 1 FROM courses c2 " +
                    "      WHERE c2.level = c1.level " +
                    "      AND c2.department_code = c1.department_code " +
                    "      AND c2.cnumber < c1.cnumber" +
                    "    )" +
                    "  ) AS delete" +
                    ");";
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