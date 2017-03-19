/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author dfChicken
 */
import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import util.Utils;

@Path("/upload")
public class FileUploadSvc {

    @Context
    ServletContext servletContext;

    private static final String CANDIDATE_NAME = "candidateName";
    private static final String SUCCESS_RESPONSE = "{\"status\": \"true\"}";
    private static final String FAILED_RESPONSE = "Failed";

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/multipleFiles")
    public String uploadFile(@Context HttpServletRequest request) {

        String actualPath = servletContext.getRealPath("");
        String serverPath = "http://" + request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath();

        String responseStatus = SUCCESS_RESPONSE;
        String candidateName = null;

        String fileLocation = "";
        fileLocation = actualPath + File.separator + "uploads";
        System.out.println(fileLocation);
        //checks whether there is a file upload request or not
        if (ServletFileUpload.isMultipartContent(request)) {
            final FileItemFactory factory = new DiskFileItemFactory();
            final ServletFileUpload fileUpload = new ServletFileUpload(factory);
            try {
                /*
                 * parseRequest returns a list of FileItem
                 * but in old (pre-java5) style
                 */
                final List items = fileUpload.parseRequest(request);

                if (items != null) {
                    final Iterator iter = items.iterator();
                    while (iter.hasNext()) {
                        final FileItem item = (FileItem) iter.next();
                        String itemName = item.getName();
                        if (itemName != null) {
                            itemName = FilenameUtils.getName(itemName);
                        }
                        final String fieldName = item.getFieldName();
                        final String fieldValue = item.getString();
                        System.out.println("File: " + itemName);
                        if (item.isFormField()) {
                            candidateName = fieldValue;
                            System.out.println("Field Name: " + fieldName + ", Field Value: " + fieldValue);
                            System.out.println("Candidate Name: " + candidateName);
                        } else {
                            final File savedFile = new File(fileLocation + File.separator
                                    + itemName);
                            System.out.println("Saving the file: " + savedFile.getName());
                            item.write(savedFile);
                        }

                    }
                }
            } catch (Exception e) {
                responseStatus = Utils.constructFailed(false, e.getLocalizedMessage());
            }
        }

        return responseStatus;
    }

    @GET
    @Path("/test")
    @Produces("text/plain")
    public String uploadTestParams(@Context HttpServletRequest request) {
        String responseStatus = request.getParameterMap().toString();

        System.out.println("Returned Response Status: " + responseStatus);

        return responseStatus;
    }

    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/postLink")
    public String postLink(@QueryParam("link") String link){
        return link;
    }
}
