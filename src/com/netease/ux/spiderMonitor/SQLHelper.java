/**
 * 数据库操作封装
 * @version 0.2
 * @2015-11-04
 */
package com.netease.ux.spiderMonitor;

import com.netease.ux.spiderMonitor.Config;

import java.io.*;
import java.util.*;		
import java.lang.*;
import java.sql.*;
import java.math.* ; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;

//用于对properties 这类配置文件做映射，支持key
//www.cnblog.com/lingiu/p/3468464.html
import java.util.Properties; 

import javafx.beans.property.SetProperty;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.digester.SetPropertiesRule;

public class SQLHelper implements java.io.Serializable{
		
	public Logger logger = Logger.getLogger(SQLHelper.class);
	private Properties dbProp;
	private String projectPath = "/Users/starsdeep/Documents/Eclipse_workspace/spider_monitor";
	private String configFilePath = projectPath + "/config/dbConfig.cfg";
	private String dbDriver="";
	private String dbUrl="";
	private String dbUsername="";
	private String dbPassword="";
	private Connection conn=null;
	private Statement stmt=null;
	
	private void initProperties(){
		this.dbProp = new Properties();
		try{
			FileInputStream inputConfig=new FileInputStream(this.configFilePath);
			dbProp.load(inputConfig);
			/*System.out.println("Initing Config..Complete\n");*/
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	
	
	/**
	 * Constructor
	 */
	public SQLHelper(){
		initProperties();
		dbDriver=dbProp.getProperty("dbDriver");
		dbUrl=dbProp.getProperty("dbUrl");
		dbUsername=dbProp.getProperty("dbUsername");
		dbPassword=dbProp.getProperty("dbPassword");
		System.out.println("[mylog:] " + dbUrl);
	}
	
	private boolean connect_db(){
		try{
			Class.forName(dbDriver);
			System.out.println("Connecting to database...\n");
			conn = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
			return true;
		}
		catch (SQLTimeoutException e){
			System.out.println("Error in connect_db:");
			logger.error("[group:" + this.getClass().getName() + "][message: exception][" + e.toString() +"]");
			e.printStackTrace();
			return false;
		}
		catch (SQLException e){
			System.out.println("Error in connect_db:SQLException");
			logger.error("[group:" + this.getClass().getName() + "][message: exception][" + e.toString() +"]");
			e.printStackTrace();
			return false;
		}
		catch (Exception e){
			logger.error("[group:" + this.getClass().getName() + "][message: exception][" + e.toString() +"]");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 关闭数据库链接
	 * @throws SQLException
	 */
	public void close(){
		try{
			stmt.close();
			conn.close();
		}
		catch(SQLException e){
			logger.error("[group:" + this.getClass().getName() + "][message: exception][" + e.toString() +"]");
			e.printStackTrace();
		}
	}
	
	

	public List<String[]> getRecords(String sqlcmd){
		try {
			connect_db(); 
			stmt=conn.createStatement();
			//logger.info("[group:" + this.getClass().getName() + "][message: ][ the sqlcmd is: " + sqlcmd +"]");
			System.out.println("mylog: the sqlcmd is," + sqlcmd );
			ResultSet rs=stmt.executeQuery(sqlcmd);
			ResultSetMetaData rsmd = rs.getMetaData();
			int fieldNum = rsmd.getColumnCount();
			List<String[]> recordList = new ArrayList<String[]>();
			
			while(rs.next()){
				String[] singleRecord = new String[fieldNum];		
				for (int i = 1; i <= fieldNum; i++) {		
					singleRecord[i-1] = rs.getString(i);
//					logger.info("[group:" + this.getClass().getName() + "][message: ][" + singleRecord[i-1] +"]");			
				}
				recordList.add(singleRecord);	
			}
			rs.close();
			close();
			return recordList;
		} catch (SQLException e) {
			close();
			logger.error("[group:" + this.getClass().getName() + "][message: exception][" + e.toString() +"]");
			e.printStackTrace();
			return null;
		}
	}
	
	public HashMap<String,String> recordsToMap(List<String[]> records){
		HashMap<String,String> dict = new HashMap<String,String>();
		for(int i=0;i<records.size();i++){
			dict.put(records.get(i)[0], records.get(i)[1]);
		}
		return dict;
	}

}
