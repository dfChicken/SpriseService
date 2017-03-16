/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.PhotoData;
import entity.Photo;
import java.util.ArrayList;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import util.Utility;

/**
 *
 * @author dfChicken
 */
@Path("/photos")
public class PhotoSvc {

    @RolesAllowed("USER")
    @GET
    @Path("/timeline")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getTimelinePhotos(@QueryParam("uid") int uid) {
        String response = "";
        Gson gson = new GsonBuilder().serializeNulls().create();
        //allow null value
        ArrayList<Photo> photos = PhotoData.getTimelinePhotos(uid);
        if (!photos.isEmpty()) {
//            response = new Gson().toJson(photos);
            response = gson.toJson(photos);
        }
        return response;
    }

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getUserPhotos(@QueryParam("uid") int uid) {
        String response = "";
        ArrayList<Photo> photos = PhotoData.getUserPhotos(uid);
        if (!photos.isEmpty()) {
            response = new Gson().toJson(photos);
        }
        return response;
    }

    @GET
    @Path("/notuser")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getAllPhotosNotUser(@QueryParam("uid") int uid) {
        String response = "";
        ArrayList<Photo> photos = PhotoData.getAllPhotosExceptUser(uid);
        if (!photos.isEmpty()) {
            response = new Gson().toJson(photos);
        }
        return response;
    }

    @GET
    @Path("/photo")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getSinglePhoto(@QueryParam("pid") int pid) {
        String response = "";
        Photo p = PhotoData.getSinglePhoto(pid);
        if (null != p) {
            response = new Gson().toJson(p);
        }
        return response;
    }

    @DELETE
    @Path("/delete/single")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deletePhoto(@QueryParam("pid") int pid) {
//        String response = "";
        boolean result = PhotoData.deletePhoto(pid);
//        if (result) {
//            response = Utility.deleteJsonResponse(pid, Utility.PHOTO, result);
//        } else {
//            response = Utility.deleteJsonResponse(pid, Utility.PHOTO, false);
//        }
        return Utility.entityJsonResponse(Utility.DELETE, pid, Utility.PHOTO, result);
    }

}
