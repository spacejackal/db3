
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
			e.printStackTrace();
			System.out.println("Failed to connect to database");
			return; // Exit if connection fails
		}
		//initiate sql statement
		Statement stmt = null;
		
		try {
			stmt = connect.createStatement();
			//To execute a SELECT query, call the executeQuery(String) method with the SQL to use
			String query_student = "SELECT c.cnumber, c.cname, AVG(r.grade) as average_grade " +
								   "FROM courses c " +
								   "JOIN register r ON c.cnumber = r.course_number " +
								   "GROUP BY c.cnumber, c.cname " +
								   "ORDER BY c.cnumber;";
					
			
			ResultSet rs = stmt.executeQuery(query_student);
        	System.out.println("Course Numbers, names, average Grades:");	
  
	         while(rs.next()){
	            //Display values
	             System.out.println(rs.getInt("cnumber") + " | " + 
	                              rs.getString("cname") + " | " + 
	                              String.format("%.2f", rs.getDouble("average_grade")));
	         }


             // Query for count of female students who major or minor in LAS degrees
             query_student = "SELECT COUNT(DISTINCT s.sid) as female_count " +
                           "FROM students s " +
                           "JOIN departments d ON d.college = 'LAS' " +
                           "JOIN degrees deg ON deg.department_code = d.dcode " +
                           "WHERE s.gender = 'F' AND (" +
                           "  EXISTS (SELECT 1 FROM major maj WHERE maj.sid = s.sid AND maj.name = deg.dgname AND maj.level = deg.level) OR " +
                           "  EXISTS (SELECT 1 FROM minor min WHERE min.sid = s.sid AND min.name = deg.dgname AND min.level = deg.level)" +
                           ");";

             rs = stmt.executeQuery(query_student);
             System.out.println("\nCount of female students in LAS:");
             
             while(rs.next()){
                 System.out.println("Female students count: " + rs.getInt("female_count"));
             }

             
             query_student = "WITH student_degrees AS (" +
                           "  SELECT DISTINCT s.sid, s.gender, maj.name as degree_name, maj.level as degree_level " +
                           "  FROM students s " +
                           "  JOIN major maj ON s.sid = maj.sid " +
                           "  UNION " +
                           "  SELECT DISTINCT s.sid, s.gender, min.name as degree_name, min.level as degree_level " +
                           "  FROM students s " +
                           "  JOIN minor min ON s.sid = min.sid" +
                           "), " +
                           "degree_counts AS (" +
                           "  SELECT degree_name, degree_level, " +
                           "    COUNT(CASE WHEN gender = 'M' THEN 1 END) as male_count, " +
                           "    COUNT(CASE WHEN gender = 'F' THEN 1 END) as female_count " +
                           "  FROM student_degrees " +
                           "  GROUP BY degree_name, degree_level" +
                           ") " +
                           "SELECT degree_name, degree_level, male_count, female_count " +
                           "FROM degree_counts " +
                           "WHERE male_count > female_count;";

             rs = stmt.executeQuery(query_student);
             System.out.println("\nDegrees with more male than female students:");
             System.out.println("Degree Name | Level | Male Count | Female Count");
             
             while(rs.next()){
                 System.out.println(rs.getString("degree_name") + " | " + 
                                  rs.getString("degree_level") + " | " + 
                                  rs.getInt("male_count") + " | " + 
                                  rs.getInt("female_count"));
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