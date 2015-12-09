package com.netease.ux.spiderMonitor;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.*;

import com.netease.ux.spiderMonitor.*;
import com.google.common.collect.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.digester.SetPropertiesRule;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;

public class TableData {
	// parameter
	public Logger logger = Logger.getLogger(SQLHelper.class);
	private SQLHelper dbHelper;
	private HashMap<String, Object> tableConfigs;
	private LinkedList<HashMap<String,String>> alignDicts;
	public Date today;
	public int periodSize;
	public Date[] period;
	public DateFormat df;
	public String tableName;
	public String tableField;
	public int fieldNum;
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

	
	public String getTable(String tableName, String tableField, String today, int periodSize, int fieldNum){
		//0 initialization
		this.tableName = tableName;
		this.tableField = tableField;
		this.today = this.StringtoDate(today);
		this.periodSize = periodSize;
		this.fieldNum = fieldNum;
		
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
		if(this.fieldNum>1)
			records = Utility.mergeRecordField(records, this.fieldNum);
		Table<String, String, String> table = Utility.recordsToTable(records);
		LinkedList<HashMap<String,String>>list = Utility.TableToList(table, tableField);
		//test
		if(this.fieldNum>1){
			String[] t = records.get(0);
			System.out.println("test merged record:");
			for(int i=0;i<t.length;i++){
				System.out.println(i + ":" + t[i]);
			}
		}
		
		
		//4 align first list field with name
		this.alignDicts = new LinkedList<HashMap<String,String>>();
		if(this.tableField.equals("source_id")){
			this.alignDicts.add(getSourceAlign());
		}
		else if(this.tableField.equals("game_id"))
			this.alignDicts.add(getGameAlign());
		else if(this.fieldNum==2 && this.tableField.equals("game_id, source_id")){
			this.alignDicts.add(getGameAlign());
			this.alignDicts.add(getSourceAlign());
		}
		else
			;
		
//		if(this.shouldAlign)
//			addName(list);
		
		
		//5 get anomaly
		LinkedList<String> anomaly = new LinkedList<String>();
		if(this.tableName.equals("rank") || this.tableName.equals("page")){
			anomaly = getAnomaly(table);
			System.out.println(anomaly.size());
		}
		
		//6 encode to json string
		JSONObject obj=new JSONObject();
		obj.put("anomaly", anomaly);
		obj.put("data", list);
		obj.put("id_name_dicts", this.alignDicts);
		String jsonText = obj.toJSONString();
		//System.out.print(jsonText);
		return jsonText;
	}

	// return a set of id which is abnormal
	
	public LinkedList<String> getAnomaly(Table<String, String, String> table){
		Map<String,String> row;
		int today_value = 0;
		LinkedList<String> result = new LinkedList<String>();
		for(String rowKey: table.rowKeySet()){
			row = table.row(rowKey);
			HashMap<String,String> temp_row = new HashMap<String,String>(row);
			if(isRowAbnormal(rowKey, temp_row))
				result.add(rowKey);
				
		}
		return result;
	}
	
	
	
	private boolean isRowAbnormal(String rowKey, Map<String,String> row){
		int today_value = 0;
		try{
			today_value = Integer.parseInt(row.get(DateToString(this.today)));
		}catch(NumberFormatException e){
			//e.printStackTrace();
			today_value = 0;
		}
		
		if(this.tableName.equals("page")){
			row.remove(DateToString(this.today));
			double mean = meanOfMap(row);
			return isAbnormalBymean(mean,(double)today_value, 0.25);
		}
		if(this.tableName.equals("rank") && rowKey.substring(0,3).equals("ios")){
			return today_value!=1200;
		}
		return false;
	}
	
	private double meanOfMap(Map<String,String> m){
		double s = 0;
		for(String v: m.values()){
			s += Integer.parseInt(v);
		}
		return s * 1.0 / m.size(); 
	}
	
	private boolean isAbnormalBymean(double value1, double value2, double threshold){
		return (value1>10 && value2 < value1 && value2/value1 < threshold);
	}
	
	
//	public void align(LinkedList<HashMap<String,String>>list){
//		for(int i=0;i<list.size();i++){
//			String key = list.get(i).get(this.tableField);
//			if(this.alignDict.containsKey(key))
//				list.get(i).put(this.tableField, this.alignDict.get(key));
//		}
//		return;
//	}
//	
//	public void addName(LinkedList<HashMap<String,String>>list){
//		for(int i=0;i<list.size();i++){
//			String key = list.get(i).get(this.tableField);
//			if(this.alignDict.containsKey(key)){
//				list.get(i).put(this.tableField+"_name", this.alignDict.get(key));
//			}
//		}
//	}
	
	
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
