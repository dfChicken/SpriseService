/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.DBConnection;
import dao.UserData;
import static dao.UserData.registerUser;
import entity.User;
import util.Utils;
import java.sql.SQLException;
import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.internal.util.Base64;

/**
 *
 * @author dfChicken
 */
//Path: http://localhost/<appln-folder-name>/register
@Path("/register")
public class RegisterSvc {

    //Permit for all request
//    @PermitAll
    @POST
    @Path("/doregister")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String doRegister(User user) {
        String response = "";
        boolean result = false;

        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();
        String fname = user.getFirstName();
        String lname = user.getLastName();
        int gender = user.getGender();
        java.sql.Timestamp timestmp = new java.sql.Timestamp(user.getCreatedTime());

        result = UserData.registerUser(username, email, password, fname, lname, "", gender, 1, 1, timestmp, timestmp);
        System.out.println(user.toString());
        if (result) {
            int uid = UserData.getUserId(email, password);
            String token = LoginSvc.issueToken(email, password);
            response = Utils.loginSuccess(true, uid, email, token);
        } else {
            response = Utils.constructFailed("register", false, "email conflict");
        }

        return response;

    }

//    private int registerUser(String name, String uname, String pwd) {
//        System.out.println("Inside checkCredentials");
//        int result = 3;
//        if (Utils.isNotNull(uname) && Utils.isNotNull(pwd)) {
//            try {
//                if (UserData.insertUser(name, uname, pwd)) {
//                    System.out.println("RegisterUSer if");
//                    result = 0;
//                }
//            } catch (SQLException sqle) {
//                System.out.println("RegisterUSer catch sqle");
//                //When Primary key violation occurs that means user is already registered
//                if (sqle.getErrorCode() == 1062) {
//                    result = 1;
//                } //When special characters are used in name,username or password
//                else if (sqle.getErrorCode() == 1064) {
//                    System.out.println(sqle.getErrorCode());
//                    result = 2;
//                }
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                System.out.println("Inside checkCredentials catch e ");
//                result = 3;
//            }
//        } else {
//            System.out.println("Inside checkCredentials else");
//            result = 3;
//        }
//
//        return result;
//    }
}
