package dbC;
import java.sql.*;

public class dbConnection {
	public static Connection dbC() {
		Connection connect=null;
		String uri=
				"jdbc:mysql://localhost:6666/bankuser?"+
				"useSSL=true&serverTimezone=GMT&characterEncoding=utf-8";
		String user="root";
		String pw="WRWKwxc@@99";
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (Exception e) {}
		try {
			connect=DriverManager.getConnection(uri,user,pw);
		}
		catch(SQLException e) {}
		return connect;
	}

}
