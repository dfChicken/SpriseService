/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dal.InteractionData;
import dal.MessageData;
import entity.Message;
import entity.Notification;
import entity.Token;
import entity.User;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.Utils;

/**
 *
 * @author dfChicken
 */
@Path("/message")
public class MessageSvc {

    @GET
    @Path("/getchatted")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getChattedUsers(@QueryParam("uid") int uid) {
        String response = "";
        //allow null value
        Gson gson = new GsonBuilder().serializeNulls().create();

        ArrayList<User> users = MessageData.getChattedFirebaseList(uid);
        if (!users.isEmpty()) {
        //response = new Gson().toJson(photos);
            response = gson.toJson(users);
        }
        return response;
    }

    @GET
    @Path("/photos/notification")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getPhotosNotificationList(@QueryParam("uid") int uid) {
        String response = "";
        Gson gson = new GsonBuilder().serializeNulls().create();
        ArrayList<Notification> notifications = MessageData.getPhotosNotificationList(uid);
        if (!notifications.isEmpty()) {
            response = gson.toJson(notifications);
        }
        return response;
    }

    @PUT
    @Path("/addchat")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addChat(@QueryParam("sid") int sid, @QueryParam("rid") int rid) {
        boolean result = false;
        result = MessageData.addChatRoom(sid, rid);
        if (result) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @PUT
    @Path("/user/addtoken")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUserFirebaseToken(@QueryParam("uid") int uid, @QueryParam("token") String token) {
        boolean result = false;
        result = MessageData.addUserFirebaseToken(uid, token);
        if (result) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @PUT
    @Path("/user/updatetoken")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateUserFirebaseToken(@QueryParam("uid") int uid, Token token) {
        String response = "";
        boolean result = false;
        result = MessageData.updateUserFirebaseToken(uid, token.getToken(), token.getOldToken());
//        System.out.println("token" + token);
//        System.out.println("oldToken" + oldToken);
//        System.out.println("uid = " + uid);
//        System.out.println("token = " + token.getToken());
//        System.out.println("oldToken = " + token.getOldToken());
        if (result) {
            response = Utils.constructJSON(true);
        } else {
            response = Utils.constructJSON(false);
        }
        return response;
    }

    @DELETE
    @Path("/user/deletetoken")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUserFirebaseToken(@QueryParam("uid") int uid, @QueryParam("token") String token) {
        boolean result = false;
        result = MessageData.deleteUserFirebaseToken(uid, token);
        if (result) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.GONE).build();
        }
    }

    @POST
    @Path("/pushMsgNoti")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String pushMessageNotification(Message msg) {
        MessageData.pushMessageNotification(msg.getMessage(), msg.getSender_id(), msg.getReceiver_id());

        return Utils.constructJSON(true);
    }
    
    @POST
    @Path("/pushPhotoNoti")
    @Produces(MediaType.APPLICATION_JSON)
    public String pushPhotoNotification(@QueryParam("type") int type, @QueryParam("pid") int pid, @QueryParam("uid") int uid){
        MessageData.pushPhotoNotification(type, pid, uid);
        return Utils.constructJSON(true);
    }
}
