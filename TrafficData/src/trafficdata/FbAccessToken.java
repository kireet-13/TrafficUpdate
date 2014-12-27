package trafficdata;
import com.restfb.DefaultFacebookClient;

public class FbAccessToken extends DefaultFacebookClient {
        public void LoggedInFacebookClient(String appId, String appSecret) {
        AccessToken accessToken = this.obtainAppAccessToken(appId, appSecret);
        this.accessToken = accessToken.getAccessToken();
        System.out.println(accessToken.toString());
    }
    
}
