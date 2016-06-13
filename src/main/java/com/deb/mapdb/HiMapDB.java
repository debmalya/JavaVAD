package com.deb.mapdb;

import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

public class HiMapDB {

	public static void main(String[] args) {
		inMemoryDB();

		fileDB();
		
		serialize();
		
		DB db = DBMaker
		        .fileDB("encrypted.db")
		        //TODO encryption API
//		        .encryptionEnable("password")
		        .make();

	}

	private static void serialize() {
		DB db = DBMaker
		        .fileDB("file.db")
		        .make();
		ConcurrentMap<String,Long> map = db
		        .hashMap("map", Serializer.STRING, Serializer.LONG)
		        .make();
		map.put("something", 111L);

		db.close();
	}

	/**
	 * HashMap (and other collections) can be also stored in file. In this case
	 * the content can be preserved between JVM restarts. It is necessary to
	 * call DB.close() to protect file from data corruption. Other option is to
	 * enable transactions with write ahead log.
	 * 
	 */
	private static void fileDB() {
		DB db = DBMaker.fileDB("file.db").make();
		ConcurrentMap map = db.hashMap("map").make();
		map.put("something", "fileDB");
		System.out.println(map.get("something"));
		db.close();

	}

	private static void inMemoryDB() {
		DB db = DBMaker.memoryDB().make();
		// It opens in-memory HashMap, it uses off-heap store and it is not
		// limited by Garbage Collection:
		ConcurrentMap<String, String> map = (ConcurrentMap<String, String>) db.hashMap("map").make();
		map.put("something", "inMemoryDB");
		System.out.println(map.get("something"));
	}

}
