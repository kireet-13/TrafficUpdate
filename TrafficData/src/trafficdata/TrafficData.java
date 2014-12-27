package trafficdata;
import com.restfb.*;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TrafficData
{
	public static void main(String[] args)
	{
                Date current = new Date();
		FacebookClient facebookClient = new DefaultFacebookClient("CAACEdEose0cBABtGidcg2MAetE4rZA0W5Fc87X1Rv8h599XvWm6AloqYCxSluvN0IPFQt6RZCjwTrPMORVR1XSPkEB6Pu7ZA5JY3H0tpUKhCpmXV0o0X9UV0p9u5VDNSXx5CAwsNVonE4Y2ZBkSQxG7sENuARPJDZCQicEYYd5ICcbawkJ6wDVlQClwpaf5csQS3Ff6TRtNjHOYe95TpB");
                Connection<Post> myFeed = facebookClient.fetchConnection("147207215344994/posts", Post.class);
		//Page page = facebookClient.fetchObject("BangaloreTrafficPolice", Page.class);
		//Connection<User> myFriends = facebookClient.fetchConnection("me/friends",User.class,Parameter.with("Fields", "name,id,picture"));
		//Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class);
                    
    	//System.out.println("Count of my friends: " + myFriends.getData().size());
                    
                String url="https://www.facebook.com/pages/Delhi-Traffic-Police/117817371573308";
                Page page = facebookClient.fetchObject(url, Page.class); 
                System.out.println(page.getId());
                                
                long currentL=current.getTime()*1000;
                System.out.println(currentL);
                Connection<Post> pageFeed = facebookClient.fetchConnection(page.getId() + "/feed", Post.class);
                to:
                for (List<Post> feed : pageFeed){
                    for (Post post : feed){
                        Date d=post.getCreatedTime();
                        long dL=d.getTime()*1000;
                        
                        System.out.println(dL);
                        String name=post.getFrom().getName().trim();    
                         //System.out.println(post.getMessage());
                        if((dL>((currentL)-1800000)))
                        {
                            //System.out.println(post.getMessage());
                        }
                                           
                  
                }
           }
                 
//		System.out.println("User name: " + page.getName());
//		System.out.println(page.getId());
//		System.out.println("Page likes: " + page.getLikes());
		
	}
}
