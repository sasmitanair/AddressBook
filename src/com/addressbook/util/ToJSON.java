package com.addressbook.util;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.addressbook.hibernate.AddressBookEntry;

import java.util.*;

/**
 * This converts the AddressBookEntry into Name value pair to represent in JSON format
 * @author CompAdmin
 *
 */
public class ToJSON {

	/**
	 * Converts the rows retrieved from db into JSON format 
	 * @param resultset
	 * @return
	 * @throws Exception
	 */
	public JSONArray toJSONArray(List<AddressBookEntry> resultset) throws Exception{
		JSONArray json = new JSONArray();
		try{
			Iterator<AddressBookEntry> it = resultset.iterator();
			while(it.hasNext()){
				AddressBookEntry entry = it.next();
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("Name", entry.getContactName());
				jsonObj.put("Number", entry.getContactNumber());
				json.put(jsonObj);
			}
			
		}catch(Exception e){
			System.out.println("Failed to build the json array... reason: " + e.getMessage());
			
		}
		return json;
	}
	
	/**
	 * Converts the Map of unique contact list into JSON format 
	 * @param resultset
	 * @return
	 * @throws Exception
	 */
	public JSONArray toJSONArray(Map<String,String> uniqueContacts) throws Exception{
		JSONArray json = new JSONArray();
		try{
			for(Map.Entry<String, String> entry : uniqueContacts.entrySet()){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("Number", entry.getKey());
				jsonObj.put("Name", entry.getValue());
				json.put(jsonObj);
			}
			
		}catch(Exception e){
			System.out.println("Failed to build the json array... reason: " + e.getMessage());
			
		}
		return json;
	}
}
