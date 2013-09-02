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
	 * Converts the contact rows retrieved from the db into JSON format 
	 * @param resultset
	 * @return
	 * @throws Exception
	 */
	public JSONArray toJSONArray(List<AddressBookEntry> resultset) throws Exception{
		JSONArray json = new JSONArray();
		String name="";
		String number="";
		try{
			Iterator<AddressBookEntry> it = resultset.iterator();
			while(it.hasNext()){
				AddressBookEntry entry = it.next();
				name = entry.getContactName();
				number = entry.getContactNumber();
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("Name", name);
				jsonObj.put("Number", number);
				json.put(jsonObj);
			}
			
		}catch(Exception e){
			System.out.println("Failed to build the json array... reason: " + e.getMessage());
			
		}
		return json;
	}
}
