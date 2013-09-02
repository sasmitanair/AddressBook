package com.addressbook.hibernate.test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import com.addressbook.hibernate.DBQuery;

public class DBQueryTest {


	
	@Test
	public void addAddressBookEntryTest(){
		
		DBQuery query = new DBQuery();
		assertTrue(query.add("Bia", "0404555777"));
	}
	
	@Test
	public void retrieveAddressBookEntryTest(){
		
		//add a dummy entry first
		DBQuery query = new DBQuery();
		assertTrue(query.add("John", "0404555999"));
		
		//Now retrieve inserted element
		assertTrue(query.get().size()>0);
	}
	
	@Test
	public void addAddressBookEntryWithInvalidInputTest(){
		DBQuery query = new DBQuery();
		assertFalse(query.add("John", "invalid"));
	}
	
}
