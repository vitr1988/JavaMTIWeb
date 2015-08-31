package ru.mti.edu.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.mti.edu.jdbc.Db;

/**
 * Servlet implementation class HelloWorldServlet
 */
@WebServlet("/HelloWorldServlet")
public class HelloWorldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served atfdafdafd: ").append(request.getContextPath());
		
		try {
			print(response.getWriter());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void print(PrintWriter pw) throws SQLException {
		try (Connection conn = Db.getConnection()){
					
			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//			stat.executeUpdate("delete from dept where deptno = 1");
			PreparedStatement prepStat = conn.prepareStatement("delete from dept where deptno = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//			prepStat.setString(1, "1 or 1 = 1");
			prepStat.setInt(1, 1);
			// select * from dept where deptno = 1 or 1 = 1 - SQL Injection
			prepStat.execute();
//			System.out.println(prepStat.getUpdateCount());
//			stat.executeUpdate("insert into dept(deptno, dname, loc) values (1, 'IT Department', 'Moscow')");
			ResultSet rs = stat.executeQuery("select * from dept");
			ResultSetMetaData metaData = rs.getMetaData();
			
			rs.beforeFirst();
			while(rs.next()){ 
				int deptno = rs.getInt("deptno");
				String dname = rs.getString("dname"),
						loc = rs.getString(3); // rs.getString("loc")
				pw.println(deptno + " | " + dname + " | " + rs.getString(3));
			}
			
//			CallableStatement call = conn.prepareCall("{call raise_application_error(?, ?)}");
//			call.setInt(1, -20002);
//			call.setString(2, "Проверка вызова хранимой процедурыvgfgsf!");
//			call.execute();
			pw.flush();
			pw.close();
			stat.close();
			prepStat.close();
		}
	}

}
