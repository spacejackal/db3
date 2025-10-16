
import java.sql.*;
public class Index {
	private static Connection connect = null;

	public static void main(String[] args) 
	{
        long queryExecutionTime = 0; 

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
			
			
			
            String query_student = "WITH student_degrees AS (" +
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

			long queryStartTime = System.nanoTime();
            ResultSet rs = stmt.executeQuery(query_student);
            long queryEndTime = System.nanoTime();
            queryExecutionTime = queryEndTime - queryStartTime;
            System.out.println("Degree Name | Level | Male Count | Female Count");
            System.out.println("--------------------------------------------------");
                           
             while(rs.next()){
                 System.out.println(rs.getString("degree_name") + " | " + 
                                  rs.getString("degree_level") + " | " + 
                                  rs.getInt("male_count") + " | " + 
                                  rs.getInt("female_count"));
             }

             // End timing for query execution

             System.out.println("\nQuery execution time: " + queryExecutionTime + " nanoseconds");

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