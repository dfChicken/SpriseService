<%-- 
    Document   : gglogin
    Created on : Feb 10, 2017, 4:47:45 PM
    Author     : dfChicken
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login With Google Using Java</title>
    </head>
    <link href="social-buttons.css" rel="stylesheet"/>
    <link href="bootstrap.min.css" rel="stylesheet">
    <style>
        .demoDiv {
            margin:auto;
            text-align:center;
        }
    </style>
    <body>
        <br/>
        <div class="demoDiv">
            <a	href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8088/ANDRService/oauth2callback&response_type=code&client_id=59285751805-ntafdo0hq5ims8jbri7s0f7c0d5spuu1.apps.googleusercontent.com&approval_prompt=force"
               class="btn btn-lg btn-social btn-google"> <i
                    class="fa fa-twitter"></i> Sign in with Google
            </a>
        </div>
    </body>
</html>
