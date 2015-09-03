package ru.mti.edu.jdbc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class Db {

	public static Connection getConnection(HttpServletRequest req) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// �� �� ����� �������� �������� ����
//			OracleDriver driver = new OracleDriver();
//			DriverManager.registerDriver(driver);
			DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/MTIDB");
			return ds.getConnection();
			
//			Properties prop = new Properties();
//			prop.load(new FileInputStream(req.getServletContext().getRealPath("WEB-INF/classes/ru/mti/edu/jdbc/dbinfo.properties")));
//			return DriverManager.getConnection(prop.getProperty("db.url"), prop.getProperty("db.login"), prop.getProperty("db.password"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} 
		
		return null;
	}

}
