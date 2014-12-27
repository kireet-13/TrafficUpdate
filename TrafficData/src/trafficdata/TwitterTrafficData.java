package trafficdata;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.*;
import java.util.*;
import java.io.*;

import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterTrafficData {

    public static void main(String args[])throws IOException
    {
       Twitter twitter = new TwitterFactory().getInstance();

	   	       AccessToken accessToken = new AccessToken("154196958-J1Gqy86jmQ6YSoFVVq69bmbJB0acGxiDEocxtvre", "DpTJr3huuDy2qMwsCMgsTn5yNbi0oQzSDGhDDWQsLog");
	   	       twitter.setOAuthConsumer("GPtsu5cjC08KTOEojEoaHw", "SsgeXn73bN4CXUYtJfEdKOwBxVTmAEPvmFo3q2CX45w");
	       twitter.setOAuthAccessToken(accessToken);

	       try {
	           Query query = new Query("dtptraffic");
	           QueryResult result;

			  result= twitter.search(query);
			   List<Status> tweets = result.getTweets();
			   	for (Status tweet : tweets) {
			   	               System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
				}
	       }
	       catch (TwitterException te) {
	           te.printStackTrace();
	           System.out.println("Failed to search tweets: " + te.getMessage());
	           System.exit(-1);
         }
    }

    private static void storeAccessToken(User user, AccessToken accessToken)
    {
        System.out.println(user.getScreenName());
    }
}
