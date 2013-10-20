package controllers;

import com.mongodb.*;
import com.mongodb.util.JSON;
import play.mvc.Controller;

import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: fxkun_000
 * Date: 20/10/13
 * Time: 13:21
 * To change this template use File | Settings | File Templates.
 */
public class Rtb extends Controller {

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


    private static List<String> likes = Arrays.asList("mode","show","garden","tech","travel");

    private static Map<String, Banner> bannersByLike= new HashMap(){{
        put("mode",new Banner("http://faceplouc.com/adchance/mode.jpg","#"));
        put("show",new Banner("http://faceplouc.com/adchance/show.jpg","#"));
        put("garden",new Banner("http://faceplouc.com/adchance/garden.jpg","#"));
        put("tech",new Banner("http://faceplouc.com/adchance/tech.jpg","#"));
        put("travel",new Banner("http://faceplouc.com/adchance/travel.jpg","#"));
    }
    };

    public static void bid(String request){
        request = request.replace("\\","");
        request = request.substring(1,request.length() - 1);
        Logger.getAnonymousLogger().info(request);

        DBObject bidRequest = (DBObject) JSON.parse(request);
        String imei = bidRequest.get("o").toString();

        DBObject userRequest = (DBObject) JSON.parse(String.format("{imei : \"%s\"}",imei));
        DBObject user = users.findOne(userRequest);

        List<Banner> banners = new ArrayList();

        if (user != null) {
            List<String> userLikes = new ArrayList();
            for (String like : likes) {
                Object userLike = (Object) user.get(like);

                if ( (userLike != null )&&( userLike instanceof Boolean) && ((Boolean )userLike )){
                    userLikes.add(like);
                    if ( bannersByLike.containsKey(like)){
                        banners.add(bannersByLike.get(like));
                    }
                }
            }

            if ( banners.size() == 0) {
                renderJSON("{ bid: 0 }");
                return;
            }
            int bannernb = (int) Math.round(( Math.random() * banners.size())) % banners.size();

            Banner selectedBanner = banners.get( bannernb );

            if (selectedBanner == null) {
                renderJSON("{ bid: 0 }");
                return;
            }

            BasicDBObject bidResponse = new BasicDBObject().append("bid",  Math.round(1 + 42 * Math.random()) );
            bidResponse.append("htmlstring", String.format (
                    "<banner><a href='%s'<img src='%s'/></a></banner>",
                    selectedBanner.ahref,
                    selectedBanner.imgsrc
                    )
            );
            bidResponse.append("imgsrc", selectedBanner.imgsrc);
            bidResponse.append("ahref", selectedBanner.ahref);

            renderJSON(JSON.serialize(bidResponse));
        } else {
            renderJSON("{ bid: 0 }");
        }
    }
}
