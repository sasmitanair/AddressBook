package com.addressbook.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.addressbook.hibernate.AddressBookEntry;

public class CompareAddressBooksTest {

	@Test
	public void compareAddressBooksAndRetrieveUniqueContactsTest(){
		Map<String,String> expectedUniqueset = new HashMap<String, String>();
		expectedUniqueset.put("0404555777", "Bia");
		
		String inputContactList = "[{\"Name\":\"Alona\",\"Number\":\"038989899\"},{\"Name\":\"Bia\",\"Number\":\"0404555777\"}]";
		assertComparisons(inputContactList, expectedUniqueset );
		
	}
	
	@Test
	public void compareEmptyUserProvidedAddressBooksWithDBContactsTest(){
		Map<String,String> expectedUniqueset = new HashMap<String, String>();
		expectedUniqueset.put("038989899", "Alona");
		assertComparisons("", expectedUniqueset );
		
	}
	
	@Test
	public void compareNullUserProvidedAddressBooksWithDBContactsTest(){
		Map<String,String> expectedUniqueset = new HashMap<String, String>();
		expectedUniqueset.put("038989899", "Alona");
		assertComparisons(null, expectedUniqueset );
		
	}
	
	//Util method
	private void assertComparisons(String inputContactList, Map<String,String> expectedUniqueset){
		
		List<AddressBookEntry> availablecontactsList = new ArrayList<AddressBookEntry>();
		AddressBookEntry entry1 = new AddressBookEntry();
		entry1.setContactName("Alona");
		entry1.setContactNumber("038989899");
		availablecontactsList.add(entry1);
		
		Map<String,String> actualUniqueset = (new CompareAddressBooks()).compare(availablecontactsList, inputContactList);
		
		assertTrue(expectedUniqueset.equals(actualUniqueset));
	}
}
