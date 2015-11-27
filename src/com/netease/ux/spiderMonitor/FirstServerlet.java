package com.netease.ux.spiderMonitor;

import java.io.*;
import java.text.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import java.util.*;

import com.netease.ux.spiderMonitor.*;

/**
 * Servlet implementation class FirstServerlet
 */

public class FirstServerlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public FirstServerlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// test zone no meaing
		String[] testlist = new String[10];
		int i = testlist.length;
		// testlist.len

		TableData tableData = new TableData("20151012", 7);

		System.out.println("this is my log!!!");

		// SQLHelper dbHelper = new SQLHelper();
		// String sqlcmd = "select * from test;";
		// List<String[]> records = dbHelper.getRecords(sqlcmd);
		//

		// refresh database
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		response.setHeader("Pragma", "No-Cache");
		response.setHeader("Cache-Control", "No-Cache");
		response.setDateHeader("Ewindowsxpires", 0);

		System.out.println("flag");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		// out.write(tableData.getRankInfo());

		// out.println("<html>");
		// out.println("<head>");
		// out.println("<title>Hello World!</title>");
		// out.println("</head>");
		// out.println("<body>");
		// out.println("<h1>Hello World!</h1>");
		// out.println("</body>");
		// out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
