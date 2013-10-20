package adchance;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.netty.buffer.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.littleshoot.proxy.*;
import io.netty.handler.codec.http.*;

import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

import java.io.StringWriter;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: fel
 * Date: 19/10/13
 * Time: 23:55
 * To change this template use File | Settings | File Templates.
 */

/*
    run with :
    mvn compile exec:java -Dexec.mainClass=adchance.Proxy

*/

public class Proxy {
    private static final Logger log = Logger.getAnonymousLogger();


    public static void main(String args[]) {

        HttpProxyServer server =
                DefaultHttpProxyServer.bootstrap()
                        .withPort(8080)
                        .withListenOnAllAddresses(true)
                        .withAllowLocalOnly(false)
                        .withIdleConnectionTimeout(5)
                        .withFiltersSource(new HttpFiltersSourceAdapter() {
                            public HttpFilters filterRequest(HttpRequest originalRequest) {
                                // Check the originalRequest to see if we want to filter it
                                boolean wantToFilterRequest = true;

                                if (!wantToFilterRequest) {
                                    return null;
                                } else {
                                    return new HttpFiltersAdapter(originalRequest) {
                                        @Override
                                        public HttpResponse requestPre(HttpObject httpObject) {

                                            if (httpObject instanceof HttpRequest) {
                                                log.info("REQUEST");
                                                HttpRequest httpRequest = (HttpRequest) httpObject;
                                                log.info("\turl: " + httpRequest.getUri());

                                                URI url = URI.create(httpRequest.getUri()); // url decoding
                                                String host = url.getHost();
                                                String path = url.getPath();

                                                log.info("\thost: " + host);
                                                log.info("\tpath: " + path);

                                                List<NameValuePair> params = URLEncodedUtils.parse(url, "utf8");
                                                Map<String, String> urlParameters = new HashMap();
                                                Iterator<NameValuePair> iParams = params.iterator();

                                                while (iParams.hasNext()) {
                                                    NameValuePair param = iParams.next();
                                                    urlParameters.put(param.getName(), param.getValue());
                                                }

                                                System.out.println("PARAMS : \n" + JSON.serialize(urlParameters));

                                                // 4 in a line
                                                if (StringUtils.equals(host, "wv.inner-active.mobi")) {
                                                    if (false && StringUtils.startsWith(path, "/simpleM2M/clientRequestWVBannerOnly")) {
                                                        return getAdRequestResponse(
                                                                "wv.inner-active.mobi.ftl",
                                                                "http://lorempixel.com/320/50/",
                                                                "#"
                                                        );
                                                    }
                                                    // BenjBanana
                                                } else if (StringUtils.equals(host, "my.mobfox.com")) {
                                                    if (StringUtils.startsWith(path, "/request.php")) {
                                                        return getAdRequestResponse(
                                                                "my.mobfox.com.ftl",
                                                                "http://192.168.230.33:9000/public/tech.jpg",
                                                                "#"
                                                        );
                                                        /*
                                                        DBObject rtbResponse = rtbRequest(JSON.serialize(urlParameters));
                                                        Logger.getAnonymousLogger().info(JSON.serialize(rtbResponse));
                                                        if (Integer.parseInt(rtbResponse.get("bid").toString()) > 0) {
                                                            return getAdRequestResponse(
                                                                    "my.mobfox.com.ftl",
                                                                    rtbResponse.get("imgsrc").toString(), //lorempixel.com/300/50/",
                                                                    rtbResponse.get("ahref").toString()
                                                            );
                                                        }
*/
                                                    }
                                                } else if (StringUtils.equals(host, "api2.playhaven.com")) {
                                                    if (StringUtils.startsWith(path, "/v3/publisher/content/")) {
                                                        return getAdRequestResponse(
                                                                "api2.playhaven.com.ftl",
                                                                "http://lorempixel.com/640/560/",
                                                                "#"
                                                        );
                                                    }
                                                } else if (StringUtils.equals(host, "my.mobfox.com")) {
                                                    if (StringUtils.startsWith(path, "/request.php")) {
                                                        return getAdRequestResponse(
                                                                "my.mobfox.com.ftl",
                                                                "http://lorempixel.com/300/50/",
                                                                "#"
                                                        );
                                                    }
                                                }

                                                return null;
                                            }
                                            // TODO: implement your filtering here
                                            return null;
                                        }

                                        @Override
                                        public HttpResponse requestPost(HttpObject httpObject) {
                                            // TODO: implement your filtering here
                                            return null;
                                        }

                                        @Override
                                        public void responsePre(HttpObject httpObject) {
                                            // TODO: implement your filtering here
                                        }

                                        @Override
                                        public void responsePost(HttpObject httpObject) {
                                            // TODO: implement your filtering here
                                        }
                                    };
                                }
                            }
                        }

                        )
            .start();
}

