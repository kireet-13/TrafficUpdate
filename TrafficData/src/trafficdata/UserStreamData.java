package trafficdata;

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
import java.util.StringTokenizer;
import java.util.logging.Level;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.json.simple.JSONValue;

public final class UserStreamData {

    public static void main(String[] args) throws TwitterException {
        String serializedClassifier = "english.all.3class.distsim.crf.ser.gz";
        AbstractSequenceClassifier classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("GPtsu5cjC08KTOEojEoaHw");
        cb.setOAuthConsumerSecret("SsgeXn73bN4CXUYtJfEdKOwBxVTmAEPvmFo3q2CX45w");
        cb.setOAuthAccessToken("154196958-J1Gqy86jmQ6YSoFVVq69bmbJB0acGxiDEocxtvre");
        cb.setOAuthAccessTokenSecret("DpTJr3huuDy2qMwsCMgsTn5yNbi0oQzSDGhDDWQsLog");
        twitter4j.TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        UserStreamListener listener = new UserStreamListener() {
            @Override
            public void onStatus(Status status) {
                //System.out.println("onStatus @" + status.getUser().getScreenName() + " - " + status.getText());
                BufferedWriter fw = null;
                try {
                    fw = new BufferedWriter(new FileWriter("d://traffic_data_mumbai.txt", true));
                    String XmlData = classifier.classifyWithInlineXML(status.getText());
                    System.out.println(XmlData);
                    StringTokenizer st = new StringTokenizer(XmlData, "<");
                    while (st.hasMoreTokens()) {
                        String ele = st.nextToken();
                        if (ele.startsWith("LO") || ele.startsWith("PER")) {
                            ele = ele.replace("LOCATION>", "");
                            ele = ele.replace("/LOCATION>", "");
                            ele = ele.replace("PERSON>", "");
                            ele = ele.replace("/PERSON>", "");
                            System.out.println(ele.trim());
                            String address = ele.trim() + ",mumbai";
                            //System.out.println(address);
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
                            org.json.simple.JSONObject jso = (org.json.simple.JSONObject) obj;
                            org.json.simple.JSONArray arr1 = (org.json.simple.JSONArray) jso.get("results");

                            String data1 = arr1.get(0).toString();
                            Object obj1 = JSONValue.parse(data1);
                            org.json.simple.JSONObject jso1 = (org.json.simple.JSONObject) obj1;
                            String address_updated = jso1.get("formatted_address").toString();
                            //System.out.println(address_updated);
                            String data2 = jso1.get("geometry").toString();
                            Object obj2 = JSONValue.parse(data2);
                            org.json.simple.JSONObject jso2 = (org.json.simple.JSONObject) obj2;
                            String data3 = jso2.get("location").toString();
                            Object obj3 = JSONValue.parse(data3);
                            org.json.simple.JSONObject jso3 = (org.json.simple.JSONObject) obj3;
                            String lat = jso3.get("lat").toString();
                            String lng = jso3.get("lng").toString();
                            System.out.println(address_updated);
                            fw.write(new Date() + "###" + address_updated + "###" + lat + "###" + lng + "\n");

                        }
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                finally
                {
                    try {
                        fw.close();
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(UserStreamData.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            @Override
            public void onDeletionNotice(long directMessageId, long userId) {
                System.out.println("Got a direct message deletion notice id:" + directMessageId);
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got a track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onFriendList(long[] friendIds) {

            }

            @Override
            public void onFavorite(User source, User target, Status favoritedStatus) {

            }

            @Override
            public void onUnfavorite(User source, User target, Status unfavoritedStatus) {
                System.out.println("onUnFavorite source:@"
                        + source.getScreenName() + " target:@"
                        + target.getScreenName() + " @"
                        + unfavoritedStatus.getUser().getScreenName()
                        + " - " + unfavoritedStatus.getText());
            }

            @Override
            public void onFollow(User source, User followedUser) {
                System.out.println("onFollow source:@"
                        + source.getScreenName() + " target:@"
                        + followedUser.getScreenName());
            }

            @Override
            public void onUnfollow(User source, User followedUser) {
                System.out.println("onFollow source:@"
                        + source.getScreenName() + " target:@"
                        + followedUser.getScreenName());
            }

            @Override
            public void onDirectMessage(DirectMessage directMessage) {
                System.out.println("onDirectMessage text:"
                        + directMessage.getText());
            }

            @Override
            public void onUserListMemberAddition(User addedMember, User listOwner, UserList list) {
                System.out.println("onUserListMemberAddition added member:@"
                        + addedMember.getScreenName()
                        + " listOwner:@" + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            @Override
            public void onUserListMemberDeletion(User deletedMember, User listOwner, UserList list) {
                System.out.println("onUserListMemberDeleted deleted member:@"
                        + deletedMember.getScreenName()
                        + " listOwner:@" + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            @Override
            public void onUserListSubscription(User subscriber, User listOwner, UserList list) {
                System.out.println("onUserListSubscribed subscriber:@"
                        + subscriber.getScreenName()
                        + " listOwner:@" + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            @Override
            public void onUserListUnsubscription(User subscriber, User listOwner, UserList list) {
                System.out.println("onUserListUnsubscribed subscriber:@"
                        + subscriber.getScreenName()
                        + " listOwner:@" + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            @Override
            public void onUserListCreation(User listOwner, UserList list) {
                System.out.println("onUserListCreated  listOwner:@"
                        + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            @Override
            public void onUserListUpdate(User listOwner, UserList list) {
                System.out.println("onUserListUpdated  listOwner:@"
                        + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            @Override
            public void onUserListDeletion(User listOwner, UserList list) {
                System.out.println("onUserListDestroyed  listOwner:@"
                        + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            @Override
            public void onUserProfileUpdate(User updatedUser) {
                System.out.println("onUserProfileUpdated user:@" + updatedUser.getScreenName());
            }

            public void onUserDeletion(long deletedUser) {
                System.out.println("onUserDeletion user:@" + deletedUser);
            }

            public void onUserSuspension(long suspendedUser) {
                System.out.println("onUserSuspension user:@" + suspendedUser);
            }

            @Override
            public void onBlock(User source, User blockedUser) {
                System.out.println("onBlock source:@" + source.getScreenName()
                        + " target:@" + blockedUser.getScreenName());
            }

            @Override
            public void onUnblock(User source, User unblockedUser) {
                System.out.println("onUnblock source:@" + source.getScreenName()
                        + " target:@" + unblockedUser.getScreenName());
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
                System.out.println("onException:" + ex.getMessage());
            }
        };
        twitterStream.addListener(listener);
        // user() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        twitterStream.user();
    }

}
