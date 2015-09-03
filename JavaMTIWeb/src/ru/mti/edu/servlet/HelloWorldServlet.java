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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.mti.edu.jdbc.Db;

/**
 * Servlet implementation class HelloWorldServlet
 */
@WebServlet("/HelloWorldServlet")
public class HelloWorldServlet extends HttpServlet {
	
//	@Override
//	public void init(ServletConfig sc){
//		System.out.println("Container called init method!");
//	}
	private static final long serialVersionUID = 1L;
       
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served atfdafdafd: ").append(request.getContextPath());
		
		try {
			print(request, response.getWriter());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("Cp1251");
		String login = request.getParameter("login");
//		String password = request.getParameter("password");
//		
//		response.setCharacterEncoding("Cp1251");
//		response.addHeader("TestHeader", "TestHeader");
//		
//		response.setContentType("text/html;charset=Cp1251;");
//		
//		PrintWriter pw = response.getWriter();
//		pw.print("<html><head><title>Ответ сервлета</title></head><body>Приветствуем, пользователь с логином <strong>" + login+ "</strong> и паролем <strong>" + password + "</strong></body></html>");
//		pw.flush();
//		pw.close();
//		
//		response.setStatus(HttpServletResponse.SC_OK);
//		response.sendError(HttpServletResponse.SC_NOT_FOUND);
//		response.sendRedirect("http://mti.edu.ru");
		
		HttpSession session = request.getSession();
		session.setAttribute("loginAttribute", login);
		
	}
	
	private void print(HttpServletRequest request, PrintWriter pw) throws SQLException {
		try (Connection conn = Db.getConnection(request)){
					
			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//			stat.executeUpdate("delete from dept where deptno = 1");
			PreparedStatement prepStat = conn.prepareStatement("delete from dept where deptno = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//			prepStat.setString(1, "1 or 1 = 1");
			prepStat.setInt(1, 1);
			// select * from dept where deptno = 1 or 1 = 1 - SQL Injection
			prepStat.execute();
//			System.out.println(prepStat.getUpdateCount());
//			stat.executeUpdate("insert into dept(deptno, dname, loc) values (1, 'IT Department', 'Moscow')");
			ResultSet rs = stat.executeQuery("select deptno, dname, loc from dept");
			ResultSetMetaData metaData = rs.getMetaData();
			
			rs.beforeFirst();
			while(rs.next()){ 
				int deptno = rs.getInt("deptno");
				String dname = rs.getString("dname"),
						loc = rs.getString(3); // rs.getString("loc")
				if (deptno == 20 && rs.getConcurrency() == ResultSet.CONCUR_UPDATABLE){
					
					rs.updateString(3, "DALLAS");
					rs.updateRow();
				}
				
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

	@Override
	public void destroy(){
		System.out.println("Container called destroy method!");
	}
}
