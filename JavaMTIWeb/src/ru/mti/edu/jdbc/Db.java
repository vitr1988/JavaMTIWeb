package ru.mti.edu.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.jdbc.driver.OracleDriver;

public class Db {

	public static Connection getConnection() {
		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
			// �� �� ����� �������� �������� ����
			OracleDriver driver = new OracleDriver();
			DriverManager.registerDriver(driver);
			
//			DataSource dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/orcl");
//			dataSource.getConnection();
			
//			Properties prop = new Properties();
//			prop.load(new FileInputStream("src/ru/mti/edu/jdbc/dbinfo.properties"));
			return DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/orcl", "scott", "tiger");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		} 
		
		return null;
	}

}
