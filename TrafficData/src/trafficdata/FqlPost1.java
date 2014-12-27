/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trafficdata;

import com.restfb.Facebook;

/**
 *
 * @author kanu
 */
public  class FqlPost1 {
    @Facebook("post_id")
    String post_id;

    @Facebook("created_time")
    String created_time;
    
    @Facebook("message")
    String message;
    
    @Facebook("actor_id")
    String actor_id;

    @Override
    public String toString() {
        return String.format("%s,%s %s %s", post_id, created_time,message,actor_id);
    }
}
