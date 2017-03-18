/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.DBConnection;
import dao.UserData;
import util.Utils;
import java.sql.SQLException;
import javax.annotation.security.PermitAll;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author dfChicken
 */
//Path: http://localhost/<appln-folder-name>/register
@Path("/register")
public class RegisterSvc {

    //Permit for all request
//    @PermitAll
    // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/register/doregister
    @Path("/doregister")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    // Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
    public String doLogin(@QueryParam("name") String name, @QueryParam("username") String uname, @QueryParam("password") String pwd) {
        String response = "";
        //System.out.println("Inside doLogin "+uname+"  "+pwd);
        int retCode = registerUser(name, uname, pwd);
        if (retCode == 0) {
            response = Utils.constructJSON("register", true);
        } else if (retCode == 1) {
            response = Utils.constructJSON("register", false, "You are already registered");
        } else if (retCode == 2) {
            response = Utils.constructJSON("register", false, "Special Characters are not allowed in Username and Password");
        } else if (retCode == 3) {
            response = Utils.constructJSON("register", false, "Error occured");
        }
        return response;

    }

    private int registerUser(String name, String uname, String pwd) {
        System.out.println("Inside checkCredentials");
        int result = 3;
        if (Utils.isNotNull(uname) && Utils.isNotNull(pwd)) {
            try {
                if (UserData.insertUser(name, uname, pwd)) {
                    System.out.println("RegisterUSer if");
                    result = 0;
                }
            } catch (SQLException sqle) {
                System.out.println("RegisterUSer catch sqle");
                //When Primary key violation occurs that means user is already registered
                if (sqle.getErrorCode() == 1062) {
                    result = 1;
                } //When special characters are used in name,username or password
                else if (sqle.getErrorCode() == 1064) {
                    System.out.println(sqle.getErrorCode());
                    result = 2;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("Inside checkCredentials catch e ");
                result = 3;
            }
        } else {
            System.out.println("Inside checkCredentials else");
            result = 3;
        }

        return result;
    }

}
