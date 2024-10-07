package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Registration
 */
public class Registration extends HttpServlet {
	Connection con;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	
		// TODO Auto-generated method stub
		ServletContext sc=config.getServletContext();
		String s1=sc.getInitParameter("driver");
		String s2=sc.getInitParameter("url");
		String s3=sc.getInitParameter("username");
		String s4=sc.getInitParameter("password");
		
		try {
			Class.forName(s1);
			con=DriverManager.getConnection(s2, s3, s4);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String s1=request.getParameter("fname");
		String s2=request.getParameter("lname");
		String s3=request.getParameter("uname");
		String s4=request.getParameter("pword");
		try {
			PreparedStatement psmt=con.prepareStatement("INSERT INTO registration (fname, uname, username, password) VALUES (?, ?, ?, ?)");
			psmt.setString(1, s1);
			psmt.setString(2, s2);
			psmt.setString(3, s3);
			psmt.setString(4, s4);
			psmt.executeUpdate();
			HttpSession hs=request.getSession(true);
			hs.setAttribute("uname",s3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		PrintWriter pw=response.getWriter();
		RequestDispatcher rd=request.getRequestDispatcher("login.html");
		rd.forward(request, response);
		pw.print("welcome"+s1);
	}

}
