package com.netease.ux.spiderMonitor;

import java.io.*;
import java.util.*;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.*;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class Rank
 */
@WebServlet("/Table")
public class TableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    public TableServlet(){
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		setup(request,response);
		PrintWriter out= response.getWriter();
		
		String startDay = request.getParameter("startDay");
		int periodSize = Integer.parseInt(request.getParameter("periodSize"));
		String tableName = request.getParameter("tableName");
		String tableField = request.getParameter("tableField");
		
		TableData tableData;
		try {
			tableData = new TableData();
			out.write(tableData.getTable(tableName,tableField,startDay,periodSize));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
//		TableData tableData;
//		try {
//			tableData = new TableData(startDay, periodSize);
//			out.write(tableData.getTable(request.getParameter("tableName"), request.getParameter("tableField")));
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
	}

	private void setup(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		response.setContentType("text/html;charset=utf-8"); 
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        
        response.setHeader("Pragma","No-Cache");
		response.setHeader("Cache-Control","No-Cache");
		response.setDateHeader("Ewindowsxpires", 0);
	}

}
