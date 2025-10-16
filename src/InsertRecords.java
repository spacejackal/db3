
import java.sql.*;
public class InsertRecords {
	private static Connection connect = null;

	public static void main(String[] args) 
	{

		try {
			//Set up connection parameters
			String userName = "coms363";
			String password = "password";
			String dbServer = "jdbc:mysql://localhost:3306/project1?allowLoadLocalInfile=true";
			//Set up connection
			connect = DriverManager.getConnection(dbServer,userName,password);
		} catch(Exception e) {

		}
		//initiate sql statement
		Statement stmt = null;
		try {
            stmt = connect.createStatement();
            String val = "LOAD data local "+
             "infile 'students.csv' "+
            " into table students"+
            " fields terminated by ','"+
            " enclosed by '\"'"+
            " lines terminated by '\r\n'"+
            " ignore 1 rows"+
            " (sid,ssn,name,gender,dob,c_addr,c_phone,p_addr,p_phone);";
            stmt.executeUpdate(val);
            System.out.println("Inserted records into students table");

            val = "LOAD data local "+
             "infile 'departments.csv' "+
            " into table departments"+
            " fields terminated by ','"+
            " enclosed by '\"'"+
            " lines terminated by '\r\n'"+
            " ignore 1 rows"+
            " (dcode,dname,phone,college);";
            stmt.executeUpdate(val);
            System.out.println("Inserted records into departments table");

            val = "LOAD data local "+
             "infile 'degrees.csv' "+
            " into table degrees"+
            " fields terminated by ','"+
            " enclosed by '\"'"+
            " lines terminated by '\r\n'"+
            " ignore 1 rows"+
            " (dgname,level,department_code);";
            stmt.executeUpdate(val);
            System.out.println("Inserted records into degrees table");

            val = "LOAD data local "+
                "infile 'major.csv' "+
                " into table major"+
                " fields terminated by ','"+
                " enclosed by '\"'"+
                " lines terminated by '\r\n'"+
                " ignore 1 rows"+
                " (sid,name,level);";
            stmt.executeUpdate(val);
            System.out.println("Inserted records into majors table");

            val = "LOAD data local "+
                "infile 'minor.csv' "+
                " into table minor"+
                " fields terminated by ','"+
                " enclosed by '\"'"+
                " lines terminated by '\r\n'"+
                " ignore 1 rows"+
                " (sid,name,level);";
            stmt.executeUpdate(val);
            System.out.println("Inserted records into minors table");

            val = "LOAD data local "+
                "infile 'courses.csv' "+
                " into table courses"+
                " fields terminated by ','"+
                " enclosed by '\"'"+
                " lines terminated by '\r\n'"+
                " ignore 1 rows"+
                " (cnumber,cname,description,creditshours,level,department_code);";
            stmt.executeUpdate(val);
            System.out.println("Inserted records into courses table");

            val = "LOAD data local "+
                "infile 'register.csv' "+
                " into table register"+
                " fields terminated by ','"+
                " enclosed by '\"'"+
                " lines terminated by '\r\n'"+
                " ignore 1 rows"+
                " (sid, course_number, regtime, grade);";
            stmt.executeUpdate(val);
            System.out.println("Inserted records into register table");

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
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