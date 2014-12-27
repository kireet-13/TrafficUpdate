package trafficdata;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author kanu
 */
class FqlPost {

    private static String pageId;
    private static String startTimeInSec;
    private static String endTimeInSec;

    public static void main(String[] args) {
        pageId = "117817371573308";
        String serializedClassifier = "english.all.3class.distsim.crf.ser.gz";
        AbstractSequenceClassifier classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
        Date current = new Date();
        long currentL = current.getTime() / 1000;
        long endL = currentL - 345600;
        endTimeInSec = currentL + "";
        startTimeInSec = endL + "";
        String query = "select post_id,created_time,message,actor_id from stream where "
                + "source_id = " + pageId + " and created_time > "
                + startTimeInSec + " and created_time < " + endTimeInSec
                + " limit 1000";
        FacebookClient facebookClient = new DefaultFacebookClient("CAACEdEose0cBAF9NV6LkLcdZA5XMLW1vzqOn1zZCB3OEwrmxJVxZCiAkGQ9pQgVEIQu4OXSw5xlzI2gsONPTZCc4GOjbfH8K5y4uVfDzVUP5M1YNZAk6NNvzpzZB6gEfbrFmP2xOZBIatZCi40vDZAJhxJlBtG89XACrsO4fthTGaZB4KZCSlq3KLMdSWULZB5KzwFrQpZCpeQNy3rtQ42C5WQTi6");
        List<FqlPost1> fqlPosts = facebookClient.executeFqlQuery(query, FqlPost1.class);
        for (FqlPost1 pagePost : fqlPosts) {
            Date dd = new Date(Long.parseLong(pagePost.created_time) * 1000);
            BufferedWriter fw = null;
            try {
                fw = new BufferedWriter(new FileWriter("d://traffic_data_delhi_map.txt", true));
                if (pagePost.actor_id.equals("117817371573308")) {

                    String XmlData = classifier.classifyWithInlineXML(pagePost.message);
                    //System.out.println(XmlData);
                    StringTokenizer st = new StringTokenizer(XmlData, "<");
                    while (st.hasMoreTokens()) {
                        String ele = st.nextToken();
                        if (ele.startsWith("LOC")||ele.startsWith("PER")) {
                            ele = ele.replace("LOCATION>", "");
                            ele = ele.replace("/LOCATION>", "");
                            ele = ele.replace("PERSON>", "");
                            ele = ele.replace("/PERSON>", "");
                            System.out.println(ele.trim());
                            String address = ele.trim() + ",new delhi";
                            String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(address) + "&sensor=false&key=AIzaSyC-SQ14QC6ZcilMloviU-KPU-EfmA8qnlY";
                            URI uri = new URI(url);
                            URL page = new URL(uri.toString());
                            StringBuffer text = new StringBuffer();
                            HttpURLConnection conn = (HttpURLConnection) page.openConnection();
                            conn.connect();
                            InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
                            BufferedReader buff = new BufferedReader(in);
                            String st1 = buff.readLine();
                            while (st1 != null) {
                                text.append(st1);
                                st1 = buff.readLine();
                            }
                            String data = text.toString();
                            Object obj = JSONValue.parse(data);
                            JSONObject jso = (JSONObject) obj;
                            JSONArray arr1 = (JSONArray) jso.get("results");

                            String data1 = arr1.get(0).toString();
                            Object obj1 = JSONValue.parse(data1);
                            JSONObject jso1 = (JSONObject) obj1;
                            String address_updated = jso1.get("formatted_address").toString();
                            //System.out.println(address_updated);
                            String data2 = jso1.get("geometry").toString();
                            Object obj2 = JSONValue.parse(data2);
                            JSONObject jso2 = (JSONObject) obj2;
                            String data3 = jso2.get("location").toString();
                            Object obj3 = JSONValue.parse(data3);
                            JSONObject jso3 = (JSONObject) obj3;
                            String lat = jso3.get("lat").toString();
                            String lng = jso3.get("lng").toString();
                            fw.write(dd + "###" + address_updated + "###" + lat + "###" + lng +"\n");

                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(TwitterStream.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

}
