
import java.sql.*;
public class Query {
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
            "  SELECT DISTINCT s.sid, s.gender, maj.name, maj.level  " +
            "  FROM students s " +
            "  JOIN major maj ON s.sid = maj.sid " +
            "  UNION " +
            "  SELECT DISTINCT s.sid, s.gender, min.name, min.level " +
            "  FROM students s " +
            "  JOIN minor min ON s.sid = min.sid" +
            "), " +
            "MaleCounts AS (" +
            "  SELECT name as degree_name, level as degree_level, COUNT(*) as male_count " +
            "  FROM student_degrees " +
            "  WHERE gender = 'M' " +
            "  GROUP BY name, level" +
            "), " +
            "FemaleCounts AS (" +
            "  SELECT name as degree_name, level as degree_level, COUNT(*) as female_count " +
            "  FROM student_degrees " +
            "  WHERE gender = 'F' " +
            "  GROUP BY name, level" +
            ") " +
            "SELECT" + 
            "  m.degree_name, " +
            "  m.degree_level, " +
            "  m.male_count, " +
            "  f.female_count " +
            "FROM MaleCounts m " +
            "JOIN FemaleCounts f ON m.degree_name = f.degree_name AND m.degree_level = f.degree_level " +
            "WHERE m.male_count > f.female_count;";

             rs = stmt.executeQuery(query_student);
             System.out.println("\nDegree Name | Level | Male Count | Female Count");
             
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