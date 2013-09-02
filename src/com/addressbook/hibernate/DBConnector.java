package com.addressbook.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class DBConnector {
	
	private static DBConnector connector = null;
	private static SessionFactory factory = null;
	
	private DBConnector(){
		
		Configuration configure = new Configuration();
		configure.configure("com/addressbook/hibernate/hibernate.cfg.xml");
		
		ServiceRegistry registry = new ServiceRegistryBuilder().applySettings(configure.getProperties()).buildServiceRegistry();
		factory = configure.buildSessionFactory(registry);
		
	}

	public static SessionFactory getFactory() {
		if(connector == null){
			connector = new DBConnector();
		}
		return factory;
	}
	

}
