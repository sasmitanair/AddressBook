package com.addressbook.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.addressbook.hibernate.AddressBookEntry;

/**
 * This class compares a given address book with the available address book stored in table 
 * ADDRESSBOOK and returns the contacts unique to both the address books
 * 
 * Example: Book1 = { “Bob”, “Mary”, “Jane” }
• 			Book2 = { “Mary”, “John”, “Jane” }
• 			The friends that are unique to each address book is:
• 			Book1 \ Book2 = { “Bob”, “John” }
 * @author CompAdmin
 *
 */
public class CompareAddressBooks {

	//Stores Number Vs ContactName for user provided Address book
	Map<String,String> userInputMap = new HashMap<String, String>(); 
	
	//Stores Number Vs ContactName for details retrieved from the database
	Map<String,String> dbRetrievedMap = new HashMap<String, String>();
	
	/**
	 * Compares the entries in the available address book with the user provided address book.
	 * Extracts the entries that are uniquely present in both the address books
	 * @param availableContactList
	 * @param userProvidedContacts
	 * @return
	 */
	public Map<String,String> compare(List<AddressBookEntry> availableContactList, String userProvidedContacts){
		//Populate userInputMap with details in userProvidedContactList
		extractData(userProvidedContacts);
		
		//Populate dbRetrievedMap with details in availableContactList
		extractData(availableContactList);
		
		return findUniqueEntries();
	}
	
	/**
	 * Overloaded method to extract the Name,Number pair  from a List<AddressBookEntry> input
	 * @param availableContactList
	 */
	private void extractData(List<AddressBookEntry> availableContactList) {

		Iterator<AddressBookEntry> it = availableContactList.iterator();

		while (it.hasNext()) {
			AddressBookEntry entry = it.next();
			dbRetrievedMap
					.put(entry.getContactNumber(), entry.getContactName());
		}
	}
	
	/**
	 * Overloaded method to extract the Name,Number pair  from a String input
	 * @param userProvidedContacts
	 */
	private void extractData(String userProvidedContacts) {
		/*
		 * Sample JSON format stored in userProvidedContacts
		 * [{"Name":"Alona","Number":"038989899"},{"Name":"Anna","Number":"909090"}]
		 * 
		 * Extract Name and Number from the above format and store it in userInputMap 
		 */
		
		if(userProvidedContacts == null || userProvidedContacts.isEmpty()){
			return;
		}
		StringTokenizer tokens = new StringTokenizer(userProvidedContacts, "{}");
		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken();
			if (token.contains(":") && token.contains(",")) {
				String name = token.substring(token.indexOf(":") + 2, token
							  .indexOf(",") - 1);
				String number = token.substring(token.lastIndexOf(":") + 2,
							    token.length() - 1);
				userInputMap.put(number, name);
			}
		}
	}
	
	/**
	 * This method finds the unique entries present in userInputMap and dbRetrievedMap
	 * @return
	 */
	private Map<String,String> findUniqueEntries(){
		Map<String,String> uniqueSet = new HashMap<String, String>(userInputMap);
		
		uniqueSet.keySet().removeAll(dbRetrievedMap.keySet());
		dbRetrievedMap.keySet().removeAll(userInputMap.keySet());
		uniqueSet.putAll(dbRetrievedMap);
		
		return uniqueSet;
	}
	
}
