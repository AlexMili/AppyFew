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

import org.bson.types.ObjectId;
import com.mongodb.*;
import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.net.UnknownHostException;

public class Week1Homework4 {
    private static final Logger logger = LoggerFactory.getLogger("logger");

    public static void main(String[] args) throws UnknownHostException {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(
                Week1Homework4.class, "/");

        final MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));

        Spark.get(new Route("/mongo/:db/:collection") {
            @Override
            public Object handle(final Request request,
                                 final Response response) {
								 
				DB database = client.getDB(request.params(":db"));
				DBCollection collection = database.getCollection(request.params(":collection"));

                StringWriter writer = new StringWriter();
                try {

					DBCursor cursor = collection.find();
                    while (cursor.hasNext()) {
                        DBObject result = cursor.next();
                        writer.append(result.toString());
                    }

                    response.body(writer.toString());
                } catch (Exception e) {
                    logger.error("Failed", e);
                    halt(500);
                }
                return writer;
            }
        });


        Spark.get(new Route("/mongo/:db/:collection/:oid") {
            @Override
            public Object handle(final Request request,
                                 final Response response) {

                DB database = client.getDB(request.params(":db"));
                DBCollection collection = database.getCollection(request.params(":collection"));


                StringWriter writer = new StringWriter();
                try {
                    String sOid = request.params(":oid");

                    DBCursor cursor = collection.find(new BasicDBObject("_id",sOid));

                    if (!cursor.hasNext()){
                        ObjectId objId = new ObjectId(sOid);
                        cursor = collection.find(new BasicDBObject("_id",objId));
                    }

                    while (cursor.hasNext()) {
                        DBObject result = cursor.next();
                        writer.append(result.toString());
                    }

                    response.body(writer.toString());
                } catch (Exception e) {
                    logger.error("Failed", e);
                    halt(500);
                }
                return writer;
            }
        });

    }
}
