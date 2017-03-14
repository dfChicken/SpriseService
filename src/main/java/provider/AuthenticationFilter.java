/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provider;

import dao.UserData;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author dfChicken
 */
/**
 * This filter verify the access permissions for a user based on username and
 * password provided in request Bộ lọc này sẽ xác nhận quyền truy cập cho người
 * dùng dựa vào username và mật khẩu được cung cấp trong request
 *
 */
@Provider
//@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements javax.ws.rs.container.ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED).build();
    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).build();
//    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
//            .entity("You cannot access this resource").build();
//    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN)
//            .entity("Access blocked for all users !!").build();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
//        System.out.println("Method: " + requestContext.getRequest().getMethod());
//        System.out.println(requestContext.getUriInfo().getRequestUri());
        System.out.println(requestContext.getHeaders().toString());
        Method method = resourceInfo.getResourceMethod();
        // nếu không sử dụng annotation PermitAll (PermitAll cho phép truy cập không cần auth)
        if (!method.isAnnotationPresent(PermitAll.class)) {
            // Access denied for all
            // Ngăn chặn request với tất cả role (DenyAll)
            if (method.isAnnotationPresent(DenyAll.class)) {
                requestContext.abortWith(ACCESS_FORBIDDEN);
            }
            // Cho phép truy cập với từng role được định nghĩa trong RolesAllowed
            if (method.isAnnotationPresent(RolesAllowed.class)) {

                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

                // Get request headers - lấy request header
                final MultivaluedMap<String, String> headers = requestContext.getHeaders();

                // Fetch authorization header - lấy auth header
                final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

                // If no authorization information present; block access
                if (authorization == null || authorization.isEmpty()) {
//                    throw new NotAuthorizedException(
//                            "Unauthorized: No Authorization header was found",
//                            Response.status(Response.Status.UNAUTHORIZED));
                    requestContext.abortWith(ACCESS_DENIED);
                } else {
                    // Get encoded username and password
                    final String encodedUserPassword = authorization.get(0).substring(AUTHENTICATION_SCHEME.length()).trim();

                    // Decode username and password
                    String[] values = new String(DatatypeConverter.parseBase64Binary(encodedUserPassword),
                            Charset.forName("ASCII")).split(":");
                    // Get username and password
                    String username = values[0];
                    String password = values[1];

//                    System.out.println("Authentication: " + username + ":" + password);
                    if (!isUserAllowed(username, password, rolesSet)) {
                        requestContext.abortWith(ACCESS_DENIED);
                    }
                }
            }

        }

    }

    private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet) {
        boolean isAllowed = false;
        //Step 1. Fetch password from database and match with password in argument
        //If both match then get the defined role for user from database and continue; else return isAllowed [false]
        //Access the database and do this part yourself
        //String userRole = userMgr.getUserRole(username);
        if (UserData.checkLogin(username, password)) {
            String userRole = "USER";
            //Step 2. Verify user role
            if (rolesSet.contains(userRole)) {
                isAllowed = true;
            }
        }
        return isAllowed;
    }

//    private void validateToken(String token) {
//        // Check if it was issued by the server and if it's not expired
//        // Throw an Exception if the token is invalid
//        String usernameAndPassword = new String(Base64.decode(token.getBytes()));;
//
//        //Split username and password tokens
//        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
//        if (tokenizer.hasMoreTokens()) {
//            final String username = tokenizer.nextToken();
//            final String password = tokenizer.nextToken();
//
//            //Verifying Username and password
//            System.out.println("Authentication: " + username + ":" + password);
//        }
//    }
}
