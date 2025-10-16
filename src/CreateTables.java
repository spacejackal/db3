
import java.sql.*;
public class CreateTables {
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
			stmt = connect.createStatement();
			//sql1 is the string of the sql code
			String create_student = "CREATE TABLE students (\r\n" + 
					"	sid INT NOT NULL,\r\n" + 
					"	ssn INTEGER,\r\n" + 
					"    name VARCHAR(10),\r\n" + 
					"    gender VARCHAR(1),\r\n" + 
					"    dob DATE, \r\n" + 
					"    c_addr VARCHAR(20),\r\n" + 
					"    c_phone VARCHAR(10),\r\n" + 
					"    p_addr VARCHAR(20),\r\n" + 
					"    p_phone VARCHAR(10),\r\n" + 
					"    PRIMARY KEY(ssn),\r\n" + 
					"    UNIQUE(sid)\r\n" + 
					");";
			// To update data in a database, call the executeUpdate(String SQL) method
			stmt.executeUpdate(create_student);	
			System.out.println("Created students table");  
            create_student = "create table departments("+
							"    dcode int,"+
    						"    dname varchar(50) NOT NULL,"+
    						"    phone varchar(10),"+
    						"    college varchar(20),"+
    						"    primary key(dcode),"+
    						"    unique key(dname)"+
							");";
            stmt.executeUpdate(create_student);
            System.out.println("Created departments table");

			create_student = "create table degrees("+
							"    dgname varchar(50),"+
							"    level varchar(5),"+
							"    department_code int,"+
							"    primary key(dgname, level),"+
							"    foreign key(department_code) references departments(dcode)"+
							");";
			stmt.executeUpdate(create_student);
			System.out.println("Created degrees table");
			create_student = "create table courses("+
							"    cnumber int,"+
							"    cname varchar(50),"+
							"    description varchar(100),"+
							"    creditshours int,"+
							"    level varchar(20),"+
							"    department_code int,"+
							"    primary key(cnumber),"+
							"    foreign key(department_code) references departments(dcode)"+
							");";
			stmt.executeUpdate(create_student);
			System.out.println("Created courses table");
			create_student = "create table register("+
							"    sid int,"+
							"    course_number int,"+
							"    regtime varchar(20),"+
							"    grade int,"+
							"    primary key(sid, course_number),"+
							"    foreign key(sid) references students(sid),"+
							"    foreign key(course_number) references courses(cnumber)"+
							");";
			stmt.executeUpdate(create_student);
			System.out.println("Created register table");
			create_student = "create table major("+
							"    sid int,"+
							"    name varchar(50),"+
							"    level varchar(5),"+
							"    primary key(sid, name, level),"+
							"    foreign key(sid) references students(sid),"+
							"    foreign key(name, level) references degrees(dgname, level)"+
							");";

			stmt.executeUpdate(create_student);
			System.out.println("Created major table");

			create_student = "create table minor("+
							"    sid int,"+
							"    name varchar(50),"+
							"    level varchar(5),"+
							"    primary key(sid, name, level),"+
							"    foreign key(sid) references students(sid),"+
							"    foreign key(name, level) references degrees(dgname, level)"+
							");";
			stmt.executeUpdate(create_student);
			System.out.println("Created minor table");

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