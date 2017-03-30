/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.InteractionData;
import dao.MessageData;
import dao.PhotoData;
import dao.UserData;
import entity.Comment;
import entity.Photo;
import entity.User;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.glassfish.jersey.internal.util.Base64;
import util.Utils;

/**
 *
 * @author dfChicken
 */
@Path("/interact")
public class InteractionSvc {

    @PUT
    @Path("/photo/like")
    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
    public Response putPhotoLike(@QueryParam("uid") int uid, @QueryParam("pid") int pid, @QueryParam("date_created") long created) {
        boolean result = false;
        Timestamp date_created = new Timestamp(created);
        result = InteractionData.putPhotoLike(uid, pid, date_created);
        if (result) {
            MessageData.pushPhotoNotification(MessageData.TYPE_LIKE, pid, uid);
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @PUT
    @Path("/user/addfollow")
    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFollow(@QueryParam("uid") int uid, @QueryParam("fuid") int fuid) {
        boolean result = false;
        result = InteractionData.addFollow(uid, fuid);
        if (result) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @PUT
    @Path("/user/addcomment")
    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
    public Response addComment(@QueryParam("uid") int uid, @QueryParam("pid") int pid, @QueryParam("content") String content, @QueryParam("time_created") long created) {
        boolean result = false;
        Timestamp _created = new Timestamp(created);
//        Timestamp _updated = new Timestamp(created);
        String decodedComment = Utils.decodeUTF8(content);
        result = InteractionData.putComment(uid, pid, decodedComment, _created, _created);
        if (result) {
            MessageData.pushPhotoNotification(MessageData.TYPE_COMMENT, pid, uid);
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @GET
    @Path("/photo/getcomments")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getPhotoComments(@QueryParam("pid") int pid) {
        String response = "";
        Gson gson = new GsonBuilder().serializeNulls().create();
        //allow null value
        ArrayList<Comment> comments = InteractionData.getPhotoComments(pid);
        if (!comments.isEmpty()) {
//            response = new Gson().toJson(photos);
            response = gson.toJson(comments);
        }
        return response;
    }

    @GET
    @Path("/user/followers")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getFollowerUsers(@QueryParam("uid") int uid) {
        String response = "";
        Gson gson = new GsonBuilder().serializeNulls().create();
        ArrayList<User> users = InteractionData.getFollowerUsers(uid);
        if (!users.isEmpty()) {
//            response = new Gson().toJson(photos);
            response = gson.toJson(users);
        }
        return response;
    }

    @GET
    @Path("/user/following")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getFollowingUsers(@QueryParam("uid") int uid) {
        String response = "";
        Gson gson = new GsonBuilder().serializeNulls().create();
        ArrayList<User> users = InteractionData.getFollowingUsers(uid);
        if (!users.isEmpty()) {
//            response = new Gson().toJson(photos);
            response = gson.toJson(users);
        }
        return response;
    }

    @GET
    @Path("/user/searchusers")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getUsersByNamePrefix(@QueryParam("uid") int uid, @QueryParam("prefix") String prefix) {
        String response = "";
        Gson gson = new GsonBuilder().serializeNulls().create();
        String decodedQuery = Utils.decodeUTF8(prefix);
        ArrayList<User> users = InteractionData.getUsersByNamePrefix(uid, decodedQuery);
        if (!users.isEmpty()) {
            response = gson.toJson(users);
        }
        return response;
    }

    @GET
    @Path("/user/getdetails")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getUserDetails(@QueryParam("uid") int uid) {
        String response = "";
        Gson gson = new GsonBuilder().serializeNulls().create();
        //allow null value
        User u = UserData.getSingleUserWithDetails(uid);
        if (u != null) {
//            response = new Gson().toJson(photos);
            response = gson.toJson(u);
        }
        return response;
    }

    @GET
    @Path("/user/info")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getUserInfo(@QueryParam("uid") int uid) {
        String response = "";
        Gson gson = new GsonBuilder().serializeNulls().create();
        //allow null value
        User u = UserData.getUserDataLite(uid);
        if (u != null) {
//            response = new Gson().toJson(photos);
            response = gson.toJson(u);
        }
        return response;
    }

    @PUT
    @Path("/user/putcommentlike")
    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
    public Response putCommentLike(@QueryParam("uid") int uid, @QueryParam("comment_id") int cmid) {
        boolean result = false;
        result = InteractionData.putCmtLike(uid, cmid);
        if (result) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @DELETE
    @Path("/photo/dislike")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePhotoLike(@QueryParam("uid") int uid, @QueryParam("pid") int pid) {
        boolean result = false;
        result = InteractionData.deletePhotoLike(uid, pid);
        if (result) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.GONE).build();
        }
    }

    @DELETE
    @Path("/photo/dislikecomment")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCommentLike(@QueryParam("uid") int uid, @QueryParam("comment_id") int cmid) {
        boolean result = false;
        result = InteractionData.deleteCmtLike(uid, cmid);
        if (result) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.GONE).build();
        }
    }

    @DELETE
    @Path("/user/removefollow")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFollower(@QueryParam("uid") int uid, @QueryParam("fuid") int fuid) {
        boolean result = false;
        result = InteractionData.deleteFollow(uid, fuid);
        if (result) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.GONE).build();
        }
    }

    @DELETE
    @Path("/user/deletecomment")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteComment(@QueryParam("comment_id") int cmid, @QueryParam("uid") int uid, @QueryParam("pid") int pid) {
        boolean result = false;
        result = InteractionData.deleteComment(cmid, uid, pid);
        if (result) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.GONE).build();
        }
    }

    @PUT
    @Path("/user/updavatar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAvatar(@QueryParam("uid") int uid, @QueryParam("pid") int pid) {
        boolean result = false;
        result = UserData.updateAvatar(uid, pid);
        if (result) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.GONE).build();
        }
    }
}
