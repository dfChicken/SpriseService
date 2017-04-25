/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dal.DBConnection;
import dal.UserData;
import javax.annotation.security.PermitAll;
import util.Utils;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.internal.util.Base64;

/**
 *
 * @author dfChicken
 */
//Path: http://localhost/<appln-folder-name>/login
@Path("/login")
public class LoginSvc {

    //Permit for all request
//    @PermitAll
    // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/login/dologin
    @Path("/dologin")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    // Query parameters are parameters: http://localhost/<appln-folder-name>/login/dologin?username=abc&password=xyz
    public String doLogin(@QueryParam("email") String email, @QueryParam("password") String pwd) {
        String response = "";
        if (checkCredentials(email, pwd)) {
            String token = issueToken(email, pwd);
            int uid = UserData.getUserId(email, pwd);
            response = Utils.loginSuccess(true, uid, email, token);
        } else {
            response = Utils.loginFailed(false, "Incorrect Email or Password");
        }
        return response;
    }

    /**
     * Method to check whether the entered credential is valid
     *
     * @param uname
     * @param pwd
     * @return
     */
    private boolean checkCredentials(String email, String pwd) {
        System.out.println("Inside checkCredentials");
        boolean result = false;
        if (Utils.isNotNull(email) && Utils.isNotNull(pwd)) {
            try {
                result = UserData.checkLogin(email, pwd);
                //System.out.println("Inside checkCredentials try "+result);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                //System.out.println("Inside checkCredentials catch");
                result = false;
            }
        } else {
            //System.out.println("Inside checkCredentials else");
            result = false;
        }

        return result;
    }

    /**
     * Method to create token based on username and password
     *
     * @param uname
     * @param pwd
     * @return
     */
    public static String issueToken(String email, String pwd) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        // This example will encode user+pass to Base64 Basic Authentication
        String token = Base64.encodeAsString(email + ":" + pwd);
        System.out.println("Generated Token: " + token);
        return token;
    }
}
