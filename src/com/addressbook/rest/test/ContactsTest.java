package com.addressbook.rest.test;

import javax.xml.bind.JAXBElement;

import org.junit.Before;
import org.junit.Test;

import com.addressbook.hibernate.AddressBookEntry;
import com.addressbook.hibernate.DBQuery;
import com.addressbook.rest.Contacts;

import static org.junit.Assert.*;

public class ContactsTest {

	@Before
	public void setUp(){
		DBQuery query = new DBQuery();
		query.add("Bia", "0404555777");
		query.add("John", "0404555999");
	}
	@Test
	public void validateRESTGetMethod() throws Exception{
		String expectedResultPart1 = "\"Name\":\"John\",\"Number\":\"0404555999\"}";
		String expectedResultPart2 = "\"Name\":\"Bia\",\"Number\":\"0404555777\"}";
		Contacts contact = new Contacts();
		String actualResult = contact.returnContacts();
		
		assertTrue(actualResult.contains(expectedResultPart1) && actualResult.contains(expectedResultPart2));
	}
	
	@Test
	public void validateRESTAddMethod() throws Exception{
		
		String expectedResultPart1 = "\"Name\":\"John\",\"Number\":\"0404555999\"}";
		String expectedResultPart2 = "\"Name\":\"Bia\",\"Number\":\"0404555777\"}";
		String expectedResultPart3 = "\"Name\":\"Alona\",\"Number\":\"038989899\"}";
		
		Contacts contact = new Contacts();
		String actualResult = contact.add("Alona-038989899");
		assertTrue(actualResult.contains(expectedResultPart1) && actualResult.contains(expectedResultPart2) && actualResult.contains(expectedResultPart3));

		
	}
	
	@Test
	public void validateRESTAddMethodInvalidNewEntryNoContactName() throws Exception{
		
		Contacts contact = new Contacts();
		String actualResult = contact.add("038989899");
		assertTrue(actualResult==null);

		
	}
	
	@Test
	public void validateRESTAddMethodInvalidNewEntryNoContactNumber() throws Exception{
		
		Contacts contact = new Contacts();
		String actualResult = contact.add("Abha");
		assertTrue(actualResult==null);

		
	}
	
	@Test
	public void validateRESTAddMethodInvalidNullNewEntry() throws Exception{
		
		Contacts contact = new Contacts();
		String actualResult = contact.add(null);
		assertTrue(actualResult==null);

		
	}
	
	@Test
	public void validateRESTAddMethodInvalidReversedNewEntryFormat() throws Exception{
		
		Contacts contact = new Contacts();
		String actualResult = contact.add("0389454545-Abha");
		assertTrue(actualResult==null);

		
	}
}