    private static DBObject rtbRequest(String params) {
        try {
        String urlName = "http://localhost:9000/rtb/bid";
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            HttpPost httpPost = new HttpPost(urlName);
            List <NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("request", JSON.serialize(params)));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);
            log.info(response2.getEntity().getContent().toString());
            return (DBObject) JSON.parse( response2.getEntity().getContent().toString() );

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BasicDBObject().append("bid",1)
                .append("imgsrc","http://193.168.230.33:9000/public/home.jpg")
                .append("ahref","#")
                ;
    }

    static DefaultFullHttpResponse getAdRequestResponse(String templateName, String adImg, String adUrl) {
        log.info("\t********************* " + templateName);
        StringWriter responseContent = null;
        try {
            // Build the response

            final Configuration configuration = new Configuration();
            configuration.setClassForTemplateLoading(Proxy.class, ".");

            Template responseTemplate = configuration.getTemplate(templateName);

            // template parameters
            Map<String, String> responseParametersMap = new HashMap<String, String>();
            responseParametersMap.put("AD_A_HREF", adUrl); //href

            responseParametersMap.put("AD_IMG_SRC", adImg); // img


            responseContent = new StringWriter();
            responseTemplate.process(responseParametersMap, responseContent);
        } catch (Exception e) {
            log.severe("Template building : " + e.getMessage());
            e.printStackTrace();
        }

        // Content request response forge
        if (responseContent == null) {
            return null;
        }

        ByteBuf content = Unpooled.copiedBuffer(responseContent.toString(), Charset.forName("utf8"));

        DefaultFullHttpResponse response =
                new DefaultFullHttpResponse(
                        HttpVersion.HTTP_1_0,
                        HttpResponseStatus.OK,
                        content);

        response.headers().add("Access-Control-Allow-Origin", "*");
        response.headers().add("Content-Type", "text/html;charset=UTF-8");

        return response;

    }
    /*

    [ ] Benj Banana
    my.mobfox.com
    GET /request.php?rt=android_app&v=1.0&i=&u=Mozilla%2F5.0%20(Linux%3B%20U%3B%20Android%204.1.2%3B%20fr-fr%3B%20GT-I9100%20Build%2FJZO54K)%20AppleWebKit%2F534.30%20(KHTML%2C%20like%20Gecko)%20Version%2F4.0%20Mobile%20Safari%2F534.30&u2=Mozilla%2F5.0%20(Linux%3B%20U%3B%20Android%204.1.2%3B%20fr-fr%3B%20GT-I9100%20Build%2FJZO54K)%20AppleWebKit%2F533.1%20(KHTML%2C%20like%20Gecko)%20Version%2F4.0%20Mobile%20Safari%2F533.1&s=96f2e31d641d7e58f8e74642789c37bd&o=359778043115714&o2=null&t=0&connection_type=null&listads=&c.mraid=0&sdk=banner HTTP/1.1

    [ ] Shoot the apple // Whale trail frenzy
    www.moreadexchange.com :8080
    GET /ads/?sdk_version=1.0.0&android_sdk=16&device_type=samsung+samsung+GT-I9100&interstitial=1&udid=359778043115714&os_type=android&os_version=4.1.2&pkg=com.droidhen.shootapple&lang=fr&country=FR HTTP/1.1

    [ ] SpeedX3D
    api2.playhaven.com
    GET /v3/publisher/content/?app=com.gamelion.speedx3d&os=4.1.2+16&orientation=0&connection=2&hardware=GT-I9100&stime=0&idiom=2&width=800&placement_id=launch&app_version=1.9&sdk_version=1.12.3&sdk_platform=android&languages=fr&height=480&token=823a8eae08e141c6805df2e8797de6ad&nonce=Hd0mAZqHUyB4dqPAB7ECKD1mQTU&preload=0&device=1bc14fdb92cf95aa&dpi=240&signature=6d5ec165e6594d8b649447e6b0c15666863c59e2

    [ ] IPAD : 4 in a line
    wv.inner-active.mobi
        GET /simpleM2M/clientRequestWVBannerOnly?aid=ZingMagic_ZingMagic_FialV_iOS_iPhone&v=2.1.0-iPad-2.0.1.7&po=947&f=628&k=kids,sports,games,spa,phone,camera,shoes,clothes,jewellery,cars,gadgets,food,resturants,offers&cid=17051722021&cuid=17019101379&w=1024&h=768&nt=WIFI&t=1382145376
    RESPONSE HEADER
        HTTP/1.1 200 OK
        Access-Control-Allow-Origin: *
        Content-Type: text/html;charset=UTF-8
        Date: Sat, 19 Oct 2013 01:16:15 GMT
        Server: Apache-Coyote/1.1
        Set-Cookie: IACID=17051722021; Domain=.inner-active.mobi; Expires=Tue, 18-Oct-2016 01:16:16 GMT
        X-IA-Ad-Height: 90
        X-IA-Ad-Width: 728
        X-IA-AdNetwork: MdotM_RTB
        X-IA-Cid: 17051722021
        X-IA-Error: OK
        X-IA-Pricing: CPM
        X-IA-Pricing-Currency: USD
        X-IA-Pricing-Value: 0.37892105263157894
        X-IA-Session: 1442007347
        Content-Length: 10860
        Connection: keep-alive




     */
}
