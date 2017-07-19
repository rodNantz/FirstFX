package app.fxmltableview.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public static Connection getConnection() throws SQLException, ClassNotFoundException{
		String url = "jdbc:mysql://localhost:3306/nlçalala";
		String usuario = "root";
		String senha = "root";
		Class.forName("com.mysql.jdbc.Driver");
		Connection con;
		con = DriverManager.getConnection(url, usuario, senha);
		return con;
	}
	
}
