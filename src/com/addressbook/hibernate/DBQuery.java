package com.addressbook.hibernate;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

public class DBQuery {

	
	/**
	 * SELECT - Retrieve data from DB
	 * TODO: Given the name of the address book, fetch the list of stored contacts
	 * For now assuming only one addressBook.
	 * Hence a get should return the list of contacts from the db
	 * @param addressBookId
	 * @return
	 */
	public List<AddressBookEntry> get() {
		List<AddressBookEntry> contactsList = new ArrayList<AddressBookEntry>();
		Session session = null;
		try {
			session = DBConnector.getFactory().openSession();
			session.beginTransaction();
			contactsList = session.createQuery("from AddressBookEntry").list();
			System.out.println("contactsList = " + contactsList.size());
		} catch (Exception e) {
			System.out.println("EXCEPTION while retrieving from DB. reason: " + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return contactsList;

	}
	

	/**
	 * INSERT - Add new contacts to db
	 * @param name
	 * @param number
	 * @return
	 */	
	public boolean add(String name, String number) {
		boolean status = false;

		// if number contains any other character except 0-9, return false
		String regex = "[0-9]+";
		if (!(number.matches(regex)))
			return status;

		AddressBookEntry entry = new AddressBookEntry();
		entry.setContactName(name);
		entry.setContactNumber(number);
		Session ss = null;

		try {
			ss = DBConnector.getFactory().openSession();
			ss.beginTransaction();
			ss.saveOrUpdate(entry);
			ss.getTransaction().commit();
			status = true;
		} catch (Exception e) {
			System.out.println("Failed to persist the contact with name = "
					+ name + " contact = " + number + " reaon: "
					+ e.getMessage());
		} finally {
			ss.close();
		}

		return status;
	}
	
	/**
	 * DROP - drops the entire table
	 * Utility method for JUnit setup and teardown
	 */
	public void dropTable() {
		Session ss = null;

		try {
			ss = DBConnector.getFactory().openSession();
			ss.beginTransaction();
			ss.createSQLQuery("DROP TABLE addressbook").executeUpdate();
			ss.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Failed to drop table AddressBookEntry. reason:"
					+ e.getMessage());
		} finally {
			ss.close();
		}

	}
}
