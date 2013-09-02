package com.addressbook.rest;

import java.util.List;

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
import com.addressbook.util.ToJSON;

@XmlRootElement
@Path("/v1/contacts")
public class Contacts {
	
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
	
	
	@PUT
	@Path("/addContact/{newEntry}")
	@Consumes("application/xml")
	@Produces(MediaType.APPLICATION_JSON)
	public String add(@PathParam("newEntry") String newEntry ) throws Exception{ 
		
		if(newEntry==null || !newEntry.contains("-"))
			return null;
		
		String[] token = newEntry.split("-");
		DBQuery query = new DBQuery();
		if(!query.add(token[0], token[1]))
			return null;
		//return the updated contactlist
		return returnContacts();
	}

}
