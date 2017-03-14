/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import provider.AuthenticationFilter;

/**
 *
 * @author dfChicken
 */
@javax.ws.rs.ApplicationPath("resources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        // khai báo sử dụng authentication filter
        resources.add(AuthenticationFilter.class);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(provider.AuthenticationFilter.class);
        resources.add(service.FileUploadSvc.class);
        resources.add(service.InteractionSvc.class);
        resources.add(service.LoginSvc.class);
        resources.add(service.PhotoSvc.class);
        resources.add(service.RegisterSvc.class);
    }

}
