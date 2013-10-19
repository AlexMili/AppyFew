package controllers;


import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import play.*;
import play.mvc.*;

import models.*;

import java.io.IOException;
import java.io.StringWriter;
import java.net.UnknownHostException;

public class AdChanceUser extends Controller {

    private static final String dbname = "adchance";
    private static final String collectionname = "users";

    static MongoClient client = null;
    static{
        try {
            client = new MongoClient(new ServerAddress("localhost", 27017));
        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    private static final DB db = client.getDB(dbname);
    private static final DBCollection users = db.getCollection(collectionname);

    private static void log(String format,String ... args) {
        System.out.println(String.format(format,args));
    }



    public static void createUser(String jsonuser) throws IOException {
        DBObject dbUser = (DBObject) JSON.parse(jsonuser);
        dbUser.put("_id", dbUser.get("id"));

        MapUtils.debugPrint(System.out,"user", dbUser.toMap());

        users.save(dbUser);

    }

    public static void updateUser(String id, User user) {
        // User dbUser = User.findById(id);
        //dbUser.updateDetails(user); // some model logic you would write to do a safe merge
        // dbUser.save();
        // user(id);
    }

    public static void deleteUser(String id) {
        //User.findById(id).delete();
        // renderText("success");
    }

    public static void user(String id)  {
        // User user = User.findById(id);
        // render(user);
    }

}