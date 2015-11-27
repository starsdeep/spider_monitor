package com.netease.ux.spiderMonitor;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.*;

import com.netease.ux.spiderMonitor.*;
import com.google.common.collect.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.digester.SetPropertiesRule;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;

public class TableData {
	// parameter
	public Logger logger = Logger.getLogger(SQLHelper.class);
	private SQLHelper dbHelper;
	private HashMap<String, Object> tableConfigs;
	public Date today;
	public int periodSize;
	public Date[] period;
	public DateFormat df;
	public String tableName;
	public String tableField;
	private String projectPath = "/Users/starsdeep/Documents/Eclipse_workspace/spider_monitor";
	private String configFilePath = projectPath + "/config/tableConfig.cfg";
	//set when table name is given
	private String sqlcmd;
	private boolean shouldAlign;
	// data
	private Map<String, String[]> weeklyPageScan;

	public TableData() throws FileNotFoundException, IOException, org.json.simple.parser.ParseException {
		this.df = new SimpleDateFormat("yyyyMMdd");
//		this.period = new Date[periodSize];
		this.dbHelper = new SQLHelper();
		
//		for (int i = 0; i < this.periodSize; i++) {
//			this.period[i] = addDate(this.today, -i);
//		}
		
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject)parser.parse(new FileReader(this.configFilePath));
		this.tableConfigs = (HashMap<String, Object>) Utility.jsonToMap(config);
		
	}

	
	
	public String getTable(String tableName, String tableField, String today, int periodSize){
		//0 initialization
		this.tableName = tableName;
		this.tableField = tableField;
		this.today = this.StringtoDate(today);
		this.periodSize = periodSize;
		String startDay = this.DateToString(this.addDate(this.today,-(this.periodSize)));
		
		this.period = new Date[periodSize];
		for (int i = 0; i < this.periodSize; i++) {
			this.period[i] = addDate(this.today, -i);
		}
		
		String data;
		List<String[]> records;
		String sql;
		
		//1 get table config info, like sqlcmd, shouldAlign
		HashMap<String,Object> tableConfig = (HashMap<String,Object>)this.tableConfigs.get(this.tableName);
		this.sqlcmd = (String)tableConfig.get("sqlcmd");
		this.shouldAlign = (Boolean)tableConfig.get("shouldAlign");
		
		//2 format sql
		if(tableName.equals("rank"))
			sql = String.format(this.sqlcmd, tableField, startDay);
		else if(tableName.equals("page"))
			sql = String.format(this.sqlcmd, tableField, startDay, tableField);
		else if(tableName.equals("comment"))
			sql = String.format(this.sqlcmd, tableField, startDay, tableField);
		else
			sql = "invalid";
		
		//3 get list
		records = this.dbHelper.getRecords(sql);
		Table<String, String, String> table = Utility.recordsToTable(records);
		LinkedList<HashMap<String,String>>list = Utility.TableToList(table, tableField);
		
		//4 align first list field with name
		
		HashMap<String,String> dict;
		if(this.tableField.equals("source_id")){
			dict = getSourceAlign();
		}
		else if(this.tableField.equals("game_id"))
			dict = getGameAlign();
		else
			dict = new HashMap<String,String>();
		if(this.shouldAlign)
			align(list,dict);	
		
		
		return JSONValue.toJSONString(list);
	}

	public void align(LinkedList<HashMap<String,String>>list, HashMap<String,String> dict){
		for(int i=0;i<list.size();i++){
			String key = list.get(i).get(this.tableField);
			if(dict.containsKey(key))
				list.get(i).put(this.tableField, dict.get(key));
		}
		return;
	}
	
	public HashMap<String,String> getSourceAlign(){
		List<String[]> records = this.dbHelper.getRecords("select id,value from dim_source_name");
		return Utility.recordsToMap(records);
	}
	
	public HashMap<String,String> getGameAlign(){
		List<String[]> records = this.dbHelper.getRecords("select id,value from dim_game_name");
		return Utility.recordsToMap(records);
	}
	
	//tool function
	public Date StringtoDate(String s) {
		Date today = new Date();
		try {
			today = df.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return today;
	}

	public String DateToString(Date d) {
		return this.df.format(d);
	}

	
	public Date addDate(Date d, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

}
