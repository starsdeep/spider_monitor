package com.netease.ux.spiderMonitor;

import java.util.*;

import org.json.simple.*;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public final class Utility {
	
	public static HashMap<String,String> recordsToMap(List<String[]> records){
		HashMap<String,String> dict = new HashMap<String,String>();
		for(int i=0;i<records.size();i++){
			dict.put(records.get(i)[0], records.get(i)[1]);
		}
		return dict;
	}
	
	public static Table<String, String, String> recordsToTable(List<String[]> records) {
		Table<String, String, String> table = HashBasedTable.create();
		for (int i = 0; i < records.size(); i++) {
			table.put(records.get(i)[0], records.get(i)[1], records.get(i)[2]);
		}
		return table;
	}
	
	//this should ensure the columen key set be iterated with order
	public static LinkedList TableToList(Table<String, String, String> table, String rowName) {
		LinkedList list = new LinkedList();
		for (String r : table.rowKeySet()) {
			HashMap<String, String> m = new LinkedHashMap<String, String>();
			m.put(rowName, r);
			for (String c : table.columnKeySet()) {
				m.put(c, table.get(r, c));
			}
			list.add(m);
		}
		return list;
	}

	
	public static List<String[]> mergeRecordField(List<String[]> records, int num_merge){
		List<String[]> result = new ArrayList<String[]>();
		int num_field = records.get(0).length - (num_merge-1);
		for(String[] record: records){
			String[] new_record = new String[num_field];
			String merged_field = record[0];
			for(int i=1;i<num_merge;i++){
				merged_field += ":" + record[i];
			}
			new_record[0] = merged_field;
			for(int i=num_merge;i<record.length;i++){
				new_record[i-num_merge+1] = record[i]; 
			}
			result.add(new_record);
		}
		return result;
	}
	
	
	public static Map<String, Object> jsonToMap(JSONObject json){
	    Map<String, Object> retMap = new HashMap<String, Object>();
	    	retMap = toMap(json);
	    return retMap;
	}

	public static Map<String, Object> toMap(JSONObject object){
	    Map<String, Object> map = new HashMap<String, Object>();
	    
	    for(Object key_iter : object.keySet()) {
	        String key = (String)key_iter;
	        Object value = object.get(key);

	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        map.put(key, value);
	    }
	    return map;
	}

	public static List<Object> toList(JSONArray array){
	    List<Object> list = new ArrayList<Object>();
	    for(int i = 0; i < array.size(); i++) {
	        Object value = array.get(i);
	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        list.add(value);
	    }
	    return list;
	}
	
	public static void test_jsonToMap(){
		
	}
}
