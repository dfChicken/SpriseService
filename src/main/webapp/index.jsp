<%-- 
    Document   : index
    Created on : Jan 21, 2017, 10:54:48 PM
    Author     : dfChicken
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <form action="resources/files/upload" method="post" enctype="multipart/form-data">
            <p>
                Select an image file : <input type="file" name="file" size="45" />
            </p>
            <p>Target Upload Path : <input type="text" name="path" /></p>
            <input type="submit" value="Upload It" />
        </form>
    </body>
</html>
