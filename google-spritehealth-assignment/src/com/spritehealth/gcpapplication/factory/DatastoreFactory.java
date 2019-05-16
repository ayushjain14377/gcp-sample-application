package com.spritehealth.gcpapplication.factory;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Key;

public final class DatastoreFactory {

	private DatastoreFactory() {
		
	}
	
	public static final String bucket = "spritehealth-gcp-application.appspot.com";
	public static final String KIND_NAME = "Users";
	public static final String KEY_NAME = "Username";
	
	static Key entityKey;
 
	
	public static Key createEntityKey(Datastore datastore, String key) {
		
		entityKey = datastore.newKeyFactory().setKind(KIND_NAME).newKey(key);
		
		return entityKey;
}

}
