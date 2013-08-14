/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tengen;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class UpdateRemoveTest {
	public static void main(String[] args) throws UnknownHostException {
		DBCollection collection = createCollection();

		List<String> names = Arrays.asList("alice", "bobby", "cathy", "david",
				"ethan");
		for (String name : names) {
			collection.insert(new BasicDBObject("_id", name));
		}

		// see scratch method
		runPro();
		// printCollection(collection);
	}

	private static void runPro() throws UnknownHostException {
		// TODO Auto-generated method stub
		MongoClient client = new MongoClient();
		DB db = client.getDB("students");
		DBCollection collection = db.getCollection("grades");

		DBObject query = new BasicDBObject("type", "homework");
		DBCursor cursor = collection.find(query);
		DBObject object = null;
		String student_id = "";
		Double score = 0.0;
		try {
			while (cursor.hasNext()) {
				object = cursor.next();
				if(object.get("student_id") == student_id){
					if((Double) object.get("score") < score){
						
					}
				}else{
					student_id = object.get("student_id").toString();
					score = (Double) object.get("score");
				}
			}
		} finally {
			cursor.close();
		}
	}

	// these are all the statement I used throughout the lecture.
	private static void scratch(DBCollection collection) {
		collection.update(new BasicDBObject("_id", "alice"), new BasicDBObject(
				"age", 24));

		collection.update(new BasicDBObject("_id", "alice"), new BasicDBObject(
				"$set", new BasicDBObject("age", 24)));

		collection.update(new BasicDBObject("_id", "alice"), new BasicDBObject(
				new BasicDBObject("gender", "F")));

		collection.update(new BasicDBObject("_id", "frank"), new BasicDBObject(
				"$set", new BasicDBObject("age", 24)), true, false);

		collection.update(new BasicDBObject(), new BasicDBObject("$set",
				new BasicDBObject("title", "Dr")), false, true);

		collection.remove(new BasicDBObject("_id", "frank"));
	}

	private static DBCollection createCollection() throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB db = client.getDB("course");
		DBCollection collection = db.getCollection("UpdateRemoveTest");
		collection.drop();
		return collection;
	}

	private static void printCollection(final DBCollection collection) {
		DBCursor cursor = collection.find().sort(new BasicDBObject("_id", 1));
		try {
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
		} finally {
			cursor.close();
		}

	}
}
