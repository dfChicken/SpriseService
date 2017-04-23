/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.InteractionData;
import dao.PhotoData;
import entity.Photo;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
    public String getUserPhotos(@QueryParam("uid") int uid, @QueryParam("guid") int guid) {
        String response = "";
        ArrayList<Photo> photos = PhotoData.getUserPhotos(uid, guid);
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
    public Response deletePhoto(@QueryParam("uid") int uid, @QueryParam("pid") int pid) {
//        String response = "";
        boolean result = PhotoData.deletePhoto(uid, pid);

        if (result) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.GONE).build();
        }
    }

    @PUT
    @Path("/photo/upload")
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadNewPhotoFeed(@QueryParam("uid") int uid, @QueryParam("caption") String caption, @QueryParam("lat") float lat, @QueryParam("longt") float longt,
            @QueryParam("size") long size, @QueryParam("url") String url, @QueryParam("down_url") String down_url, @QueryParam("status") int status,
            @QueryParam("isAvatar") int isAvatar, @QueryParam("created") long created, @QueryParam("ratio") float ratio) {

        boolean result = false;

        Timestamp create_time = new Timestamp(created);
        if ("null".equals(down_url)) {
            down_url = null;
        }

        String decodedCaption = Utils.decodeUTF8(caption);

        result = PhotoData.insertPhoto(uid, decodedCaption, lat, longt, size, url, down_url, status, isAvatar, create_time, create_time, ratio);
        if (result) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @PUT
    @Path("/photo/updimgratio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePhotoSizeRatio(@QueryParam("pid") int pid, @QueryParam("ratio") float ratio) {
        boolean result = false;
        result = PhotoData.updatePhotoSizeRatio(pid, ratio);
        if (result) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.GONE).build();
        }
    }

}
