/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trafficdata;

public class TestAccess {
    public static void main(String[] args)
    {
        FbAccessToken fb= new FbAccessToken();
        fb.LoggedInFacebookClient("850511448302448", "4b14c19b3035a3deb91e893849b1c42f");
    }
    
}
