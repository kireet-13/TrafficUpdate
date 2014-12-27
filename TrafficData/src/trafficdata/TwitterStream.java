package trafficdata;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.xml.sax.InputSource;
import twitter4j.DirectMessage;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamAdapter;
import twitter4j.UserStreamListener;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author kanu
 */
public class TwitterStream {

    public static void main(String[] args) throws IOException, ParserConfigurationException {
        String serializedClassifier = "english.all.3class.distsim.crf.ser.gz";
        AbstractSequenceClassifier classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("GPtsu5cjC08KTOEojEoaHw");
        cb.setOAuthConsumerSecret("SsgeXn73bN4CXUYtJfEdKOwBxVTmAEPvmFo3q2CX45w");
        cb.setOAuthAccessToken("154196958-J1Gqy86jmQ6YSoFVVq69bmbJB0acGxiDEocxtvre");
        cb.setOAuthAccessTokenSecret("DpTJr3huuDy2qMwsCMgsTn5yNbi0oQzSDGhDDWQsLog");
        twitter4j.TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        StatusListener listener = new StatusListener() {

            public void onStatus(Status status) {
                BufferedWriter fw = null;
                try {
                    fw = new BufferedWriter(new FileWriter("d://traffic_data_mumbai.txt", true));
                    String XmlData = classifier.classifyWithInlineXML(status.getText());
                    System.out.println(XmlData);
                    StringTokenizer st = new StringTokenizer(XmlData, "<");
                    while (st.hasMoreTokens()) {
                        String ele = st.nextToken();
                        if (ele.startsWith("LO")||ele.startsWith("PER")) {
                            ele = ele.replace("LOCATION>", "");
                            ele = ele.replace("/LOCATION>", "");
                            ele = ele.replace("PERSON>", "");
                            ele = ele.replace("/PERSON>", "");
                            System.out.println(ele.trim());
                            String address = ele.trim() + ",mumbai";
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
                            fw.write(new Date()+"###"+address_updated+"###"+lat+"###"+lng+"\n");

                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(TwitterStream.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        fw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(TwitterStream.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice sdn) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onTrackLimitationNotice(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onScrubGeo(long l, long l1) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onStallWarning(StallWarning sw) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onException(Exception excptn) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        };
        FilterQuery fq = new FilterQuery();

        String keywords[] = {"Mumbai traffic", "@TrafflineMUM",  "TrafficMum", "MumbaiTrafficPol", "avoid traffic Mumbai",
            "Breakdown Mumbai traffic","@smart_mumbaikar","@TrafficBOM" , "#StreetSmartWithTraffline mumbai", "#mumbai #TRAFFICALERT ", "#mumbai #TRAFFIC"
        };

        fq.track(keywords);
        twitterStream.addListener(listener);
        twitterStream.filter(fq);

    }

}
