package com.spritehealth.gcpapplication.factory;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public final class UserFactory {

	public LinkedHashSet<String> properties = new LinkedHashSet<String>(); //properties for datastore entity
	public ArrayList<String> values = new ArrayList<String>(); //values of corresponding property 
	
	public LinkedHashSet<String> getProperties() {
		return properties;
	}
	public ArrayList<String> getValues() {
		return values;
	}
	
	
}
