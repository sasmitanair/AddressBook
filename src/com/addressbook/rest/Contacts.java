package com.addressbook.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import com.addressbook.hibernate.AddressBookEntry;
import com.addressbook.hibernate.DBQuery;
import com.addressbook.util.CompareAddressBooks;
import com.addressbook.util.ToJSON;

/**
 * This class exposes the REST Web Service methods for:
 * - retrieving the existing contact list
 * - adding new contacts to the address book
 * - comparing different address books to find unique contacts
 * @author CompAdmin
 *
 */
@XmlRootElement
@Path("/v1/contacts")
public class Contacts {
	
	public static final String errorString = "Wrong input format. Valid input pattern is ContactName-ContactNumber";
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String returnContacts()throws Exception{
		String result = "";
		
		DBQuery query = new DBQuery();
		List<AddressBookEntry> contactList = query.get();
		
		ToJSON formatter = new ToJSON();
		result = formatter.toJSONArray(contactList).toString();
		return result;
	}
	
	
	/**
	 * This method adds the new contact details to the address book
	 * @param newEntry
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("/addContact/{newEntry}")
	@Consumes("application/text")
	@Produces(MediaType.APPLICATION_JSON)
	public String add(@PathParam("newEntry") String newEntry ) throws Exception{ 
		
		if(newEntry==null || !newEntry.contains("-"))
			return errorString;
		
		String[] token = newEntry.split("-");
		DBQuery query = new DBQuery();
		if(!query.add(token[0], token[1]))
			return errorString;
		
		//return the updated contactlist
		return returnContacts();
	}
	
	@PUT
	@Path("/compare")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String compare(String userProvidedContactList ) throws Exception{ 
		
		System.out.println("input book = " + userProvidedContactList);
		if(userProvidedContactList==null || userProvidedContactList.isEmpty())
			return "Address book provided as input is EMPTY";
		

		DBQuery query = new DBQuery();
		List<AddressBookEntry> availableContactList = query.get();
		Map<String,String> uniqueSet = (new CompareAddressBooks()).compare(availableContactList, userProvidedContactList);
		ToJSON formatter = new ToJSON();
		
		return formatter.toJSONArray(uniqueSet).toString();
	}

}
